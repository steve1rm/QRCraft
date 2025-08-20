package me.androidbox.qrcraft.features.create_qr.choose_type

sealed interface CreateQRScreenAction {
    data object OnBackIconClick : CreateQRScreenAction
    data object OnGenerateQRClick : CreateQRScreenAction
    data class OnTextChange(val value: String) : CreateQRScreenAction
    data class OnLinkChange(val value: String) : CreateQRScreenAction
    data class OnContactNameChange(val value: String) : CreateQRScreenAction
    data class OnContactEmailChange(val value: String) : CreateQRScreenAction
    data class OnContactPhoneNumberChange(val value: String) : CreateQRScreenAction
    data class OnPhoneNumberChange(val value: String) : CreateQRScreenAction
    data class OnGeolocationLatChange(val value: String) : CreateQRScreenAction
    data class OnGeolocationLonChange(val value: String) : CreateQRScreenAction
    data class OnWiFiSsidChange(val value: String) : CreateQRScreenAction
    data class OnWiFiPasswordChange(val value: String) : CreateQRScreenAction
    data class OnWiFiEncryptionTypeChange(val value: String) : CreateQRScreenAction
}