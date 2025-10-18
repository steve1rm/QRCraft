package me.androidbox.qrcraft.scanning.presentation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import co.touchlab.kermit.Logger
import com.kashif.cameraK.controller.CameraController
import com.kashif.cameraK.enums.CameraLens
import com.kashif.cameraK.enums.Directory
import com.kashif.cameraK.enums.ImageFormat
import com.kashif.cameraK.enums.QualityPrioritization
import com.kashif.cameraK.enums.TorchMode
import com.kashif.cameraK.ui.CameraPreview
import com.kashif.qrscannerplugin.rememberQRScannerPlugin
import dev.icerock.moko.permissions.PermissionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.ImagePicker
import me.androidbox.qrcraft.permissions.PermissionDialog
import me.androidbox.qrcraft.scanQRFromImage
import me.androidbox.qrcraft.scanning.presentation.components.CustomSnackBarVisuals
import me.androidbox.qrcraft.scanning.presentation.components.CustomSnackbar
import me.androidbox.qrcraft.scanning.presentation.components.ErrorDialog
import me.androidbox.qrcraft.scanning.presentation.components.ScanningSurfaceRoundedCorners
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.tick

@Composable
fun ScanningScreen(
    onCloseClicked: () -> Unit,
    onProvidePermission: () -> Unit,
    modifier: Modifier = Modifier,
    permissionState: PermissionState,
    onNavigateToScanResult: (String) -> Unit,
    prefDataStore: PrefDataStore,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var cameraController by remember {
        mutableStateOf<CameraController?>(null)
    }

    var shouldShowSystemPermissionsDialog by remember {
        mutableStateOf(false)
    }

    var showQrCraftPermissionDialog by remember {
        mutableStateOf(permissionState != PermissionState.Granted)
    }

    var isFlashlightOn by rememberSaveable {
        mutableStateOf(false)
    }

    var showErrorDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val imagePicker = remember { ImagePicker() }
    imagePicker.pickImage { uri ->
        coroutineScope.launch {
            try {
                val qrResult = scanQRFromImage(uri.toString())
                if (qrResult != null) {
                    onNavigateToScanResult(qrResult)
                } else {
                    showErrorDialog = true
                    // Show error snackbar

                }
            } catch (e: Exception) {
                showErrorDialog = true

                // Show error snackbar
            }
        }
    }

    var hasShownSnackBarOnce by remember { mutableStateOf(false) }
    val qrScannerPlugin = rememberQRScannerPlugin(coroutineScope)

    LaunchedEffect(isFlashlightOn) {
//        cameraController?.setFlashMode(if (isFlashlightOn) FlashMode.ON else FlashMode.OFF)
        cameraController?.setTorchMode(if (isFlashlightOn) TorchMode.ON else TorchMode.OFF)
    }

    LaunchedEffect(Unit) {
        Logger.d(
            tag = "Scanned code",
            messageString = "qrCode LaunchedEffect"
        )

        qrScannerPlugin
            .getQrCodeFlow()
            .distinctUntilChanged()
            .collectLatest { qrCode ->
                Logger.d(
                    tag = "Scanned code",
                    messageString = "qrCode $qrCode"
                )

                qrScannerPlugin.pauseScanning()
                onNavigateToScanResult(qrCode)
            }
    }

    if (shouldShowSystemPermissionsDialog) {
        onProvidePermission()
    }

    showQrCraftPermissionDialog = when (permissionState) {
        PermissionState.Granted -> {
            coroutineScope.launch {
                val hasShown =
                    prefDataStore.data.firstOrNull()?.get(booleanPreferencesKey("snackbar")) == true

                if (!hasShown) {
                    snackbarHostState.showSnackbar(
                        CustomSnackBarVisuals(
                            message = "Camera permission granted",
                            duration = SnackbarDuration.Short,
                            drawableResource = Res.drawable.tick,
                            containerColor = Color(0xff4caf50),
                            contentColor = Color(0xFF273037)
                        )
                    )

                    prefDataStore.edit {
                        it.set(booleanPreferencesKey("snackbar"), value = true)
                    }
                }
            }

            false
        }

        else -> {
            true
        }
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
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val boxHeight = this.maxHeight

                ScanningSurfaceRoundedCorners(
                    modifier = Modifier.fillMaxSize(),
                    surfaceRadius = 18.dp,
                    lineColor = Color.Yellow,
                    lineStrokeWidth = 5.dp,
                    lineExtensionLength = 32.dp,
                    content = { modifier ->
                        CameraPreview(
                            cameraConfiguration = {
                                setCameraLens(CameraLens.BACK)
//                                setFlashMode(FlashMode.OFF)
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
                            },
                            modifier = modifier
                        )
                    })

                IconButton(
                    onClick = {
                        isFlashlightOn = !isFlashlightOn
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = if (isFlashlightOn) {
                            MaterialTheme.colorScheme.primary
                        } else MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                        .statusBarsPadding()
                ) {
                    Icon(
                        imageVector = if (isFlashlightOn) Icons.Outlined.FlashOff else Icons.Outlined.FlashOn,
                        contentDescription = if (isFlashlightOn) "Turn off flashlight" else "Turn on flashlight"
                    )
                }
                IconButton(
                    onClick = {
                        imagePicker.launch()
                    },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                        .statusBarsPadding()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Image,
                        contentDescription = "Pick image from gallery"
                    )
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.TopCenter)
                        .padding(top = boxHeight * 0.20f),
                    textAlign = TextAlign.Center,
                    text = "Point your camera a the QR Code",
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall
                )

                if (showQrCraftPermissionDialog) {
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

    if (showErrorDialog) {
        ErrorDialog(
            message = "No QR-codes found",
            onDismissRequest = {
                showErrorDialog = false
            }
        )
    }
}

@Preview
@Composable
fun ScanningScreenPreview() {
    AppTheme {

/*
        ScanningScreen(
            onCloseClicked = {},
            onNavigateToScanResult = {},
            permissionState = PermissionState.NotDetermined,
            onProvidePermission = {},
            prefDataStore = FIXME
        )
*/
    }
}

