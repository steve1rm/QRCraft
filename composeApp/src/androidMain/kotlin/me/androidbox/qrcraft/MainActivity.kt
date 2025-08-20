@file:OptIn(ExperimentalPermissionsApi::class)

package me.androidbox.qrcraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import me.androidbox.qrcraft.core.utils.qrCraftPrefDataStore
import me.androidbox.qrcraft.create.QRPreviewScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        setContent {
            val prefDataStore = remember { applicationContext.qrCraftPrefDataStore }

            QRPreviewScreen()

           /* App(
                prefDataStore = prefDataStore
            )*/
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(
        prefDataStore = LocalContext.current.qrCraftPrefDataStore
    )
}