package me.androidbox.qrcraft.core.presentation.design_system.buttons

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import me.androidbox.ui.AppTheme
import me.androidbox.ui.OnSurface
import me.androidbox.ui.OnSurfaceDisabled
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun QRCraftButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    disabledBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = OnSurface,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = OnSurfaceDisabled
        ),
        enabled = enabled,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview
@Composable
fun QRCraftButtonPreview() {
    AppTheme {
        QRCraftButton(
            text = "Generate QR-Code",
            onClick = { }
        )
    }
}
@Preview
@Composable
fun QRCraftButtonDisabledPreview() {
    AppTheme {
        QRCraftButton(
            text = "Generate QR-Code",
            onClick = { },
            enabled = false
        )
    }
}