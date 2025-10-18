package me.androidbox.qrcraft.history.presentation

import me.androidbox.qrcraft.history.presentation.model.HistoryTab
import me.androidbox.qrcraft.history.presentation.model.QREntryUi

sealed interface HistoryAction {
    data class OnTabSelected(val tab: HistoryTab) : HistoryAction
    data class OnItemLongClick(val item: QREntryUi) : HistoryAction
    data class OnItemClick(val item: QREntryUi) : HistoryAction
    data class OnItemFavoriteToggle(val item: QREntryUi) : HistoryAction
    data object OnShareClick : HistoryAction
    data object OnDeleteClick : HistoryAction
    data object OnBottomSheetDismiss : HistoryAction
}