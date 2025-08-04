package me.androidbox.qrcraft.core.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import me.androidbox.qrcraft.scanning.presentation.PrefDataStore
import me.androidbox.qrcraft.scanning.presentation.dataStoreFileName

val Context.qrCraftPrefDataStore: PrefDataStore by preferencesDataStore(
    name = dataStoreFileName
)
