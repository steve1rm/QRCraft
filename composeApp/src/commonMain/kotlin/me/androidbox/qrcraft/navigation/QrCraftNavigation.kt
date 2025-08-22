package me.androidbox.qrcraft.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import me.androidbox.qrcraft.create.QRPreviewScreen
import me.androidbox.qrcraft.features.create_qr.choose_type.CreateQRChooseTypeScreen
import me.androidbox.qrcraft.features.create_qr.choose_type.CreateQRScreenRoot
import me.androidbox.qrcraft.features.scan_result.presentation.ScanResultScreen
import me.androidbox.qrcraft.navigation.QrCraftNavGraph.QrCraftNavigation
import me.androidbox.qrcraft.permissions.PermissionsViewModel
import me.androidbox.qrcraft.scanning.presentation.PrefDataStore
import me.androidbox.qrcraft.scanning.presentation.ScanningScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.qrCraftNavigation(
    navHostController: NavHostController,
    prefDataStore: PrefDataStore) {
    this.navigation<QrCraftNavigation>(
        startDestination = QrCraftNavigation.Scan
    ) {
        composable<QrCraftNavigation.Scan> {

            val factory = rememberPermissionsControllerFactory()
            val permissionController = remember(factory) {
                factory.createPermissionsController()
            }

            BindEffect(permissionController)
            val permissionsViewModel = viewModel(initializer = {
                PermissionsViewModel(permissionController)
            })

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

        composable<QrCraftNavigation.ScanResult> {
            val scanResultsRoute = it.toRoute<QrCraftNavigation.ScanResult>()
            ScanResultScreen(scannedQrCode = scanResultsRoute.scannedQrCode)
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
                    navHostController.navigate(QrCraftNavigation.QrPreview(
                        scannedQrCode = result,
                        title = "Title",
                        details = "Details"
                    ))

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

            QRPreviewScreen(
                title = qrContentRoute.title,
                details = qrContentRoute.details,
                qrContent = qrContentRoute.scannedQrCode,
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}