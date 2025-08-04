@file:OptIn(ExperimentalPermissionsApi::class)

package me.androidbox.qrcraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import me.androidbox.qrcraft.permissions.PermissionsViewModel
import me.androidbox.qrcraft.presentation.CameraPreviewViewModel
import me.androidbox.qrcraft.presentation.ScanningScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val factory = rememberPermissionsControllerFactory()
            val permissionController = remember(factory) {
                factory.createPermissionsController()
            }

            BindEffect(permissionController)

            val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

            val cameraPreviewViewModel = remember {
                CameraPreviewViewModel()
            }

            val permissionsViewModel = remember {
                PermissionsViewModel(
                  permissionsController = permissionController
                )
            }
            ScanningScreen(
                cameraPreviewViewModel = cameraPreviewViewModel,
                 permissionsViewModel = permissionsViewModel
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}