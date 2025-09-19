package me.androidbox.qrcraft.history.presentation.model

enum class HistoryTab(
    val index: Int,
    val title: String,
) {
    SCANNED(0, "Scanned"),
    GENERATED(1, "Generated")
}