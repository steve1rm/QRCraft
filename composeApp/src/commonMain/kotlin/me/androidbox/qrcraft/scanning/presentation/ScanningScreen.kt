 package me.androidbox.qrcraft.scanning.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import co.touchlab.kermit.Logger
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
import com.kashif.qrscannerplugin.rememberQRScannerPlugin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import me.androidbox.qrcraft.permissions.PermissionDialog
import me.androidbox.qrcraft.scanning.presentation.components.CustomSnackBarVisuals
import me.androidbox.qrcraft.scanning.presentation.components.CustomSnackbar
import me.androidbox.qrcraft.scanning.presentation.components.ScanningSurfaceRoundedCorners
import me.androidbox.ui.appTypography
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.tick

 @Composable
fun ScanningScreen(
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToScanResult: (String) -> Unit
) {

    val permissions: Permissions = providePermissions()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var cameraController by remember {
        mutableStateOf<CameraController?>(null)
    }

    var cameraPermissionState by remember(permissions.hasCameraPermission()) {
        mutableStateOf(permissions.hasCameraPermission())
    }
    var shouldShowSystemPermissionsDialog by remember {
        mutableStateOf(cameraPermissionState)
    }

    var hasShownSnackBarOnce by remember { mutableStateOf(false) }
    val qrScannerPlugin = rememberQRScannerPlugin(coroutineScope)

    LaunchedEffect(Unit) {
        Logger.d {
            "qrCode LaunchedEffect"
        }
        qrScannerPlugin
            .getQrCodeFlow()
       .distinctUntilChanged()
            .collectLatest { qrCode ->
                Logger.d ( tag = "Scanned code",
                    messageString = "qrCode $qrCode")
                onNavigateToScanResult(qrCode)
                qrScannerPlugin.pauseScanning()
            }
    }

    LaunchedEffect(cameraPermissionState) {
        if (cameraPermissionState && !hasShownSnackBarOnce) {
            hasShownSnackBarOnce = true

            snackbarHostState.showSnackbar(
                CustomSnackBarVisuals(
                    message = "Camera permission granted",
                    duration = SnackbarDuration.Short,
                    drawableResource = Res.drawable.tick,
                    containerColor = Color(0xff4caf50),
                    contentColor = Color(0xFF273037)
                )
            )
        }
    }

    if(shouldShowSystemPermissionsDialog) {
        permissions.RequestCameraPermission(
            onGranted = {
                cameraPermissionState = true
            },
            onDenied = {
                cameraPermissionState = false
            }
        )
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    CustomSnackbar(snackbarData = data)
                })
        },
        content = { paddingValues ->
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues)
                    .background(color = Color(0x00000000).copy(alpha = 0.50f)),
                contentAlignment = Alignment.Center) {
                val boxHeight = this.maxHeight


                ScanningSurfaceRoundedCorners(
                    modifier = Modifier.size(324.dp),
                    surfaceRadius = 18.dp,     // How rounded the Surface itself is
                    lineColor = Color.Yellow,          // Color of the corner lines
                    lineStrokeWidth = 5.dp,          // Thickness of the corner lines
                    lineExtensionLength = 32.dp,
                    content =  {
                    CameraPreview(
                        cameraConfiguration = {
                            setCameraLens(CameraLens.BACK)
                            setFlashMode(FlashMode.OFF)
                            setTorchMode(TorchMode.OFF)
                            setImageFormat(ImageFormat.JPEG)
                            setDirectory(Directory.PICTURES)
                            setQualityPrioritization(QualityPrioritization.QUALITY)
                            addPlugin(qrScannerPlugin)
                        },
                        onCameraControllerReady = {
                            Logger.d {
                                "qrCode startScanning"
                            }
                            cameraController = it
                            qrScannerPlugin.startScanning()
                        }
                    )
                })

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.TopCenter)
                        .padding(top = boxHeight * 0.20f),
                    textAlign = TextAlign.Center,
                    text = "Point your camera a the QR Code",
                    color = Color.White,
                    style = appTypography().titleMedium
                )

                if(!cameraPermissionState) {
                    PermissionDialog(
                        onCloseApp = onCloseClicked,
                        onGrantAccess = {
                            shouldShowSystemPermissionsDialog = true
                        },
                        title = "Camera Required",
                        description = "This app cannot function without camera access. To scan QR codes, Please grant permission."
                    )
                }
            }
        }
    )
}





