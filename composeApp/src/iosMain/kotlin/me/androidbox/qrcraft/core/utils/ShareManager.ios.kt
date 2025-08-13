package me.androidbox.qrcraft.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual class ShareManager {
    actual fun shareText(text: String) {
        val activityViewController = UIActivityViewController(listOf(text), null)
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            activityViewController, animated = true, completion = null
        )
    }
}

@Composable
actual fun rememberShareManager(): ShareManager {
   return remember { ShareManager() }
}