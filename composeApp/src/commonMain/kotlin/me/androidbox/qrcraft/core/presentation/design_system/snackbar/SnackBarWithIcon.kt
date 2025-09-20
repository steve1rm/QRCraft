package me.androidbox.qrcraft.core.presentation.design_system.snackbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.ui.Success

@Composable
fun SnackBarWithIcon(data: SnackbarData){
    Snackbar(
        modifier = Modifier.padding(16.dp),
        containerColor = Success,
        contentColor = MaterialTheme.colorScheme.onSurface,
        action = {
            data.visuals.actionLabel?.let { actionLabel ->
                TextButton(onClick = { data.dismiss() }) {
                    Text(actionLabel)
                }
            }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp),
            )
            Text(text = data.visuals.message, style = MaterialTheme.typography.labelLarge)
        }
    }
}