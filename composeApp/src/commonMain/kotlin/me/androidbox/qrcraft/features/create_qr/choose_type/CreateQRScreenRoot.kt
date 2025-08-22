package me.androidbox.qrcraft.features.create_qr.choose_type

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.androidbox.qrcraft.core.presentation.design_system.buttons.QRCraftButton
import me.androidbox.qrcraft.core.presentation.design_system.text_fields.QRCraftTextField
import me.androidbox.qrcraft.core.presentation.utils.ObserveAsEvents
import me.androidbox.qrcraft.features.create_qr.choose_type.model.CreateQRDetail
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.toDisplayName
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreateQRScreenRoot(
    onNavigateBack: () -> Unit,
    onNavigateToResult: (result: String) -> Unit,
    viewModel: CreateQRScreenViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            CreateQREvents.OnNavigateBack -> onNavigateBack()

            is CreateQREvents.OnNavigateToResult -> {
                onNavigateToResult(event.result)
            }
        }
    }

    CreateQRScreenScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun CreateQRScreenScreen(
    state: CreateQRScreenState,
    onAction: (CreateQRScreenAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                IconButton(
                    onClick = {
                        onAction(CreateQRScreenAction.OnBackIconClick)
                    },
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back button"
                    )
                }

                Text(
                    text = state.createQRDetail.qrContentType.toDisplayName(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    QRCreateContent(state.createQRDetail, onAction)

                    Spacer(Modifier.height(16.dp))

                    QRCraftButton(
                        text = "Generate QR-Code",
                        enabled = state.generateButtonEnabled,
                        onClick = {
                            onAction(CreateQRScreenAction.OnGenerateQRClick)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

    }
}

@Composable
private fun QRCreateContent(
    detail: CreateQRDetail,
    onAction: (CreateQRScreenAction) -> Unit,
) {
    when (detail.qrContentType) {
        QRContentType.TEXT -> {
            val detail = (detail as CreateQRDetail.Text)

            QRCraftTextField(
                value = detail.text,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnTextChange(it))
                },
                placeholder = "Text",
                modifier = Modifier.fillMaxWidth(),
                isMultiline = true
            )
        }

        QRContentType.LINK -> {
            val detail = (detail as CreateQRDetail.Link)

            QRCraftTextField(
                value = detail.url,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnLinkChange(it))
                },
                placeholder = "URL",
                modifier = Modifier.fillMaxWidth(),
            )
        }

        QRContentType.CONTACT -> {
            val detail = (detail as CreateQRDetail.Contact)

            QRCraftTextField(
                value = detail.name,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnContactNameChange(it))
                },
                placeholder = "Name",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))

            QRCraftTextField(
                value = detail.email,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnContactEmailChange(it))
                },
                placeholder = "Email",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )

            Spacer(Modifier.height(8.dp))

            QRCraftTextField(
                value = detail.phoneNumber,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnContactPhoneNumberChange(it))
                },
                placeholder = "Phone Number",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Phone
            )
        }

        QRContentType.PHONE_NUMBER -> {
            val detail = (detail as CreateQRDetail.PhoneNumber)

            QRCraftTextField(
                value = detail.phoneNumber,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnPhoneNumberChange(it))
                },
                placeholder = "Phone Number",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Phone
            )
        }

        QRContentType.GEOLOCATION -> {
            val detail = (detail as CreateQRDetail.Geolocation)

            QRCraftTextField(
                value = detail.latitude,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnGeolocationLatChange(it))
                },
                placeholder = "Latitude",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Decimal
            )

            Spacer(Modifier.height(8.dp))

            QRCraftTextField(
                value = detail.longitude,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnGeolocationLonChange(it))
                },
                placeholder = "Longitude",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Decimal
            )
        }

        QRContentType.WIFI -> {
            val detail = (detail as CreateQRDetail.WiFi)


            QRCraftTextField(
                value = detail.ssid,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnWiFiSsidChange(it))
                },
                placeholder = "SSID",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))

            QRCraftTextField(
                value = detail.password,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnWiFiPasswordChange(it))
                },
                placeholder = "Password",
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password
            )

            Spacer(Modifier.height(8.dp))

            QRCraftTextField(
                value = detail.encryptionType,
                onValueChange = {
                    onAction(CreateQRScreenAction.OnWiFiEncryptionTypeChange(it))
                },
                placeholder = "Encryption Type",
                modifier = Modifier.fillMaxWidth(),
            )
        }

        else -> {}
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        CreateQRScreenScreen(
            state = CreateQRScreenState(),
            onAction = { }
        )
    }
}

@Preview
@Composable
private fun PreviewContact() {
    AppTheme {
        CreateQRScreenScreen(
            state = CreateQRScreenState(
                createQRDetail = CreateQRDetail.defaults.find {
                    it.qrContentType == QRContentType.CONTACT
                }!!
            ),
            onAction = { }
        )
    }
}