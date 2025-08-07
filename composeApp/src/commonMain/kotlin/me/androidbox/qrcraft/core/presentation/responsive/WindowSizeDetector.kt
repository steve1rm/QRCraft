package me.androidbox.qrcraft.core.presentation.responsive

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp

@Composable
fun getDeviceType(): WindowSizeClass {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val widthPx = windowInfo.containerSize.width

    val widthDp = with(density) { widthPx.toDp() }

    return if (widthDp < 600.dp) {
        WindowSizeClass.MOBILE
    } else WindowSizeClass.TABLET
}