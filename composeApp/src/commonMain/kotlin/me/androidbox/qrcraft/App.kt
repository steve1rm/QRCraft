package me.androidbox.qrcraft

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.qrcraft.core.presentation.responsive.getDeviceType
import me.androidbox.qrcraft.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavigation()

        Text(
            text = getDeviceType().name,
            modifier = Modifier.padding(top = 100.dp)
        )
    }
}