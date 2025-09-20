package me.androidbox.qrcraft.core.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.androidbox.qrcraft.features.scan_result.data.db.QRContentTypeConverter
import me.androidbox.qrcraft.features.scan_result.data.db.QREntry
import me.androidbox.qrcraft.features.scan_result.data.db.QREntryDao
import me.androidbox.qrcraft.features.scan_result.data.db.QRTypeConverter

@Database(entities = [QREntry::class], version = 10)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(QRTypeConverter::class, QRContentTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract val qrEntryDao: QREntryDao

    companion object {
        const val DB_NAME= "qrcraft.db"
    }
}