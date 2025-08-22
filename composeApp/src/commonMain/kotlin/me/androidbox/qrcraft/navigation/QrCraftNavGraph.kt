package me.androidbox.qrcraft.navigation

import kotlinx.serialization.Serializable
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType

@Serializable
sealed interface QrCraftNavGraph {

    @Serializable
    object QrCraftNavigation {
        @Serializable
        object Scan : QrCraftNavGraph

        @Serializable
        data class ScanResult(val scannedQrCode: String) : QrCraftNavGraph

        @Serializable
        object CreateQRChooseType : QrCraftNavGraph

        @Serializable
        data class CreateQR(val type: QRContentType) : QrCraftNavGraph

        @Serializable
        data class QrPreview(val scannedQrCode: String, val title: String, val details: String) : QrCraftNavGraph
    }
}