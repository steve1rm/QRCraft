package me.androidbox.qrcraft.history.presentation

sealed interface HistoryEvents {
    data class OnShareContent(val content: String) : HistoryEvents
}