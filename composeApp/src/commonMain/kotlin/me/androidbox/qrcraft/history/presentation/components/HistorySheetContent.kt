package me.androidbox.qrcraft.history.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.ic_trash
import qrcraft.composeapp.generated.resources.share

@Composable
fun HistorySheetContent(
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 4.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onShareClick
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = vectorResource(Res.drawable.share),
                    contentDescription = "Share")
            }

            Text(
                text = "Share",
                style = MaterialTheme.typography.labelLarge
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterHorizontally)
        ) {
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = vectorResource(Res.drawable.ic_trash),
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }

            Text(
                text = "Delete",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview
@Composable
fun HistorySheetContentPreview() {
    AppTheme {
        HistorySheetContent(
            onShareClick = {},
            onDeleteClick = {},
            modifier = Modifier
        )
    }
}