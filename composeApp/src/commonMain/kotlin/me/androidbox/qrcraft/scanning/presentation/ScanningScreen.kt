package me.androidbox.qrcraft.scanning.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kashif.cameraK.permissions.Permissions
import com.kashif.cameraK.permissions.providePermissions
import kotlinx.coroutines.launch

@Composable
fun ScanningScreen(
    modifier: Modifier = Modifier
) {

    val permissions: Permissions = providePermissions()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val cameraPermissionState = remember { mutableStateOf(permissions.hasCameraPermission()) }

    if(cameraPermissionState.value) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message ="Camera permission granted",
                duration = SnackbarDuration.Long
            )
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        content = { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color.LightGray),
                contentAlignment = Alignment.Center) {
                val boxHeight = this.maxHeight

                Surface(
                    modifier
                        .size(324.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {

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
    )
}