@file:OptIn(ExperimentalPermissionsApi::class)

package me.androidbox.qrcraft.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun ScanningScreen(
    cameraPreviewViewModel: CameraPreviewViewModel,
    modifier: Modifier = Modifier
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    if(!cameraPermissionState.status.isGranted) {
        CameraPreviewContent(
            cameraPreviewViewModel
        )
    }
    else {
        // Snack bar
    }
}