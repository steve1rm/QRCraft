package me.androidbox.qrcraft

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import me.androidbox.qrcraft.bottom_bar.CustomBottomBar
import me.androidbox.qrcraft.navigation.AppNavigation
import me.androidbox.qrcraft.navigation.QrCraftNavGraph
import me.androidbox.qrcraft.scanning.presentation.PrefDataStore
import me.androidbox.ui.AppTheme
import me.androidbox.ui.OnSurface
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.scan_result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    prefDataStore: PrefDataStore
) {

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val isScanResult = backStackEntry.value?.destination?.route?.startsWith(QrCraftNavGraph.QrCraftNavigation.ScanResult::class.qualifiedName!!) == true

    val isScanningScreen = backStackEntry.value?.destination?.route == QrCraftNavGraph.QrCraftNavigation.Scan::class.qualifiedName
    val isCreateQRChooseTypeScreenLoaded = backStackEntry.value?.destination?.route == QrCraftNavGraph.QrCraftNavigation.CreateQRChooseType::class.qualifiedName

    val isBottomBarVisible = (backStackEntry.value?.destination?.route == QrCraftNavGraph.QrCraftNavigation.Scan::class.qualifiedName) or (backStackEntry.value?.destination?.route == QrCraftNavGraph.QrCraftNavigation.CreateQRChooseType::class.qualifiedName)


    LaunchedEffect(backStackEntry, isScanResult) {
        Logger.e("currentRoute $backStackEntry isScanResult $isScanResult")
    }

    AppTheme {
        Scaffold(contentWindowInsets = WindowInsets.systemBars, topBar = {
            if (isScanResult) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.scan_result),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }

                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = OnSurface,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        }, bottomBar = {

            if (isBottomBarVisible) {
                Box(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().navigationBarsPadding()
                        .then(
                            if (isScanningScreen)
                                Modifier.background(color = Color.Transparent)
                            else
                                Modifier.background(color = MaterialTheme.colorScheme.surface)
                        ), contentAlignment = Alignment.Center
                ) {

                    CustomBottomBar(
                        onHistoryClick = {},
                        onScanClick = {
                            navController.popBackStack(
                                QrCraftNavGraph.QrCraftNavigation.Scan,
                                inclusive = false
                            )
                        },
                        onCreateQRClick = {

                            navController.navigate(QrCraftNavGraph.QrCraftNavigation.CreateQRChooseType)
                        },
                        createQRHighlight = isCreateQRChooseTypeScreenLoaded
                    )

                }
            }

        }) { padding ->
            val parentModifier = Modifier.padding(paddingValues = padding)

            AppNavigation(
                navController = navController,
                prefDataStore = prefDataStore,
                modifier = if(!isScanningScreen) parentModifier else Modifier
            )
        }
    }
}