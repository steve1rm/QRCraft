@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.qrcraft.history.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HistoryItemBottomSheet(
    onDismiss: () -> Unit,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
    ) {
        HistorySheetContent(
            onShareClick = onShareClick,
            onDeleteClick = onDeleteClick
        )
    }
}

@Preview
@Composable
fun HistoryItemBottomSheetPreview() {
    AppTheme {
        HistoryItemBottomSheet(
            onDismiss = {},
            onDeleteClick = {},
            onShareClick = {})
    }
}
