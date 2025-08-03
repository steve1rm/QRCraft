package me.androidbox.qrcraft.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.androidbox.qrcraft.navigation.QrCraftNavGraph.QrCraftNavigation

fun NavGraphBuilder.qrCraftNavigation(navHostController: NavHostController) {
    this.navigation<QrCraftNavigation>(
        startDestination = QrCraftNavigation.Scan
    ) {
        composable<QrCraftNavigation.Scan> {
            Text("Hi Scan screen")
        }

        composable<QrCraftNavigation.ScanResults> {
            Text("Hi Scan results screen")
        }
    }
}