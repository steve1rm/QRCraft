package me.androidbox.qrcraft.scanning.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.tick

@Composable
fun CustomSnackbar(snackbarData: SnackbarData) {

    val visuals = snackbarData.visuals
    
    if(visuals is CustomSnackBarVisuals) {
        Snackbar(
            modifier = Modifier.padding(12.dp),
            containerColor = visuals.containerColor,
            contentColor = visuals.contentColor,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                visuals.drawableResource?.let { resId ->
                    Icon(
                        imageVector = vectorResource(Res.drawable.tick),
                        contentDescription = null,
                        tint = visuals.contentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = visuals.message)
            }
        }
    }
}

data class CustomSnackBarVisuals(
    override val message: String,
    override val duration: SnackbarDuration,
    val drawableResource: DrawableResource?,
    val containerColor: Color,
    val contentColor: Color,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals
