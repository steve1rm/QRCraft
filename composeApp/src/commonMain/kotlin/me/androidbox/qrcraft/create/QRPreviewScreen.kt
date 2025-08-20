@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.qrcraft.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import me.androidbox.qrcraft.features.scan_result.presentation.components.QRContentLayout
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.tick

@Composable
fun QRPreviewScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Preview"
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.tick),
                        contentDescription = "Navigate back"
                    )
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                QRContentLayout(
                    title = "",
                    details = "",
                    onCopyClicked = {},
                    onShareClicked = {}
                )
            }
        }
    )
}

@Preview
@Composable
fun QRPreviewScreenPreview() {
    AppTheme {
        QRPreviewScreen()
    }
}