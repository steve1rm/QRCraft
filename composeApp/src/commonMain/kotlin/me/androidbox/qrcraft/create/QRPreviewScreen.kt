@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.qrcraft.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.androidbox.qrcraft.core.presentation.responsive.WindowSizeClass
import me.androidbox.qrcraft.core.presentation.responsive.getDeviceType
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
    qrContent: String,
    isLink: Boolean
) {
    val shareManager = rememberShareManager()
    val clipboard = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    val urlHandler = LocalUriHandler.current
    val deviceType = getDeviceType()

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
                    .padding(horizontal = if(WindowSizeClass.MOBILE == deviceType) 24.dp else 0.dp)
            ) {

                QRContentLayout(
                    modifier = Modifier.then(
                        if(WindowSizeClass.TABLET == deviceType) {
                            Modifier.width(480.dp)
                        }
                        else {
                            Modifier
                        }
                    ),
                    title = title,
                    details = details,
                    qrContent = qrContent,
                    isLink = isLink,
                    onCopyClicked = {
                        coroutineScope.launch {
                           clipboard.setText(
                               buildAnnotatedString {
                                   append(details)
                               }
                           )
                        }
                    },
                    onShareClicked = {
                        shareManager.shareText(details)
                    },
                    onLinkClicked = { url ->
                        urlHandler.openUri(url)
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
            onBackClick = {},
            isLink = false
        )
    }
}