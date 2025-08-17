package me.androidbox.qrcraft

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
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import me.androidbox.qrcraft.navigation.AppNavigation
import me.androidbox.qrcraft.navigation.QrCraftNavGraph
import me.androidbox.ui.AppTheme
import me.androidbox.ui.OnSurface
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.scan_result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()

//        ?.destination?.route


    val isScanResult = currentRoute.value?.destination?.route?.startsWith(QrCraftNavGraph.QrCraftNavigation.ScanResult::class.qualifiedName!!) == true

    LaunchedEffect(currentRoute, isScanResult){
Logger.e("currentRoute $currentRoute isScanResult $isScanResult")
    }

    AppTheme {
        Scaffold(topBar = {
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
        }) {

            AppNavigation(navController = navController)
        }


        /*      Text(
                  text = getDeviceType().name,
                  modifier = Modifier.padding(top = 100.dp)
              )*/
    }
}