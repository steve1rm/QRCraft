package me.androidbox.qrcraft.create

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import me.androidbox.ui.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import qrcraft.composeapp.generated.resources.Res
import qrcraft.composeapp.generated.resources.show_more

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLine: Int = 3
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var isClickable by remember {
        mutableStateOf(false)
    }
    var lastCharacterIndex by remember {
        mutableIntStateOf(0)
    }
    val showMoreText = stringResource(Res.string.show_more)

    val primary = MaterialTheme.colorScheme.primary
    val textToShow = remember(text, isClickable, isExpanded) {
        buildAnnotatedString {
            when {
                isClickable && !isExpanded -> {
                    val adjustedText = text
                        .substring(
                            startIndex = 0,
                            endIndex = lastCharacterIndex
                        )
                        .dropLast(showMoreText.length + 3)
                        .dropLastWhile {
                            it.isWhitespace() || it == '.'
                        }
                    append(adjustedText)
                    append("...")

                    withStyle(
                        style = SpanStyle(
                            color = primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(showMoreText)
                    }
                }
                else -> append(text)
            }
        }
    }

    Text(
        text = textToShow,
        maxLines = if(isExpanded) Int.MAX_VALUE else collapsedMaxLine,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = isClickable,
                interactionSource = null,
                indication = null
            ) {
                isExpanded = !isExpanded
            }
            .animateContentSize(),
        onTextLayout = { result ->
            if(!isExpanded && result.hasVisualOverflow) {
                isClickable = true
                lastCharacterIndex = result.getLineEnd(lineIndex = collapsedMaxLine - 1)
            }
        }
    )
}

@Preview()
@Composable
private fun EchoExpandableTextPreview() {
    AppTheme {
        ExpandableText(
            text = buildString {
                repeat(100) {
                    append("Hello ")
                }
            }
        )
    }
}