@file:OptIn(ExperimentalPermissionsApi::class)

package me.androidbox.qrcraft.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import me.androidbox.qrcraft.permissions.PermissionsViewModel

@Composable
fun ScanningScreen(
    cameraPreviewViewModel: CameraPreviewViewModel,
    permissionsViewModel: PermissionsViewModel,
    modifier: Modifier = Modifier
) {



}