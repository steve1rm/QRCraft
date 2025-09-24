package me.androidbox.qrcraft.features.scan_result.data

import androidx.compose.ui.graphics.ImageBitmap

interface SaveQRCraft {
    suspend fun save(bitmap: ImageBitmap, fileName: String): String?
}

