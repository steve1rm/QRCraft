package me.androidbox.qrcraft.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
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

            )
        }

        composable<QrCraftNavigation.ScanResults> {
            Text("Hi Scan results screen")
        }
    }
}