package me.androidbox.qrcraft

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import me.androidbox.qrcraft.features.scan_result.data.SaveQRCraft

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

expect class SaveQRCraftImp : SaveQRCraft {
    override suspend fun save(imageBitmap: ImageBitmap, fileName: String): String?
}