package me.androidbox.qrcraft.features.scan_result.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerticalAlignBottom
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import coil3.compose.AsyncImage
import me.androidbox.qrcraft.core.data.MAX_CHARACTER_LENGTH
import me.androidbox.qrcraft.core.utils.rememberShareManager
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.QRType
import me.androidbox.qrcraft.features.scan_result.domain.detectQRContentType
import me.androidbox.qrcraft.features.scan_result.domain.extractQRContent
import me.androidbox.qrcraft.features.scan_result.domain.toDisplayName
import me.androidbox.ui.AppShapes
import me.androidbox.ui.GrayTxtFldHint
import me.androidbox.ui.OnSurface
import me.androidbox.ui.OnSurfaceAlt
import me.androidbox.ui.OnSurfaceDisabled
import me.androidbox.ui.Surface
import org.jetbrains.compose.resources.stringResource
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.cd_save_qr_as_image
import qrcraft.composeapp.generated.resources.image_saved_to_downloads
import qrcraft.composeapp.generated.resources.save
import qrcraft.composeapp.generated.resources.show_less
import qrcraft.composeapp.generated.resources.show_more
import qrgenerator.QRCodeImage


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScanResultScreen(
    id: Int = 0,
    scannedQrCode: String,
    qrEntryViewModel: QREntryViewModel,
    title: String? = null,
    qrType: QRType = QRType.SCANNED,
    onShowSnackBar: (message: String) -> Unit
) {
    Logger.e("scannedCode $scannedQrCode")

    val shareManager = rememberShareManager()
    val clipboard = LocalClipboardManager.current
    val uriHandler = LocalUriHandler.current
    val focusManager = LocalFocusManager.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val imageSavedToDownloadsMessage = stringResource(Res.string.image_saved_to_downloads)

    var qrContentType by remember {
        mutableStateOf(QRContentType.UNDEFINED)
    }
    val qrContentTypeDisplayable = qrContentType.toDisplayName()

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


    var currentQrContentType by remember {
        mutableStateOf(title ?: "")
    }



    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->

            if (event == androidx.lifecycle.Lifecycle.Event.ON_STOP) {
                qrEntryViewModel.addQREntry(
                    id = id,
                    title = currentQrContentType.ifEmpty { qrContentTypeDisplayable },
                    contentType = qrContentType,
                    content = qrContent,
                    qrType = qrType
                )
            }

        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)

        }
    }

    val allEntries by qrEntryViewModel.allEntries.collectAsStateWithLifecycle()
    LaunchedEffect(allEntries) {
        Logger.e("allEntries ${allEntries.size}")
        allEntries.forEach { entry ->
            Logger.e("allEntries ${entry.createdAt}")
        }
    }


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

            TextField(
                value = currentQrContentType,
                onValueChange = { changedValue ->
                    if (changedValue.length <= MAX_CHARACTER_LENGTH)
                        currentQrContentType = changedValue
                },
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                ),
                placeholder = {
                    Row(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            qrContentType.toDisplayName(),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }

                },
                modifier = Modifier.padding(top = 64.dp, start = 16.dp, end = 16.dp).fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = OnSurface,
                    focusedTextColor = OnSurface,
                    unfocusedContainerColor = Surface,
                    focusedContainerColor = Surface,
                    unfocusedPlaceholderColor = GrayTxtFldHint,
                    focusedPlaceholderColor = GrayTxtFldHint,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        /*       qrEntryViewModel.addQREntry(
                                   contentType = currentQrContentType.ifEmpty { qrContentType.name },
                                   content = qrContent
                               )*/
                    },
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )


            Text(
                text = qrContent,
                style = MaterialTheme.typography.bodyLarge,
                color = OnSurface,
                modifier = Modifier.wrapContentWidth().wrapContentHeight()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp).then(
                        if (qrContentType == QRContentType.LINK) Modifier.background(MaterialTheme.colorScheme.primary)
                            .clickable {
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
                    modifier = Modifier.size(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = OnSurface,
                        containerColor = Color.White
                    ), shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)

                ) {

                    AsyncImage(
                        model = Res.getUri("files/share.svg"),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )

                }
                Spacer(modifier = Modifier.width(8.dp))


                Button(
                    onClick = {

                        clipboard.setText(buildAnnotatedString { append(text = qrContent) })

                    },
                    modifier = Modifier.size(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = OnSurface,
                        containerColor = Color.White
                    ),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {


                    AsyncImage(
                        model = Res.getUri("files/copy.svg"),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )


                }

                Spacer(modifier = Modifier.width(8.dp))



                Button(
                    onClick = {
                    //TODO rainxchzed insert steve save function. When save is successfull call onShowSnackBar(imageSavedToDownloadsMessage)


                        onShowSnackBar(imageSavedToDownloadsMessage)
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp).weight(2f)
                        .padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = OnSurface,
                        containerColor = Color.White
                    )

                ) {
                    Image(
                        imageVector = Icons.Default.VerticalAlignBottom,
                        contentDescription = stringResource(Res.string.cd_save_qr_as_image),
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = stringResource(Res.string.save),
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