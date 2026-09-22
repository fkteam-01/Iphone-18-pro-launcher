package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorApp(
    displayValue: String,
    onButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = listOf(
        listOf("AC" to Color(0xFFA5A5A5), "+/-" to Color(0xFFA5A5A5), "%" to Color(0xFFA5A5A5), "÷" to Color(0xFFFF9F0A)),
        listOf("7" to Color(0xFF333333), "8" to Color(0xFF333333), "9" to Color(0xFF333333), "×" to Color(0xFFFF9F0A)),
        listOf("4" to Color(0xFF333333), "5" to Color(0xFF333333), "6" to Color(0xFF333333), "-" to Color(0xFFFF9F0A)),
        listOf("1" to Color(0xFF333333), "2" to Color(0xFF333333), "3" to Color(0xFF333333), "+" to Color(0xFFFF9F0A)),
        listOf("0" to Color(0xFF333333), "." to Color(0xFF333333), "=" to Color(0xFFFF9F0A))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Main Output Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = displayValue,
                    color = Color.White,
                    fontSize = if (displayValue.length > 7) 46.sp else 68.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }

            // Buttons Matrix
            rows.forEachIndexed { rowIndex, row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (rowIndex == 4) {
                        // Special bottom row: "0" takes double width
                        CalcButton(
                            text = "0",
                            color = Color(0xFF333333),
                            textColor = Color.White,
                            isWide = true,
                            onClick = { onButtonClick("0") },
                            modifier = Modifier.weight(2f)
                        )
                        CalcButton(
                            text = ".",
                            color = Color(0xFF333333),
                            textColor = Color.White,
                            onClick = { onButtonClick(".") },
                            modifier = Modifier.weight(1f)
                        )
                        CalcButton(
                            text = "=",
                            color = Color(0xFFFF9F0A),
                            textColor = Color.White,
                            onClick = { onButtonClick("=") },
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        row.forEach { (label, color) ->
                            val textColor = if (color == Color(0xFFA5A5A5)) Color.Black else Color.White
                            CalcButton(
                                text = label,
                                color = color,
                                textColor = textColor,
                                onClick = { onButtonClick(label) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalcButton(
    text: String,
    color: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isWide: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed) 0.90f else 1.0f

    Box(
        modifier = modifier
            .scale(scale)
            .height(68.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = if (isWide) Alignment.CenterStart else Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium,
            modifier = if (isWide) Modifier.padding(start = 28.dp) else Modifier
        )
    }
}
