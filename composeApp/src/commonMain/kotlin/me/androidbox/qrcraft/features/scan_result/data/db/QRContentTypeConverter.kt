package me.androidbox.qrcraft.features.scan_result.data.db

import androidx.room.TypeConverter
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType

class QRContentTypeConverter{

    @TypeConverter
    fun fromQRContentType(contentType: QRContentType): String = contentType.name

    @TypeConverter
    fun toQRContentType(value: String): QRContentType =
        QRContentType.entries.find { it.name == value } ?: QRContentType.UNDEFINED
}