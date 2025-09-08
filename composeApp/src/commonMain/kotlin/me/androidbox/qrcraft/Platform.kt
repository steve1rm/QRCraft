package me.androidbox.qrcraft

import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun rememberShareText(): (String) -> Unit