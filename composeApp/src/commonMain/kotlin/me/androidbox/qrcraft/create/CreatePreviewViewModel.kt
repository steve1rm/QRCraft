package me.androidbox.qrcraft.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.features.scan_result.data.db.QREntry
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.QREntryRepository
import me.androidbox.qrcraft.features.scan_result.domain.QRType
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi

class CreatePreviewViewModel(
    val qrEntryRepository: QREntryRepository,
) : ViewModel() {

    @OptIn(ExperimentalUuidApi::class)
    private val qrId = Random.nextInt(9999)

    fun addQREntry(
        title: String,
        content: String,
        contentType: QRContentType,
        isFavourite: Boolean,
        qrType: QRType = QRType.GENERATED,
    ) {
        val qrEntry =
            QREntry(
                id = qrId,
                title = title,
                content = content,
                contentType = contentType,
                isFavourite = isFavourite,
                qrType = qrType
            )

        viewModelScope.launch {
            qrEntryRepository.upsertQREntry(qrEntry = qrEntry)
        }
    }
}