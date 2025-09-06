package me.androidbox.qrcraft.features.scan_result.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.features.scan_result.data.db.QREntry
import me.androidbox.qrcraft.features.scan_result.domain.QREntryRepository
import me.androidbox.qrcraft.features.scan_result.domain.QRType

class QREntryViewModel(val qrEntryRepository: QREntryRepository) : ViewModel() {

    val allEntries = qrEntryRepository.allEntries.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())
    val scannedEntries = qrEntryRepository.scannedEntries.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())
    val generatedEntries = qrEntryRepository.generatedEntries.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(), emptyList())

 //Usmon: When saving a Generated QR Code, then set the QRType to QRType.GENERATED
    fun addQREntry(contentType: String, content: String, qrType: QRType = QRType.SCANNED) {
        Logger.e("Will create entry content $content, contentType $contentType")

        val qrEntry = QREntry(contentType = contentType, content = content, qrType = qrType)
        viewModelScope.launch { qrEntryRepository.addQREntry(qrEntry = qrEntry) }
    }
}