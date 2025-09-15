package me.androidbox.qrcraft.history.presentation

import me.androidbox.qrcraft.history.presentation.model.QREntryUi

sealed interface HistoryEvents {
    data class OnShareContent(val content: String) : HistoryEvents
    data class OnItemClick(val qrEntryUi: QREntryUi) : HistoryEvents
}