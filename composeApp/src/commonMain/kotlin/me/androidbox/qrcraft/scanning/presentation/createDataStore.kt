package me.androidbox.qrcraft.scanning.presentation

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

typealias PrefDataStore = DataStore<Preferences>

internal const val dataStoreFileName = "qrcraft.preferences_pb"
