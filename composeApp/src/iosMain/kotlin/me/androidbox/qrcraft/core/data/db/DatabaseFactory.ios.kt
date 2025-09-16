package me.androidbox.qrcraft.core.data.db

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        val dbFile = documentDirectory() + "/${AppDatabase.DB_NAME}"
        return Room.databaseBuilder<AppDatabase>(name = dbFile)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager().URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )

    return requireNotNull(documentDirectory?.path)
    }
}