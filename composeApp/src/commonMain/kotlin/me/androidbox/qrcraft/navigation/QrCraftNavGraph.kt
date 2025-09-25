package me.androidbox.qrcraft.navigation

import kotlinx.serialization.Serializable
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.QRType

@Serializable
sealed interface QrCraftNavGraph {

    @Serializable
    object QrCraftNavigation {
        @Serializable
        object Scan : QrCraftNavGraph

        @Serializable
        object History : QrCraftNavGraph

        @Serializable
        data class ScanResult(
            val scannedQrCode: String,
            val id: Int = 0,
            val title: String? = null,
            val qrType: QRType = QRType.SCANNED,
        ) : QrCraftNavGraph

        @Serializable
        object CreateQRChooseType : QrCraftNavGraph

        @Serializable
        data class CreateQR(val type: QRContentType) : QrCraftNavGraph

        @Serializable
        data class QrPreview(
            val scannedQrCode: String,
            val title: String,
            val details: String,
            val isFavourite: Boolean,
        ) :
            QrCraftNavGraph
    }
}