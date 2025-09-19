package me.androidbox.qrcraft.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.features.scan_result.data.db.QREntry
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.QREntryRepository
import me.androidbox.qrcraft.features.scan_result.domain.QRType

class CreatePreviewViewModel (
    val qrEntryRepository: QREntryRepository
): ViewModel() {

    fun addQREntry(title: String, content: String, contentType: QRContentType, qrType: QRType = QRType.GENERATED) {
        Logger.e("Will create entry title: $title, content $content, contentType $contentType")


        val qrEntry = QREntry(title = title, content = content, contentType = contentType, qrType = qrType)
        viewModelScope.launch { qrEntryRepository.addQREntry(qrEntry = qrEntry) }
    }
}