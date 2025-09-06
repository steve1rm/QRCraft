package me.androidbox.qrcraft.features.scan_result.domain

import kotlinx.coroutines.flow.Flow
import me.androidbox.qrcraft.features.scan_result.data.db.QREntry

interface QREntryRepository {

    val allEntries: Flow<List<QREntry>>
    val scannedEntries: Flow<List<QREntry>>

    val generatedEntries: Flow<List<QREntry>>

    suspend fun addQREntry(qrEntry: QREntry)
}