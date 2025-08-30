package me.androidbox.qrcraft.scanning.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI

@Composable
fun ScanningSurfaceRoundedCorners(
    // New name for clarity
    modifier: Modifier = Modifier,
    content: @Composable (modifier: Modifier) -> Unit,
    surfaceRadius: Dp = 18.dp,     // Radius of the main Surface and the arcs
    lineColor: Color = Color.Yellow,
    lineStrokeWidth: Dp = 5.dp,
    lineExtensionLength: Dp = 15.dp, // How long the straight part extends from the arc
) {
    Surface(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val density = LocalDensity.current

            content(
                Modifier
                    .align(Alignment.Center)
                    .drawWithContent {
                        drawContent() // Draw the CameraPreview first (fills entire area)

                        // Fixed size for the scanning rectangle
                        val scanRectSize = Size(
                            width = with(density) { 324.dp.toPx() },
                            height = with(density) { 324.dp.toPx() },
                        )

                        // Calculate center position to place the 324dp square
                        val centerX = (this.size.width - scanRectSize.width) / 2f
                        val centerY = (this.size.height - scanRectSize.height) / 2f

                        // Draw dark overlay with transparent center (cutout effect)
                        drawRect(
                            color = Color.Black.copy(alpha = 0.6f), // Semi-transparent dark overlay
                            topLeft = Offset.Zero,
                            size = this.size
                        )

                        // Create transparent cutout in the center using BlendMode
                        drawRoundRect(
                            color = Color.Transparent,
                            topLeft = Offset(centerX, centerY),
                            size = scanRectSize,
                            cornerRadius = CornerRadius(surfaceRadius.toPx()),
                            blendMode = BlendMode.Clear
                        )

                        val strokePx = lineStrokeWidth.toPx()
                        val radiusPx = surfaceRadius.toPx()
                        val extensionPx = lineExtensionLength.toPx()

                        val strokeStyle = Stroke(width = strokePx, cap = StrokeCap.Round)

                        // Top-Left Corner
                        drawArc(
                            color = lineColor,
                            startAngle = 180f,
                            sweepAngle = 90f,
                            useCenter = false,
                            topLeft = Offset(
                                centerX + strokePx / 2f,
                                centerY + strokePx / 2f
                            ),
                            size = Size(radiusPx * 2 - strokePx, radiusPx * 2 - strokePx),
                            style = strokeStyle
                        )
                        // Horizontal tail
                        drawLine(
                            color = lineColor,
                            start = Offset(centerX + radiusPx, centerY + strokePx / 2f),
                            end = Offset(centerX + radiusPx + extensionPx, centerY + strokePx / 2f),
                            strokeWidth = strokePx,
                            cap = StrokeCap.Round
                        )
                        // Vertical tail
                        drawLine(
                            color = lineColor,
                            start = Offset(centerX + strokePx / 2f, centerY + radiusPx),
                            end = Offset(centerX + strokePx / 2f, centerY + radiusPx + extensionPx),
                            strokeWidth = strokePx,
                            cap = StrokeCap.Round
                        )

                        // Top-Right Corner
                        drawArc(
                            color = lineColor,
                            startAngle = 270f,
                            sweepAngle = 90f,
                            useCenter = false,
                            topLeft = Offset(
                                centerX + scanRectSize.width - radiusPx * 2 + strokePx / 2f,
                                centerY + strokePx / 2f
                            ),
                            size = Size(radiusPx * 2 - strokePx, radiusPx * 2 - strokePx),
                            style = strokeStyle
                        )
                        // Horizontal tail
                        drawLine(
                            color = lineColor,
                            start = Offset(centerX + scanRectSize.width - radiusPx, centerY + strokePx / 2f),
                            end = Offset(centerX + scanRectSize.width - radiusPx - extensionPx, centerY + strokePx / 2f),
                            strokeWidth = strokePx,
                            cap = StrokeCap.Round
                        )
                        // Vertical tail
                        drawLine(
                            color = lineColor,
                            start = Offset(centerX + scanRectSize.width - strokePx / 2f, centerY + radiusPx),
                            end = Offset(centerX + scanRectSize.width - strokePx / 2f, centerY + radiusPx + extensionPx),
                            strokeWidth = strokePx,
                            cap = StrokeCap.Round
                        )

                        // Bottom-Left Corner
                        drawArc(
                            color = lineColor,
                            startAngle = 90f,
                            sweepAngle = 90f,
                            useCenter = false,
                            topLeft = Offset(
                                centerX + strokePx / 2f,
                                centerY + scanRectSize.height - radiusPx * 2 + strokePx / 2f
                            ),
                            size = Size(radiusPx * 2 - strokePx, radiusPx * 2 - strokePx),
                            style = strokeStyle
                        )
                        // Horizontal tail
                        drawLine(
                            color = lineColor,
                            start = Offset(centerX + radiusPx, centerY + scanRectSize.height - strokePx / 2f),
                            end = Offset(centerX + radiusPx + extensionPx, centerY + scanRectSize.height - strokePx / 2f),
                            strokeWidth = strokePx,
                            cap = StrokeCap.Round
                        )
                        // Vertical tail
                        drawLine(
                            color = lineColor,
                            start = Offset(centerX + strokePx / 2f, centerY + scanRectSize.height - radiusPx),
                            end = Offset(centerX + strokePx / 2f, centerY + scanRectSize.height - radiusPx - extensionPx),
                            strokeWidth = strokePx,
                            cap = StrokeCap.Round
                        )

                        // Bottom-Right Corner
                        drawArc(
                            color = lineColor,
                            startAngle = 0f,
                            sweepAngle = 90f,
                            useCenter = false,
                            topLeft = Offset(
                                centerX + scanRectSize.width - radiusPx * 2 + strokePx / 2f,
                                centerY + scanRectSize.height - radiusPx * 2 + strokePx / 2f
                            ),
                            size = Size(radiusPx * 2 - strokePx, radiusPx * 2 - strokePx),
                            style = strokeStyle
                        )
                        // Horizontal tail
                        drawLine(
                            color = lineColor,
                            start = Offset(centerX + scanRectSize.width - radiusPx, centerY + scanRectSize.height - strokePx / 2f),
                            end = Offset(
                                centerX + scanRectSize.width - radiusPx - extensionPx,
                                centerY + scanRectSize.height - strokePx / 2f
                            ),
                            strokeWidth = strokePx,
                            cap = StrokeCap.Round
                        )
                        // Vertical tail
                        drawLine(
                            color = lineColor,
                            start = Offset(centerX + scanRectSize.width - strokePx / 2f, centerY + scanRectSize.height - radiusPx),
                            end = Offset(
                                centerX + scanRectSize.width - strokePx / 2f,
                                centerY + scanRectSize.height - radiusPx - extensionPx
                            ),
                            strokeWidth = strokePx,
                            cap = StrokeCap.Round
                        )
                    }
                    .align(Alignment.Center)
            )
        }
    }
}