package me.androidbox.qrcraft.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = QrCraftNavGraph.QrCraftNavigation
    ) {
        this.qrCraftNavigation(navHostController = navController)
    }
}