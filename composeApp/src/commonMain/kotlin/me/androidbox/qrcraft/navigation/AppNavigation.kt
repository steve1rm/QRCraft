package me.androidbox.qrcraft.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import me.androidbox.qrcraft.scanning.presentation.PrefDataStore

@Composable
fun AppNavigation(
    navController: NavHostController,
    prefDataStore: PrefDataStore,
    modifier: Modifier) {

    NavHost(
        navController = navController,
        startDestination = QrCraftNavGraph.QrCraftNavigation,
        modifier = modifier
    ) {
        this.qrCraftNavigation(
            navHostController = navController,
            prefDataStore = prefDataStore)
    }
}