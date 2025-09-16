package me.androidbox.qrcraft

import android.content.Intent
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@Composable
actual fun rememberShareText(): (String) -> Unit {
    val context = LocalContext.current

    return remember {
        { content: String ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, content)
            }
            context.startActivity(Intent.createChooser(intent, "Share via"))
        }
    }
}