package me.androidbox.qrcraft.features.scan_result.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.QRType
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity
class QREntry @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val contentType: QRContentType,
    val content: String,
    val qrType: QRType = QRType.SCANNED,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val isFavourite: Boolean = false,
) {
}