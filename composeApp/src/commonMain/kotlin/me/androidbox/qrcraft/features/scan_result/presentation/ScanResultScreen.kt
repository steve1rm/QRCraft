package me.androidbox.qrcraft.features.scan_result.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.touchlab.kermit.Logger
import coil3.compose.AsyncImage
import me.androidbox.qrcraft.core.utils.rememberShareManager
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.detectQRContentType
import me.androidbox.qrcraft.features.scan_result.domain.extractQRContent
import me.androidbox.qrcraft.features.scan_result.domain.toDisplayName
import me.androidbox.ui.AppShapes
import me.androidbox.ui.OnSurface
import me.androidbox.ui.OnSurfaceAlt
import me.androidbox.ui.OnSurfaceDisabled
import me.androidbox.ui.Surface
import org.jetbrains.compose.resources.stringResource
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.copy
import qrcraft.composeapp.generated.resources.share
import qrcraft.composeapp.generated.resources.show_less
import qrcraft.composeapp.generated.resources.show_more
import qrgenerator.QRCodeImage


@Composable
fun ScanResultScreen(scannedQrCode: String) {
    Logger.e("scannedCode $scannedQrCode")

    var qrContentType by remember {
        mutableStateOf(QRContentType.UNDEFINED)
    }

    LaunchedEffect(scannedQrCode) {
        qrContentType = detectQRContentType(scannedQrCode = scannedQrCode)
    }

    var qrContent by remember {
        mutableStateOf("")
    }
    LaunchedEffect(qrContentType) {
        if (qrContentType != QRContentType.UNDEFINED) {
            qrContent =
                extractQRContent(scannedQRCode = scannedQrCode, qrContentType = qrContentType)
        }
    }


    var isTextExpanded by remember { mutableStateOf(false) }

    val maxLines = when {
        qrContentType != QRContentType.TEXT -> Int.MAX_VALUE
        isTextExpanded -> Int.MAX_VALUE
        else -> 6
    }

    var isMaxLinesExceeded by remember { mutableStateOf(false) }

    val shareManager = rememberShareManager()
    val clipboard = LocalClipboardManager.current


    val uriHandler = LocalUriHandler.current


    ConstraintLayout(modifier = Modifier.fillMaxSize().background(OnSurface)) {

        val (colScannedQR, colInfo) = createRefs()
        val startGuideline = createGuidelineFromStart(0.1f)
        val endGuideline = createGuidelineFromEnd(0.1f)


        Column(
            modifier = Modifier.constrainAs(colInfo) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }.background(Surface, AppShapes.large),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                qrContentType.toDisplayName(),
                style = MaterialTheme.typography.titleMedium,
                color = OnSurface,
                modifier = Modifier.padding(top = 64.dp, start = 16.dp, end = 16.dp)
            )
            Text(
                text = qrContent,
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurface,
                modifier = Modifier.wrapContentWidth().wrapContentHeight()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp).then(
                        if (qrContentType == QRContentType.LINK) Modifier.background(MaterialTheme.colorScheme.primary).clickable{
uriHandler.openUri(uri = qrContent)
                        } else Modifier
                    ),
                onTextLayout = { layoutResult ->
                    isMaxLinesExceeded = layoutResult.hasVisualOverflow
                },
                textAlign = if (qrContentType == QRContentType.TEXT) TextAlign.Start else TextAlign.Center,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )

            if (!isTextExpanded && isMaxLinesExceeded) {
                Text(
                    text = stringResource(Res.string.show_more),
                    color = OnSurfaceAlt,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 16.dp, end = 16.dp)
                        .clickable { isTextExpanded = true }
                        .align(Alignment.Start)
                )
            } else if (isTextExpanded) {
                Text(
                    text = stringResource(Res.string.show_less),
                    color = OnSurfaceDisabled,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 16.dp, end = 16.dp)
                        .clickable { isTextExpanded = false }
                        .align(Alignment.Start)
                )
            }


            Row(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(all = 16.dp)) {
                Button(
                    onClick = { shareManager.shareText(text = qrContent) },
                    modifier = Modifier.minimumInteractiveComponentSize().weight(1f)
                        .padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = OnSurface,
                        containerColor = Color.White
                    )

                ) {

                    AsyncImage(
                        model = Res.getUri("files/share.svg"),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        text = stringResource(Res.string.share),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Button(
                    onClick = {

                        clipboard.setText(buildAnnotatedString { append(text = qrContent) })

                    },
                    modifier = Modifier.minimumInteractiveComponentSize().weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = OnSurface,
                        containerColor = Color.White
                    )
                ) {


                    AsyncImage(
                        model = Res.getUri("files/copy.svg"),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(
                        text = stringResource(Res.string.copy),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(start = 8.dp)
                    )


                }
            }


        }


        Box(
            modifier = Modifier.size(160.dp).background(Color.White, AppShapes.large)
                .constrainAs(colScannedQR) {
                    top.linkTo(colInfo.top, (-120).dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                }) {

            if (qrContent.isNotEmpty())
                QRCodeImage(
                    modifier = Modifier.size(140.dp).align(Alignment.Center),
                    url = qrContent,
                    contentDescription = "Scanned QR Code"
                )
        }

    }

}