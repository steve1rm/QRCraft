package me.androidbox.qrcraft.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.features.scan_result.domain.QREntryRepository
import me.androidbox.qrcraft.history.presentation.model.HistoryTab
import me.androidbox.qrcraft.history.presentation.model.toQREntryUi

class HistoryViewModel(
    private val qrEntryRepository: QREntryRepository,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HistoryState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadEntriesForTab(HistoryTab.SCANNED)

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HistoryState()
        )
    private var dataCollectionJob: Job? = null

    fun onAction(action: HistoryAction) {
        when (action) {
            is HistoryAction.OnTabSelected -> {
                _state.update {
                    it.copy(
                        selectedTab = action.tab,
                    )
                }

                loadEntriesForTab(action.tab)
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