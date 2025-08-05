package me.androidbox.qrcraft.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    background = SurfaceHigher,
    onBackground = OnSurfaceAlt,
    error = Error,
    onError = SurfaceHigher
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography(),
        shapes = AppShapes,
        content = content
    )
}