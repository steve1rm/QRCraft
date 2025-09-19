package me.androidbox.qrcraft

import androidx.compose.runtime.Composable

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun rememberShareText(): (String) -> Unit

expect class ImagePicker() {
    @Composable
    fun pickImage(onImageSelected: (String?) -> Unit)
    fun launch()
}

expect suspend fun scanQRFromImage(imageUri: String?): String?