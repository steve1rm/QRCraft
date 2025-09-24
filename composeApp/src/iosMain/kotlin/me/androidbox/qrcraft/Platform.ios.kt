package me.androidbox.qrcraft

import androidx.compose.ui.graphics.ImageBitmap
import me.androidbox.qrcraft.features.scan_result.data.SaveQRCraft
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()
actual class SaveQRCraftImp : SaveQRCraft {
    actual override suspend fun save(
        imageBitmap: ImageBitmap,
        fileName: String
    ): String? {
        TODO("Not yet implemented")
    }
}