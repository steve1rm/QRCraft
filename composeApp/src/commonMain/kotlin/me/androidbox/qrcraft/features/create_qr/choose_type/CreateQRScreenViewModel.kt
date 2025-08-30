package me.androidbox.qrcraft.features.create_qr.choose_type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.features.create_qr.choose_type.model.CreateQRDetail
import me.androidbox.qrcraft.features.create_qr.choose_type.model.toQRCodeString
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType

class CreateQRScreenViewModel(
    val qrContentType: QRContentType,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CreateQRScreenState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadContent(qrContentType)

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CreateQRScreenState()
        )

    private val _events = Channel<CreateQREvents>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CreateQRScreenAction) {
        when (action) {
            CreateQRScreenAction.OnBackIconClick -> {
                viewModelScope.launch { _events.send(CreateQREvents.OnNavigateBack) }
            }

            CreateQRScreenAction.OnGenerateQRClick -> {
                viewModelScope.launch {
                    val result = _state.value.createQRDetail.toQRCodeString()
                    _events.send(CreateQREvents.OnNavigateToResult(result))
                }
            }

            is CreateQRScreenAction.OnContactEmailChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.Contact).copy(email = action.value)
            }

            is CreateQRScreenAction.OnContactNameChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.Contact).copy(name = action.value)
            }

            is CreateQRScreenAction.OnContactPhoneNumberChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.Contact).copy(phoneNumber = action.value)
            }

            is CreateQRScreenAction.OnGeolocationLatChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.Geolocation).copy(latitude = action.value)
            }

            is CreateQRScreenAction.OnGeolocationLonChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.Geolocation).copy(longitude = action.value)
            }

            is CreateQRScreenAction.OnLinkChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.Link).copy(url = action.value)
            }

            is CreateQRScreenAction.OnPhoneNumberChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.PhoneNumber).copy(phoneNumber = action.value)
            }

            is CreateQRScreenAction.OnTextChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.Text).copy(text = action.value)
            }

            is CreateQRScreenAction.OnWiFiEncryptionTypeChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.WiFi).copy(encryptionType = action.value)
            }

            is CreateQRScreenAction.OnWiFiPasswordChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.WiFi).copy(password = action.value)
            }

            is CreateQRScreenAction.OnWiFiSsidChange -> updateDetailAndValidate { detail ->
                (detail as CreateQRDetail.WiFi).copy(ssid = action.value)
            }
        }
    }

    /**
     * Generic method to update current detail + revalidate in one place
     */
    private fun updateDetailAndValidate(
        mapper: (CreateQRDetail) -> CreateQRDetail
    ) {
        _state.update { state ->
            val newDetail = mapper(state.createQRDetail)
            val valid = isDetailValid(newDetail)
            state.copy(createQRDetail = newDetail, generateButtonEnabled = valid)
        }
    }

    /**
     * Validates current detail generically by type
     */
    private fun isDetailValid(detail: CreateQRDetail): Boolean {
        return when (detail) {
            is CreateQRDetail.Contact -> {
                detail.name.isNotBlank() &&
                        detail.email.isNotBlank() &&
                        detail.phoneNumber.isNotBlank()
            }

            is CreateQRDetail.Geolocation -> {
                detail.latitude.isNotBlank() &&
                        detail.longitude.isNotBlank()
            }

            is CreateQRDetail.Link -> detail.url.isNotBlank()

            is CreateQRDetail.PhoneNumber -> detail.phoneNumber.isNotBlank()

            is CreateQRDetail.Text -> detail.text.isNotBlank()

            is CreateQRDetail.WiFi -> {
                detail.ssid.isNotBlank() &&
                        detail.password.isNotBlank() &&
                        detail.encryptionType.isNotBlank()
            }
        }
    }

    private fun loadContent(qrContentType: QRContentType) {
        val qrCreateQRDetail = CreateQRDetail
            .defaults
            .find { it.qrContentType == qrContentType }

        qrCreateQRDetail?.let { qRDetail ->
            _state.update {
                it.copy(
                    createQRDetail = qRDetail
                )
            }
        }
    }

}