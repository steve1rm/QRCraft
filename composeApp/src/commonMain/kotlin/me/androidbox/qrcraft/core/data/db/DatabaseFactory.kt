package me.androidbox.qrcraft.core.data.db

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create() : RoomDatabase.Builder<AppDatabase>
}