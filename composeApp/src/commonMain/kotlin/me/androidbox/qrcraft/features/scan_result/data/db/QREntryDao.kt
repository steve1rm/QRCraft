package me.androidbox.qrcraft.features.scan_result.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.androidbox.qrcraft.features.scan_result.domain.QRType

@Dao
interface QREntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: QREntry)

    @Query("SELECT * FROM qrentry")
    fun getAllEntries(): Flow<List<QREntry>>

    @Query("SELECT * FROM qrentry WHERE qrType = :type")
    fun getEntriesByType(type: QRType): Flow<List<QREntry>>

    fun getScannedEntries(): Flow<List<QREntry>>{
        return getEntriesByType(QRType.SCANNED)
    }

    fun getGeneratedEntries(): Flow<List<QREntry>>{
        return getEntriesByType(QRType.GENERATED)
    }

    @Query("DELETE FROM QREntry WHERE id = :entryId")
    suspend fun deleteEntryById(entryId: Int)

    @Delete
    suspend fun deleteEntry(entry: QREntry)

}