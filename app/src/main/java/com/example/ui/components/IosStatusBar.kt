package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IosStatusBar(
    time: String,
    batteryLevel: Int,
    isWifiOn: Boolean,
    isCellularOn: Boolean,
    isAirplaneMode: Boolean,
    isDarkMode: Boolean,
    onLeftTap: () -> Unit,
    onRightTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = Color.White

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: Time + carrier tap area
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onLeftTap
                )
        ) {
            Text(
                text = time,
                color = contentColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.2).sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            if (isAirplaneMode) {
                Text(
                    text = "✈",
                    color = contentColor.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            } else if (isCellularOn) {
                Text(
                    text = "5G",
                    color = contentColor.copy(alpha = 0.85f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Center space reserved for Dynamic Island
        Spacer(modifier = Modifier.weight(1f))

        // Right side: Cellular bars, Wifi, Battery
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onRightTap
                )
        ) {
            // Cellular signal 4 bars
            if (isCellularOn && !isAirplaneMode) {
                CellularSignalBars(color = contentColor)
            }

            // Wi-Fi icon
            if (isWifiOn && !isAirplaneMode) {
                Icon(
                    imageVector = Icons.Default.Wifi,
                    contentDescription = "Wi-Fi",
                    tint = contentColor,
                    modifier = Modifier.size(15.dp)
                )
            }

            // Battery percentage text
            Text(
                text = "$batteryLevel%",
                color = contentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            // iOS Battery Icon
            Canvas(modifier = Modifier.size(width = 24.dp, height = 12.dp)) {
                val strokeWidth = 1.2.dp.toPx()
                val cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
                val bodyWidth = size.width - 3.dp.toPx()
                val bodyHeight = size.height

                // Battery body border
                drawRoundRect(
                    color = contentColor.copy(alpha = 0.7f),
                    topLeft = Offset.Zero,
                    size = Size(bodyWidth, bodyHeight),
                    cornerRadius = cornerRadius,
                    style = Stroke(width = strokeWidth)
                )

                // Battery terminal cap
                drawRoundRect(
                    color = contentColor.copy(alpha = 0.7f),
                    topLeft = Offset(bodyWidth + 1.dp.toPx(), bodyHeight * 0.3f),
                    size = Size(1.8.dp.toPx(), bodyHeight * 0.4f),
                    cornerRadius = CornerRadius(1.dp.toPx(), 1.dp.toPx())
                )

                // Battery fill level
                val fillRatio = (batteryLevel / 100f).coerceIn(0f, 1f)
                val fillMargin = 2.dp.toPx()
                val maxFillWidth = bodyWidth - (fillMargin * 2)
                val currentFillWidth = maxFillWidth * fillRatio

                val fillColor = when {
                    batteryLevel <= 20 -> Color(0xFFFF3B30)
                    else -> Color(0xFF34C759)
                }

                if (currentFillWidth > 0) {
                    drawRoundRect(
                        color = fillColor,
                        topLeft = Offset(fillMargin, fillMargin),
                        size = Size(currentFillWidth, bodyHeight - (fillMargin * 2)),
                        cornerRadius = CornerRadius(1.5.dp.toPx(), 1.5.dp.toPx())
                    )
                }
            }
        }
    }
}

@Composable
private fun CellularSignalBars(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(width = 17.dp, height = 11.dp)) {
        val barWidth = 2.4.dp.toPx()
        val spacing = 1.8.dp.toPx()
        val totalBars = 4

        for (i in 0 until totalBars) {
            val barHeight = size.height * ((i + 1) / totalBars.toFloat())
            val x = i * (barWidth + spacing)
            val y = size.height - barHeight

            drawRoundRect(
                color = color,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(1.dp.toPx(), 1.dp.toPx())
            )
        }
    }
}
