package me.androidbox.qrcraft.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface QrCraftNavGraph {

    @Serializable
    object QrCraftNavigation {
        @Serializable
        object Scan : QrCraftNavGraph

        @Serializable
        data class ScanResult(val scannedQrCode: String) : QrCraftNavGraph

    }
}