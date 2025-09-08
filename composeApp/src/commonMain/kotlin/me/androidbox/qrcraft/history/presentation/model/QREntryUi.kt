package me.androidbox.qrcraft.history.presentation.model

import me.androidbox.qrcraft.features.scan_result.domain.QRType
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
data class QREntryUi(
    val id: Int,
    val contentType: String,
    val content: String,
    val qrType: QRType = QRType.SCANNED,
    val createdAt: Long,
    val createdAtFormatted: String,
    val iconUrl: String
)
