package me.androidbox.qrcraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.qrcraft.presentation.CameraPreviewViewModel
import me.androidbox.qrcraft.presentation.ScanningScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val cameraPreviewViewModel = remember {
                CameraPreviewViewModel()
            }
            ScanningScreen(
                cameraPreviewViewModel
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}