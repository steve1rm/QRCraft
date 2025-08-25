package me.androidbox.qrcraft.scanning.presentation

sealed interface OnScanningActions {
    data class OnScanDetected(val qrCode: String) : OnScanningActions
}


