package me.androidbox.qrcraft.history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import me.androidbox.qrcraft.core.presentation.utils.ObserveAsEvents
import me.androidbox.qrcraft.history.presentation.components.HistoryItem
import me.androidbox.qrcraft.history.presentation.components.HistoryItemBottomSheet
import me.androidbox.qrcraft.history.presentation.model.HistoryTab
import me.androidbox.qrcraft.rememberShareText
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import qrcraft.composeapp.generated.resources.Res

@Composable
fun HistoryRoot(
    viewModel: HistoryViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val shareText = rememberShareText()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is HistoryEvents.OnShareContent -> {
                shareText(event.content)
            }
        }
    }

    HistoryScreen(
        state = state,
        onAction = viewModel::onAction
    )

    if (state.selectedItem != null) {
        HistoryItemBottomSheet(
            onDismiss = {
                viewModel.onAction(HistoryAction.OnBottomSheetDismiss)
            },
            onShareClick = {
                viewModel.onAction(HistoryAction.OnShareClick)
            },
            onDeleteClick = {
                viewModel.onAction(HistoryAction.OnDeleteClick)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Scan History",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(12.dp))

        SecondaryTabRow(
            selectedTabIndex = state.selectedTab.index,
            indicator = {
                HorizontalDivider(
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .tabIndicatorOffset(state.selectedTab.index)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        .padding(horizontal = 16.dp)
                )
            },
            divider = {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )

            }
        ) {
            HistoryTab.entries.forEach { tab ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            onAction(HistoryAction.OnTabSelected(tab))
                        })
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items =state.items,
                key = { it.id }
            ) { item ->
                HistoryItem(
                    title = item.contentType,
                    details = item.content,
                    dateTime = item.createdAtFormatted,
                    icon = {
                        AsyncImage(
                            model = Res.getUri(item.iconUrl),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                        )
                    },
                    onLongClick = {
                        onAction(HistoryAction.OnItemLongClick(item))
                    },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        HistoryScreen(
            state = HistoryState(),
            onAction = {}
        )
    }
}