package me.androidbox.qrcraft.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.androidbox.ui.LinkBG
import qrcraft.composeapp.generated.resources.Res

@Composable
fun CustomBottomBar(
    onHistoryClick: () -> Unit,
    onScanClick: () -> Unit,
    onCreateQRClick: () -> Unit,
    historyHighlight: Boolean = false,
    createQRHighlight: Boolean
) {

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Color.White)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onHistoryClick,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        if (historyHighlight) LinkBG else Color.Transparent, CircleShape
                    ).clip(CircleShape)
            ) {
                AsyncImage(
                    model = Res.getUri("files/clock-refresh.svg"),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            }

            Spacer(modifier = Modifier.width(80.dp))

            IconButton(
                onClick = onCreateQRClick,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        if (createQRHighlight) LinkBG else Color.Transparent, CircleShape
                    )
                    .clip(CircleShape)
            ) {
                AsyncImage(
                    model = Res.getUri("files/plus.svg"),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            }
        }

        IconButton(
            onClick = onScanClick,
            modifier = Modifier
                .size(68.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .clip(CircleShape)
                .align(Alignment.Center)
        ) {
            AsyncImage(
                model = Res.getUri("files/scan.svg"),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
            )
        }
    }
}
