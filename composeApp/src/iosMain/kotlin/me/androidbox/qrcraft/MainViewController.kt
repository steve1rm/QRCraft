package me.androidbox.qrcraft

import androidx.compose.ui.window.ComposeUIViewController
import me.androidbox.qrcraft.core.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }