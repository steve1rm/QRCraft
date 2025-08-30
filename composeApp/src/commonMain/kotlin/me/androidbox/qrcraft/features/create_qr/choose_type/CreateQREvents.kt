package me.androidbox.qrcraft.features.create_qr.choose_type

sealed interface CreateQREvents {
    data object OnNavigateBack : CreateQREvents
    data class OnNavigateToResult(val result: String) : CreateQREvents
}