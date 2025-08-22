@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.qrcraft.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import me.androidbox.qrcraft.core.utils.rememberShareManager
import me.androidbox.qrcraft.features.scan_result.presentation.components.QRContentLayout
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.arrow_left

@Composable
fun QRPreviewScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    title: String,
    details: String,
    qrContent: String
) {
    val shareManager = rememberShareManager()

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.onSurface,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Preview",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.arrow_left),
                            contentDescription = "Navigate back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                QRContentLayout(
                    title = title,
                    details = details,
                    qrContent = qrContent,
                    onCopyClicked = {},
                    onShareClicked = {
                        shareManager.shareText(title)
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun QRPreviewScreenPreview() {
    AppTheme {
        QRPreviewScreen(
            title = "QR Code Result",
            details = "In the grand tapestry of existence, where threads of chance and choice intertwine, the relentless march of time ushers forth an ever-changing landscape of opportunities and challenges. Consider the humble artisan, meticulously shaping raw materials into objects of beauty and utility. Their dedication, a silent testament to the enduring power of human creativity, echoes through generations. Each hammer fall, each brushstroke, each carefully considered detail contributes to a legacy far greater than the sum of its parts. It is this persistent pursuit of excellence, this unwavering commitment to craft, that often distinguishes the remarkable from the mundane.",
            qrContent = "",
            onBackClick = {}
        )
    }
}