@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.qrcraft.features.scan_result.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res

@Composable
fun ButtonSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onShareClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isVisible) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {
                    // Share Button
                    OutlinedButton(
                        onClick = {
                            onShareClicked()
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        AsyncImage(
                            model = Res.getUri("files/share.svg"),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Share",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    // Delete Button
                    OutlinedButton(
                        onClick = {
                            onDeleteClicked()
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        AsyncImage(
                            model = Res.getUri("files/delete.svg"),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Delete",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                // Add some bottom padding to ensure proper spacing
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
fun ButtonSheetPreview() {
    AppTheme {
        ButtonSheet(
            isVisible = true,
            onDismiss = {},
            onShareClicked = {},
            onDeleteClicked = {}
        )
    }
}