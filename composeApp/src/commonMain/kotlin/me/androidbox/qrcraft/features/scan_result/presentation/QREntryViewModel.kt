package me.androidbox.qrcraft.features.scan_result.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.features.scan_result.data.db.QREntry
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.QREntryRepository
import me.androidbox.qrcraft.features.scan_result.domain.QRType

class QREntryViewModel(val qrEntryRepository: QREntryRepository) : ViewModel() {

    val allEntries = qrEntryRepository.allEntries.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())
    val scannedEntries = qrEntryRepository.scannedEntries.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())
    val generatedEntries = qrEntryRepository.generatedEntries.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())


    fun addQREntry(
        title: String,
        content: String,
        contentType: QRContentType,
        qrType: QRType = QRType.SCANNED,
        id: Int
    ) {
        Logger.e("Will create entry title: $title, content $content, contentType $contentType")

        val qrEntry = QREntry(id = id, title = title, content = content, contentType = contentType, qrType = qrType)
        viewModelScope.launch { qrEntryRepository.addQREntry(qrEntry = qrEntry) }
    }

    fun upsertQREntry(title: String, content: String, contentType: QRContentType, qrType: QRType = QRType.SCANNED) {
        Logger.e("Will create entry title: $title, content $content, contentType $contentType")

        val qrEntry = QREntry(title = title, content = content, contentType = contentType, qrType = qrType)
        viewModelScope.launch { qrEntryRepository.addQREntry(qrEntry = qrEntry) }
    }
}