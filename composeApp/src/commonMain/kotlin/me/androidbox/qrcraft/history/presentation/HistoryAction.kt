package me.androidbox.qrcraft.history.presentation

import me.androidbox.qrcraft.history.presentation.model.HistoryTab

sealed interface HistoryAction {
    data class OnTabSelected(val tab: HistoryTab) : HistoryAction
}