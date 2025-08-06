package me.androidbox.qrcraft

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import me.androidbox.qrcraft.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavigation()
    }
}