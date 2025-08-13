package me.androidbox.qrcraft.scanning.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI

@Composable
fun ScanningSurfaceRoundedCorners(
    // New name for clarity
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    surfaceRadius: Dp = 18.dp,     // Radius of the main Surface and the arcs
    lineColor: Color = Color.Yellow,
    lineStrokeWidth: Dp = 5.dp,
    lineExtensionLength: Dp = 15.dp, // How long the straight part extends from the arc
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(surfaceRadius)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent() // Draw the CameraPreview first

                    val strokePx = lineStrokeWidth.toPx()
                    val radiusPx = surfaceRadius.toPx()
                    val extensionPx = lineExtensionLength.toPx()

                    val strokeStyle = Stroke(width = strokePx, cap = StrokeCap.Round)

                    // Helper to convert angle from degrees to radians
                    fun Float.toRadians() = this * PI.toFloat() / 180f

                    // Top-Left Corner
                    // Arc goes from 180 degrees to 270 degrees
                    drawArc(
                        color = lineColor,
                        startAngle = 180f,
                        sweepAngle = 90f,
                        useCenter = false, // We want a stroke, not a pie slice
                        topLeft = Offset(strokePx / 2f, strokePx / 2f),
                        size = Size(radiusPx * 2 - strokePx, radiusPx * 2 - strokePx),
                        style = strokeStyle
                    )
                    // Horizontal tail
                    drawLine(
                        color = lineColor,
                        start = Offset(radiusPx, strokePx / 2f),
                        end = Offset(radiusPx + extensionPx, strokePx / 2f),
                        strokeWidth = strokePx,
                        cap = StrokeCap.Round
                    )
                    // Vertical tail
                    drawLine(
                        color = lineColor,
                        start = Offset(strokePx / 2f, radiusPx),
                        end = Offset(strokePx / 2f, radiusPx + extensionPx),
                        strokeWidth = strokePx,
                        cap = StrokeCap.Round
                    )

                    // Top-Right Corner
                    // Arc goes from 270 degrees to 360 (or 0) degrees
                    drawArc(
                        color = lineColor,
                        startAngle = 270f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(size.width - radiusPx * 2 + strokePx / 2f, strokePx / 2f),
                        size = Size(radiusPx * 2 - strokePx, radiusPx * 2 - strokePx),
                        style = strokeStyle
                    )
                    // Horizontal tail
                    drawLine(
                        color = lineColor,
                        start = Offset(size.width - radiusPx, strokePx / 2f),
                        end = Offset(size.width - radiusPx - extensionPx, strokePx / 2f),
                        strokeWidth = strokePx,
                        cap = StrokeCap.Round
                    )
                    // Vertical tail
                    drawLine(
                        color = lineColor,
                        start = Offset(size.width - strokePx / 2f, radiusPx),
                        end = Offset(size.width - strokePx / 2f, radiusPx + extensionPx),
                        strokeWidth = strokePx,
                        cap = StrokeCap.Round
                    )

                    // Bottom-Left Corner
                    // Arc goes from 90 degrees to 180 degrees
                    drawArc(
                        color = lineColor,
                        startAngle = 90f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(strokePx / 2f, size.height - radiusPx * 2 + strokePx / 2f),
                        size = Size(radiusPx * 2 - strokePx, radiusPx * 2 - strokePx),
                        style = strokeStyle
                    )
                    // Horizontal tail
                    drawLine(
                        color = lineColor,
                        start = Offset(radiusPx, size.height - strokePx / 2f),
                        end = Offset(radiusPx + extensionPx, size.height - strokePx / 2f),
                        strokeWidth = strokePx,
                        cap = StrokeCap.Round
                    )
                    // Vertical tail
                    drawLine(
                        color = lineColor,
                        start = Offset(strokePx / 2f, size.height - radiusPx),
                        end = Offset(strokePx / 2f, size.height - radiusPx - extensionPx),
                        strokeWidth = strokePx,
                        cap = StrokeCap.Round
                    )

                    // Bottom-Right Corner
                    // Arc goes from 0 degrees to 90 degrees
                    drawArc(
                        color = lineColor,
                        startAngle = 0f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(
                            size.width - radiusPx * 2 + strokePx / 2f,
                            size.height - radiusPx * 2 + strokePx / 2f
                        ),
                        size = Size(radiusPx * 2 - strokePx, radiusPx * 2 - strokePx),
                        style = strokeStyle
                    )
                    // Horizontal tail
                    drawLine(
                        color = lineColor,
                        start = Offset(size.width - radiusPx, size.height - strokePx / 2f),
                        end = Offset(
                            size.width - radiusPx - extensionPx,
                            size.height - strokePx / 2f
                        ),
                        strokeWidth = strokePx,
                        cap = StrokeCap.Round
                    )
                    // Vertical tail
                    drawLine(
                        color = lineColor,
                        start = Offset(size.width - strokePx / 2f, size.height - radiusPx),
                        end = Offset(
                            size.width - strokePx / 2f,
                            size.height - radiusPx - extensionPx
                        ),
                        strokeWidth = strokePx,
                        cap = StrokeCap.Round
                    )
                }
        ) {
            content()
        }
    }
}