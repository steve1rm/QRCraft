package me.androidbox.qrcraft.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.create.QRPreviewScreen
import me.androidbox.qrcraft.features.create_qr.choose_type.CreateQRChooseTypeScreen
import me.androidbox.qrcraft.features.create_qr.choose_type.CreateQRScreenRoot
import me.androidbox.qrcraft.features.scan_result.data.SaveQRCraft
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.detectQRContentType
import me.androidbox.qrcraft.features.scan_result.domain.extractQRContent
import me.androidbox.qrcraft.features.scan_result.domain.toDisplayName
import me.androidbox.qrcraft.features.scan_result.presentation.QREntryViewModel
import me.androidbox.qrcraft.features.scan_result.presentation.ScanResultScreen
import me.androidbox.qrcraft.history.presentation.HistoryRoot
import me.androidbox.qrcraft.navigation.QrCraftNavGraph.QrCraftNavigation
import me.androidbox.qrcraft.permissions.PermissionsViewModel
import me.androidbox.qrcraft.scanning.presentation.PrefDataStore
import me.androidbox.qrcraft.scanning.presentation.ScanningScreen
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import qrgenerator.generateQrCode

fun NavGraphBuilder.qrCraftNavigation(
    navHostController: NavHostController,
    prefDataStore: PrefDataStore,
    qrEntryViewModel: QREntryViewModel,
    onShowSnackBar: (message: String) -> Unit
) {
    this.navigation<QrCraftNavigation>(
        startDestination = QrCraftNavigation.Scan
    ) {
        composable<QrCraftNavigation.Scan> {

            val factory = rememberPermissionsControllerFactory()

            val permissionsViewModel = viewModel(initializer = {
                PermissionsViewModel(factory.createPermissionsController())
            })
            BindEffect(permissionsViewModel.permissionsController)

            ScanningScreen(
                onCloseClicked = {
                    navHostController.popBackStack<QrCraftNavigation.Scan>(
                        inclusive = true
                    )
                },
                onNavigateToScanResult = { scannedQRCode ->
                    navHostController.navigate(QrCraftNavigation.ScanResult(scannedQRCode))
                },
                permissionState = permissionsViewModel.permissionState,
                onProvidePermission = {
                    permissionsViewModel.provideOrRequestCameraPermission()
                },
                prefDataStore = prefDataStore
            )
        }

        composable<QrCraftNavigation.History> {
            HistoryRoot(
                onNavigateToScanResult = { id, scanned, title, qrType ->
                    navHostController.navigate(
                        route = QrCraftNavigation.QrPreview(
                            scannedQrCode = scanned,
                            title = title,
                            details = ""
                        )
                    )
                }
            )
        }

        composable<QrCraftNavigation.ScanResult> {
            val scanResultsRoute = it.toRoute<QrCraftNavigation.ScanResult>()
            ScanResultScreen(
                id = scanResultsRoute.id,
                scannedQrCode = scanResultsRoute.scannedQrCode,
                qrEntryViewModel = qrEntryViewModel,
                title = scanResultsRoute.title,
                qrType = scanResultsRoute.qrType,
                onShowSnackBar = onShowSnackBar
            )
        }

        composable<QrCraftNavigation.CreateQRChooseType> {
            CreateQRChooseTypeScreen(
                onNavigateToCreateQR = {
                    navHostController.navigate(QrCraftNavigation.CreateQR(it))
                }
            )
        }

        composable<QrCraftNavigation.CreateQR> { backStackEntry ->
            val args = backStackEntry.toRoute<QrCraftNavigation.CreateQR>()
            CreateQRScreenRoot(
                onNavigateBack = {
                    navHostController.navigateUp()
                },
                onNavigateToResult = { result ->
                    navHostController.navigate(
                        QrCraftNavigation.QrPreview(
                            scannedQrCode = result,
                            title = "Title",
                            details = "Details"
                        )
                    )

                },
                viewModel = koinViewModel(
                    parameters = {
                        parametersOf(
                            args.type
                        )
                    }
                )
            )
        }

        composable<QrCraftNavigation.QrPreview> {
            val qrContentRoute = it.toRoute<QrCraftNavigation.QrPreview>()

            val qrContentType = detectQRContentType(scannedQrCode = qrContentRoute.scannedQrCode)
            val qrContent = extractQRContent(
                scannedQRCode = qrContentRoute.scannedQrCode,
                qrContentType = qrContentType
            )

            val saveQRCraft = koinInject<SaveQRCraft>()
            val coroutineScope = koinInject<CoroutineScope>()

            QRPreviewScreen(
                title = qrContentType.toDisplayName(),
                contentType = qrContentType,
                details = qrContent,
                qrContent = qrContentRoute.scannedQrCode,
                isLink = qrContentType == QRContentType.LINK,
                isText = qrContentType == QRContentType.TEXT,
                onBackClick = {
                    navHostController.popBackStack()
                },
                onSave = {
                    generateQrCode(
                        url = qrContent,
                        onSuccess = { _, imageBitmap ->
                            if(imageBitmap != null) {
                                coroutineScope.launch {
                                    saveQRCraft.save(imageBitmap, "qrcaft")
                                }
                            }
                        },
                        onFailure = {

                        })
                }
            )
        }
    }
}