package me.androidbox.qrcraft.features.scan_result.data.db

import androidx.room.TypeConverter
import me.androidbox.qrcraft.features.scan_result.domain.QRType


class QRTypeConverter {
    @TypeConverter
    fun fromQRType(qrType: QRType): String {
        return qrType.name
    }

    @TypeConverter
    fun toQRType(name: String): QRType{
        return QRType.valueOf(name)

        }
}