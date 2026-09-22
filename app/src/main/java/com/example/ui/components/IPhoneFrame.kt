package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IPhoneFrame(
    showFrame: Boolean,
    onToggleFrame: () -> Unit,
    onPowerButton: () -> Unit,
    onVolumeUp: () -> Unit,
    onVolumeDown: () -> Unit,
    onActionButton: () -> Unit,
    screenContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0C0D11)),
        contentAlignment = Alignment.Center
    ) {
        if (!showFrame) {
            // Fullscreen edge-to-edge mode
            Box(modifier = Modifier.fillMaxSize()) {
                screenContent()

                // Subtle frame toggle floating button in corner
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .clip(CircleShape)
                        .background(Color(0x88000000))
                        .clickable { onToggleFrame() }
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Smartphone,
                        contentDescription = "Show iPhone 18 Pro Max Frame",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        } else {
            // iPhone 18 Pro Max Hardware Chassis View
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp)
            ) {
                // Top control bar for emulator context
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "iPhone 18 Pro Max",
                            color = Color(0xFFD4AF37),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• Natural Titanium",
                            color = Color(0xFF8E8E93),
                            fontSize = 12.sp
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x33FFFFFF))
                                .clickable { onToggleFrame() }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Full Screen",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Phone Body with hardware buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Left Hardware Buttons (Action Button, Vol Up, Vol Down)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.padding(end = 2.dp)
                    ) {
                        // Action Button
                        HardwareButtonPill(
                            height = 24.dp,
                            color = Color(0xFFFF9500),
                            label = "Action",
                            onClick = onActionButton
                        )
                        // Volume Up
                        HardwareButtonPill(
                            height = 44.dp,
                            color = Color(0xFF5E6068),
                            label = "+",
                            onClick = onVolumeUp
                        )
                        // Volume Down
                        HardwareButtonPill(
                            height = 44.dp,
                            color = Color(0xFF5E6068),
                            label = "-",
                            onClick = onVolumeDown
                        )
                    }

                    // Main Chassis Outer Shell
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.88f)
                            .fillMaxHeight(0.94f)
                            .shadow(
                                elevation = 32.dp,
                                shape = RoundedCornerShape(48.dp),
                                ambientColor = Color.Black,
                                spotColor = Color(0xFF1E222A)
                            )
                            // Grade-5 Natural Titanium Bevel
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFF5A5C64),
                                        Color(0xFF2C2D32),
                                        Color(0xFF7A7D88),
                                        Color(0xFF24252A),
                                        Color(0xFF4C4E56)
                                    )
                                ),
                                shape = RoundedCornerShape(48.dp)
                            )
                            .border(
                                width = 1.5.dp,
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color(0xFF9E9EA8),
                                        Color(0xFF32333A),
                                        Color(0xFF787A84)
                                    )
                                ),
                                shape = RoundedCornerShape(48.dp)
                            )
                            .padding(4.dp) // Titanium rim thickness
                    ) {
                        // Ultra-slim black bezel
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(44.dp))
                                .background(Color.Black)
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFF111215),
                                    shape = RoundedCornerShape(44.dp)
                                )
                        ) {
                            screenContent()
                        }
                    }

                    // Right Hardware Buttons (Side / Power Button & Camera Control)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(40.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.padding(start = 2.dp)
                    ) {
                        // Power / Side Button
                        HardwareButtonPill(
                            height = 68.dp,
                            color = Color(0xFF6A6C76),
                            label = "Power",
                            onClick = onPowerButton
                        )

                        // Camera Control Button (iPhone 18 Pro Max feature)
                        HardwareButtonPill(
                            height = 42.dp,
                            color = Color(0xFF3A3C44),
                            label = "Shutter",
                            onClick = onActionButton
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HardwareButtonPill(
    height: androidx.compose.ui.unit.Dp,
    color: Color,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(5.dp)
            .height(height)
            .clip(RoundedCornerShape(2.dp))
            .background(color)
            .clickable { onClick() }
    )
}
