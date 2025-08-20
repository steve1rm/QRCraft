package me.androidbox.qrcraft.features.scan_result.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.copy1
import qrcraft.composeapp.generated.resources.share1

@Composable
fun QRContentLayout(
    modifier: Modifier = Modifier,
    title: String,
    details: String,
    onShareClicked: () -> Unit,
    onCopyClicked: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title
        )

        Text(
            text = details
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = onShareClicked
            ) {
               Icon(
                   imageVector = vectorResource(Res.drawable.share1),
                    contentDescription = "Share"
                )

                Text(
                    text = "Share"
                )
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = onShareClicked
            ) {
               Icon(
                    imageVector = vectorResource(Res.drawable.copy1),
                    contentDescription = "Share"
                )

                Text(
                    text = "Copy"
                )
            }
        }
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

