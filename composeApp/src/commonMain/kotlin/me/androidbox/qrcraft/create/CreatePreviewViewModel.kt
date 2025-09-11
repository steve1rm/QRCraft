package me.androidbox.qrcraft.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.features.scan_result.data.db.QREntry
import me.androidbox.qrcraft.features.scan_result.domain.QREntryRepository
import me.androidbox.qrcraft.features.scan_result.domain.QRType

class CreatePreviewViewModel (
    val qrEntryRepository: QREntryRepository
): ViewModel() {

    fun addQREntry(contentType: String, content: String, qrType: QRType = QRType.GENERATED) {
        Logger.e("Will create entry content $content, contentType $contentType")

        val qrEntry = QREntry(contentType = contentType, content = content, qrType = qrType)
        viewModelScope.launch { qrEntryRepository.addQREntry(qrEntry = qrEntry) }
    }
}