package me.androidbox.qrcraft.features.scan_result.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.compose_multiplatform
import qrcraft.composeapp.generated.resources.copy
import qrcraft.composeapp.generated.resources.share

@Composable
fun QRContentLayout(
    modifier: Modifier = Modifier,
    title: String,
    details: String,
    onShareClicked: () -> Unit,
    onCopyClicked: () -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter // helps align children easily
    ) {
        Column(
            modifier = modifier
                .padding(top = 100.dp) // add padding so content doesn't overlap with image
                .background(
                    color = MaterialTheme.colorScheme.background,
                    RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(124.dp))

            Text(
                text = title
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = details
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onShareClicked
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.share),
                        contentDescription = "Share"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Share")
                }

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = onShareClicked
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.copy),
                        contentDescription = "Copy"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Copy")
                }
            }
        }

        Image(
            painter = painterResource(Res.drawable.compose_multiplatform), // adjust to your resource
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter) // centers image horizontally at top of Box
                .offset(y = -(0).dp) // move half of image height upwards to overlap
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}


@Preview
@Composable
fun QRContentLayoutPreview() {
    AppTheme {
        QRContentLayout(
            title = "QR Code Result",
            details = "In the grand tapestry of existence, where threads of chance and choice intertwine, the relentless march of time ushers forth an ever-changing landscape of opportunities and challenges. Consider the humble artisan, meticulously shaping raw materials into objects of beauty and utility. Their dedication, a silent testament to the enduring power of human creativity, echoes through generations. Each hammer fall, each brushstroke, each carefully considered detail contributes to a legacy far greater than the sum of its parts. It is this persistent pursuit of excellence, this unwavering commitment to craft, that often distinguishes the remarkable from the mundane.",
            onShareClicked = {},
            onCopyClicked = {}
        )
    }
}

