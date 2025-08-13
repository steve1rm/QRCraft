package me.androidbox.qrcraft.core.utils

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

actual class ShareManager(private val context: Context){
    actual fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            flags += Intent.FLAG_ACTIVITY_NEW_TASK
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        val chooser = Intent.createChooser(intent, null)
        context.startActivity(chooser)
    }
}

@Composable
actual fun rememberShareManager(): ShareManager {
    val context = LocalContext.current
   return remember {
       ShareManager(context = context)
   }
}