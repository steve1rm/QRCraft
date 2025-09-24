package me.androidbox.qrcraft.features.scan_result.data

import kotlinx.coroutines.flow.Flow
import me.androidbox.qrcraft.features.scan_result.data.db.QREntry
import me.androidbox.qrcraft.features.scan_result.data.db.QREntryDao
import me.androidbox.qrcraft.features.scan_result.domain.QREntryRepository

class DefaultQREntryRepository(val qrEntryDao: QREntryDao) : QREntryRepository {

    override val allEntries: Flow<List<QREntry>> = qrEntryDao.getAllEntries()

    override val scannedEntries: Flow<List<QREntry>> = qrEntryDao.getScannedEntries()

    override val generatedEntries: Flow<List<QREntry>> = qrEntryDao.getGeneratedEntries()

    override suspend fun upsertQREntry(qrEntry: QREntry) {
        qrEntryDao.upsert(qrEntry)
    }

    override suspend fun deleteQREntry(qrEntry: QREntry) {
        qrEntryDao.deleteEntry(qrEntry)
    }
}