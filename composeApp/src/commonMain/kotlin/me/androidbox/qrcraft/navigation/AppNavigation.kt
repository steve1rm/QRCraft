package me.androidbox.qrcraft.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.androidbox.qrcraft.scanning.presentation.PrefDataStore

@Composable
fun AppNavigation(
    navController: NavHostController,
    prefDataStore: PrefDataStore) {

    NavHost(
        navController = navController,
        startDestination = QrCraftNavGraph.QrCraftNavigation
    ) {
        this.qrCraftNavigation(
            navHostController = navController,
            prefDataStore = prefDataStore)
    }
}