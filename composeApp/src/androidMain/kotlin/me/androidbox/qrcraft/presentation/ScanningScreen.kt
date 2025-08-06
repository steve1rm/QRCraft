@file:OptIn(ExperimentalPermissionsApi::class)

package me.androidbox.qrcraft.presentation

import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@Composable
fun ScanningScreen(
    cameraPreviewViewModel: CameraPreviewViewModel,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    modifier: Modifier = Modifier
) {

    val surfaceRequest by cameraPreviewViewModel.surfaceRequest.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(lifecycleOwner) {
        cameraPreviewViewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.LightGray),
        contentAlignment = Alignment.Center) {
        val boxHeight = this.maxHeight

        Surface(
            modifier
                .size(324.dp),
            shape = RoundedCornerShape(18.dp)
        ) {
            surfaceRequest?.let { request ->
                CameraXViewfinder(
                    surfaceRequest = request,
                    modifier = modifier
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = boxHeight * 0.25f),
            textAlign = TextAlign.Center,
            text = "Point your camera a the QR Code",
            color = Color.White
        )
    }
}