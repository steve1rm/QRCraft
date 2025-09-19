package me.androidbox.qrcraft

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import androidx.core.net.toUri
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

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

actual class ImagePicker {
    private var launcher: ActivityResultLauncher<String>? = null
    private var onImageSelected: ((String?) -> Unit)? = null

    @Composable
    actual fun pickImage(onImageSelected: (String?) -> Unit) {
        this.onImageSelected = onImageSelected
        launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            this.onImageSelected?.invoke(uri?.toString())
        }
    }

    actual fun launch() {
        launcher?.launch("image/*")
    }
}

actual suspend fun scanQRFromImage(imageUri: String?): String? = suspendCancellableCoroutine { continuation ->
    try {
        val context = Application.applicationContext
        val uri = imageUri?.toUri()
        val image = uri?.let { InputImage.fromFilePath(context, it) }
        val scanner = BarcodeScanning.getClient()

        image?.let { scanner.process(it) }
            ?.addOnSuccessListener { barcodes ->
                val qrCode = barcodes.firstOrNull { it.format == Barcode.FORMAT_QR_CODE }
                continuation.resume(qrCode?.displayValue)
            }
            ?.addOnFailureListener {
                continuation.resume(null)
            }
    } catch (e: Exception) {
        continuation.resume(null)
    }
}
