package me.androidbox.qrcraft.history.presentation

import me.androidbox.qrcraft.history.presentation.model.HistoryTab

data class HistoryState(
    val selectedTab: HistoryTab = HistoryTab.SCANNED,
)