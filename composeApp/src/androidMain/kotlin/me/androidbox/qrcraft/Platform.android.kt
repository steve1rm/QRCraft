package me.androidbox.qrcraft

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import androidx.core.net.toUri
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import me.androidbox.qrcraft.features.scan_result.data.SaveQRCraft
import kotlin.coroutines.coroutineContext

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

actual class SaveQRCraftImp(private val context: Context) : SaveQRCraft {
    actual override suspend fun save(imageBitmap: ImageBitmap, fileName: String): String? {
        val contentValues = ContentValues().apply {
            this.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            this.put(MediaStore.Images.Media.MIME_TYPE, "image/webp")
            this.put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_DOWNLOADS + "/qrcodes"
            )
        }

        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        if (uri != null) {
            try {
                val uri = withContext(Dispatchers.IO) {
                    context.contentResolver.openOutputStream(uri).use { outputStream ->
                        if (outputStream != null) {
                            val bitmap = imageBitmap.asAndroidBitmap()
                            bitmap.compress(Bitmap.CompressFormat.WEBP, 100, outputStream)

                            println("qrcraft outputstream completed")
                        }
                    }
                    println("MEME success $uri")
                    uri
                }

                return uri.path
            } catch (exception: Exception) {
                coroutineContext.ensureActive()

                exception.printStackTrace()
            }
        }

        println("qrcraft failed ${uri?.path}")
        return uri?.path
    }
}