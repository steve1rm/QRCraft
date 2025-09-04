package me.androidbox.qrcraft.history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.lifecycle.viewmodel.compose.viewModel
import me.androidbox.qrcraft.history.presentation.model.HistoryTab
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HistoryRoot(
    viewModel: HistoryViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HistoryScreen(
        state = state,
        onAction = viewModel::onAction
    )
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

        when (state.selectedTab) {
            HistoryTab.SCANNED -> {
                Text(
                    text = "SCANNED"
                )
            }
            HistoryTab.GENERATED -> {
                Text(
                    text = "GENERATED"
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