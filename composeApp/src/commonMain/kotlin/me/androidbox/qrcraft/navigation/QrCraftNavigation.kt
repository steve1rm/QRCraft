package me.androidbox.qrcraft.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import me.androidbox.qrcraft.features.scan_result.presentation.ScanResultScreen
import me.androidbox.qrcraft.navigation.QrCraftNavGraph.QrCraftNavigation
import me.androidbox.qrcraft.scanning.presentation.ScanningScreen

fun NavGraphBuilder.qrCraftNavigation(navHostController: NavHostController) {
    this.navigation<QrCraftNavigation>(
        startDestination = QrCraftNavigation.Scan
    ) {
        composable<QrCraftNavigation.Scan> {
            ScanningScreen(
                onCloseClicked = {
                    navHostController.popBackStack<QrCraftNavigation.Scan>(
                        inclusive = true
                    )
                },
                onNavigateToScanResult = { scannedQRCode ->
                    navHostController.navigate(QrCraftNavigation.ScanResult(scannedQRCode))
                }

            )
        }

        composable<QrCraftNavigation.ScanResult> {
            val scanResultsRoute = it.toRoute<QrCraftNavigation.ScanResult>()
            ScanResultScreen(scannedQrCode = scanResultsRoute.scannedQrCode)
        }
    }
}