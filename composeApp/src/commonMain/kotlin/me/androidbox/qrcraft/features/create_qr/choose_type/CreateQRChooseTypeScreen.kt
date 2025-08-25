package me.androidbox.qrcraft.features.create_qr.choose_type

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import co.touchlab.kermit.Logger
import me.androidbox.qrcraft.core.presentation.responsive.WindowSizeClass
import me.androidbox.qrcraft.core.presentation.responsive.getDeviceType
import me.androidbox.qrcraft.features.create_qr.choose_type.components.QRTypeButton
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.ui.AppShapes
import org.jetbrains.compose.resources.stringResource
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.create_qr

@Composable
fun CreateQRChooseTypeScreen(
    onNavigateToCreateQR: (QRContentType) -> Unit,
){

    val columns = if (getDeviceType() == WindowSizeClass.MOBILE) 2 else 3


    ConstraintLayout(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {

        val (txtCreateQr,lazyVerticalGrid) = createRefs()
        val startGuideline = createGuidelineFromStart(0.05f)
        val endGuideline = createGuidelineFromEnd(0.05f)


        Text(text = stringResource(Res.string.create_qr), color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.titleMedium, // Also here need change style here too <--
            modifier = Modifier.constrainAs(txtCreateQr){
                top.linkTo(parent.top,8.dp)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }, textAlign = TextAlign.Center
        )

        LazyVerticalGrid(columns = GridCells.Fixed(columns),
           verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.constrainAs(lazyVerticalGrid){
                top.linkTo(txtCreateQr.bottom,16.dp)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }){
            items(items= QRContentType.validEntries){ qrContentType ->

                QRTypeButton(qrContentType = qrContentType,onClick = {selectedQRContentType ->
                    Logger.e("selected $selectedQRContentType")
                    onNavigateToCreateQR(selectedQRContentType)
                }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.onSurface), shape = AppShapes.large)

            }
        }
    }
}