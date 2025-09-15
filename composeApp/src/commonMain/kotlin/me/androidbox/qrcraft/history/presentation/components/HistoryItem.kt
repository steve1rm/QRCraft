package me.androidbox.qrcraft.history.presentation.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.androidbox.qrcraft.history.presentation.model.HistoryItemsType
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HistoryItem(
    title: String,
    details: String,
    dateTime: String,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onLongClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    Card(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onItemClick()
                    },
                    onLongPress = {
                        onLongClick()
                    }
                )
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            Column(
                modifier = Modifier.wrapContentWidth()
            ) {
                icon()
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xff505F6A),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = dateTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xff8C99A2)
                )
            }
        }
    }
}

@Preview
@Composable
fun HistoryItemPreview() {
    AppTheme {
        HistoryItem(
            title = HistoryItemsType.GEOLOCATION.title,
            details = "Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum justo metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium.",
            dateTime = "31 Jul 2023 10:24",
            icon = {
                Icon(
                    imageVector = vectorResource(HistoryItemsType.GEOLOCATION.image),
                    contentDescription = "Info"
                )
            },
            onLongClick = {},
            onItemClick = {}
        )
    }
}
