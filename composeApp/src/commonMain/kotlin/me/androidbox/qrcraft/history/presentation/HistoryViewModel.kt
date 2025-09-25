package me.androidbox.qrcraft.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.features.scan_result.domain.QREntryRepository
import me.androidbox.qrcraft.history.presentation.HistoryEvents.*
import me.androidbox.qrcraft.history.presentation.model.HistoryTab
import me.androidbox.qrcraft.history.presentation.model.toQREntry
import me.androidbox.qrcraft.history.presentation.model.toQREntryUi

class HistoryViewModel(
    private val qrEntryRepository: QREntryRepository,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HistoryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadEntriesForTab(_state.value.selectedTab)

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HistoryState()
        )
    private var dataCollectionJob: Job? = null
    private var deleteItemJob: Job? = null

    private val _events = Channel<HistoryEvents>()
    val events = _events.receiveAsFlow()

    //TODO Launch preview ACTION is missing so after clicking the selected item the preview screen is not loaded
    fun onAction(action: HistoryAction) {
        when (action) {
            is HistoryAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTab = action.tab)
                }

                loadEntriesForTab(action.tab)
            }

            is HistoryAction.OnItemLongClick -> {
                _state.update {
                    it.copy(selectedItem = action.item)
                }
            }

            HistoryAction.OnBottomSheetDismiss -> {
                _state.update {
                    it.copy(selectedItem = null)
                }
            }

            HistoryAction.OnDeleteClick -> {
                deleteItemJob?.cancel()

                deleteItemJob = viewModelScope.launch {
                    qrEntryRepository.deleteQREntry(_state.value.selectedItem?.toQREntry()!!)

                    _state.update { it.copy(selectedItem = null) }
                }
            }

            HistoryAction.OnShareClick -> {
                viewModelScope.launch {
                    _events.send(OnShareContent(_state.value.selectedItem!!.content))
                }
            }

            is HistoryAction.OnItemClick -> {
                viewModelScope.launch {
                    _events.send(OnItemClick(action.item))
                }
            }

            is HistoryAction.OnItemFavoriteToggle -> {
                viewModelScope.launch {
                    qrEntryRepository.upsertQREntry(
                        action.item
                            .copy(isFavourite = !action.item.isFavourite)
                            .toQREntry()
                    )
                }
            }
        }
    }

    private fun loadEntriesForTab(historyTab: HistoryTab) {
        dataCollectionJob?.cancel()

        val entriesFlow = when (historyTab) {
            HistoryTab.SCANNED -> qrEntryRepository.scannedEntries
            HistoryTab.GENERATED -> qrEntryRepository.generatedEntries
        }

        dataCollectionJob = viewModelScope.launch {
            entriesFlow
                .map { entries ->
                    entries.map { entry -> entry.toQREntryUi() }
                }
                .flowOn(Dispatchers.IO)
                .collect { items ->
                    items.forEach {
                        Logger.e("items $it")
                    }

                    _state.update {
                        it.copy(
                            items = items
                        )
                    }
                }
        }
    }

    override fun onCleared() {
        dataCollectionJob?.cancel()
        super.onCleared()
    }

}