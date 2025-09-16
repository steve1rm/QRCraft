package me.androidbox.qrcraft.history.presentation

import me.androidbox.qrcraft.history.presentation.model.HistoryTab
import me.androidbox.qrcraft.history.presentation.model.QREntryUi

data class HistoryState(
    val selectedTab: HistoryTab = HistoryTab.SCANNED,
    val items: List<QREntryUi> = emptyList(),
    val selectedItem: QREntryUi? = null,
)