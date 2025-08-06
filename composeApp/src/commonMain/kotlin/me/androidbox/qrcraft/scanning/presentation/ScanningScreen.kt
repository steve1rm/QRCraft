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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kashif.cameraK.controller.CameraController
import com.kashif.cameraK.enums.CameraLens
import com.kashif.cameraK.enums.Directory
import com.kashif.cameraK.enums.FlashMode
import com.kashif.cameraK.enums.ImageFormat
import com.kashif.cameraK.enums.QualityPrioritization
import com.kashif.cameraK.enums.TorchMode
import com.kashif.cameraK.permissions.Permissions
import com.kashif.cameraK.permissions.providePermissions
import com.kashif.cameraK.ui.CameraPreview
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.permissions.PermissionDialog

@Composable
fun ScanningScreen(
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val permissions: Permissions = providePermissions()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var cameraController by remember {
        mutableStateOf<CameraController?>(null)
    }
    val cameraPermissionState by remember { mutableStateOf(permissions.hasCameraPermission()) }
    var showPermissionDialog by remember {
        mutableStateOf(false)
    }

    if(showPermissionDialog) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message ="Camera permission granted",
                duration = SnackbarDuration.Long
            )
        }
    }

    if(showPermissionDialog) {
        permissions.RequestCameraPermission(
            onGranted = {
                showPermissionDialog = false
            },
            onDenied = {
                showPermissionDialog = false
            }
        )
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
                    CameraPreview(
                        cameraConfiguration = {
                            setCameraLens(CameraLens.BACK)
                            setFlashMode(FlashMode.OFF)
                            setTorchMode(TorchMode.OFF)
                            setImageFormat(ImageFormat.JPEG)
                            setDirectory(Directory.PICTURES)
                            setQualityPrioritization(QualityPrioritization.QUALITY)
                        },
                        onCameraControllerReady = {
                            cameraController = it
                        }
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = boxHeight * 0.25f),
                    textAlign = TextAlign.Center,
                    text = "Point your camera a the QR Code",
                    color = Color.White
                )

                if(!cameraPermissionState) {
                    PermissionDialog(
                        onCloseApp = onCloseClicked,
                        onGrantAccess = {
                            showPermissionDialog = true
                        },
                        title = "Camera Required",
                        description = "This app cannot function without camera access. To scan QR codes, Please grant permission."
                    )
                }
            }
        }
    )
}
