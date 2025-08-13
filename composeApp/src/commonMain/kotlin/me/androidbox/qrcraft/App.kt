package me.androidbox.qrcraft

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.rememberNavController
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
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    val isScanResult = currentRoute?.startsWith(QrCraftNavGraph.QrCraftNavigation.ScanResult::class.qualifiedName!!) == true

    AppTheme {
        Scaffold(topBar = {
            if (isScanResult) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.scan_result),
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
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