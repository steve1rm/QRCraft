package me.androidbox.qrcraft.history.presentation.model

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import me.androidbox.qrcraft.features.scan_result.data.db.QREntry
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.toDrawableResource
import org.jetbrains.compose.resources.DrawableResource
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun QREntry.toQREntryUi(): QREntryUi {
    return QREntryUi(
        id = this.id,
        title = this.title,
        contentType = this.contentType,
        content = this.content,
        qrType = this.qrType,
        createdAt = this.createdAt,
        createdAtFormatted = this.createdAt.toFormattedDate(),
        iconResource = getIconUrlFromContentType(this.contentType),
        isFavourite = isFavourite
    )
}

fun QREntryUi.toQREntry(): QREntry {
    return QREntry(
        id = this.id,
        title = this.title,
        content = this.content,
        contentType = this.contentType,
        qrType = this.qrType,
        createdAt = this.createdAt,
        isFavourite = this.isFavourite
    )
}

private fun getIconUrlFromContentType(contentType: QRContentType): DrawableResource? {
    return contentType.toDrawableResource()
}

@OptIn(ExperimentalTime::class)
private fun Long.toFormattedDate(): String {
    val timeZone = TimeZone.currentSystemDefault()
    val date = Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
    val year = date.year
    val day = date.day
    val month = date.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
    val hour = date.time.hour
    val minute = date.time.minute
    return "$day $month $year, $hour:$minute"
}