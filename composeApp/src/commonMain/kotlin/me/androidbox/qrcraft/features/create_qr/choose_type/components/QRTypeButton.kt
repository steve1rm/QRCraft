package me.androidbox.qrcraft.features.create_qr.choose_type.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.androidbox.qrcraft.features.scan_result.domain.QRContentType
import me.androidbox.qrcraft.features.scan_result.domain.toDisplayName
import me.androidbox.qrcraft.features.scan_result.domain.toSvgResource
import qrcraft.composeapp.generated.resources.Res

@Composable
fun QRTypeButton(
    qrContentType: QRContentType,
    onClick: (QRContentType) -> Unit,
    colors: ButtonColors,
    shape: Shape
) {


    Button(
        onClick = { onClick(qrContentType) },
        modifier = Modifier.fillMaxSize(),
        colors = colors,
        shape = shape,
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = Res.getUri(qrContentType.toSvgResource()),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
            )
            Text(
                text = qrContentType.toDisplayName(),
                style = MaterialTheme.typography.titleSmall, // Denis sorry there was change in theme so i have to change this style unless we have vewy bad ui :)
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 16.dp),
                textAlign = TextAlign.Center
            )

        }
    }
}