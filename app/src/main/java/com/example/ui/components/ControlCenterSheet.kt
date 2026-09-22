package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppId

@Composable
fun ControlCenterSheet(
    isOpen: Boolean,
    onClose: () -> Unit,
    isWifiOn: Boolean,
    onToggleWifi: () -> Unit,
    isBluetoothOn: Boolean,
    onToggleBluetooth: () -> Unit,
    isCellularOn: Boolean,
    onToggleCellular: () -> Unit,
    isAirplaneMode: Boolean,
    onToggleAirplane: () -> Unit,
    isFlashlightOn: Boolean,
    onToggleFlashlight: () -> Unit,
    isLowPowerMode: Boolean,
    onToggleLowPower: () -> Unit,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    brightness: Float,
    onBrightnessChange: (Float) -> Unit,
    volume: Float,
    onVolumeChange: (Float) -> Unit,
    // Music
    songTitle: String,
    artist: String,
    isPlayingMusic: Boolean,
    onTogglePlayMusic: () -> Unit,
    onOpenApp: (AppId) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isOpen,
        enter = fadeIn(tween(200)) + slideInVertically(initialOffsetY = { -it }, animationSpec = tween(300)),
        exit = fadeOut(tween(200)) + slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(250))
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClose
                )
                .padding(horizontal = 16.dp, vertical = 20.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {} // Consume click inside
                    ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Top Row: Connectivity 2x2 & Media Player
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Connectivity 2x2 Card
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(148.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFF2C2C2E).copy(alpha = 0.85f))
                            .padding(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                ControlCircleButton(
                                    icon = Icons.Default.AirplanemodeActive,
                                    isActive = isAirplaneMode,
                                    activeColor = Color(0xFFFF9500),
                                    onClick = onToggleAirplane
                                )
                                ControlCircleButton(
                                    icon = Icons.Default.SignalCellularAlt,
                                    isActive = isCellularOn && !isAirplaneMode,
                                    activeColor = Color(0xFF34C759),
                                    onClick = onToggleCellular
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                ControlCircleButton(
                                    icon = Icons.Default.Wifi,
                                    isActive = isWifiOn && !isAirplaneMode,
                                    activeColor = Color(0xFF007AFF),
                                    onClick = onToggleWifi
                                )
                                ControlCircleButton(
                                    icon = Icons.Default.Bluetooth,
                                    isActive = isBluetoothOn,
                                    activeColor = Color(0xFF007AFF),
                                    onClick = onToggleBluetooth
                                )
                            }
                        }
                    }

                    // Media Player Card
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(148.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFF2C2C2E).copy(alpha = 0.85f))
                            .padding(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = songTitle,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = artist,
                                        color = Color.LightGray,
                                        fontSize = 11.sp,
                                        maxLines = 1
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.Airplay,
                                    contentDescription = null,
                                    tint = Color.LightGray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SkipPrevious,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .clickable { onTogglePlayMusic() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = if (isPlayingMusic) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.SkipNext,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

                // Middle Row: Screen Mirroring & Focus & Sliders
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Two Vertical Tiles: Focus & Screen Rotation
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Focus Mode
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(67.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFF2C2C2E).copy(alpha = 0.85f))
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.NightlightRound,
                                    contentDescription = null,
                                    tint = Color(0xFF5856D6),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("Focus", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                    Text("Do Not Disturb", color = Color.LightGray, fontSize = 10.sp)
                                }
                            }
                        }

                        // Low Power Mode / Dark Mode Tile
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(67.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFF2C2C2E).copy(alpha = 0.85f))
                                .clickable { onToggleDarkMode() }
                                .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                    contentDescription = null,
                                    tint = if (isDarkMode) Color(0xFFFFCC00) else Color(0xFFFF9500),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("Appearance", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                    Text(if (isDarkMode) "Dark" else "Light", color = Color.LightGray, fontSize = 10.sp)
                                }
                            }
                        }
                    }

                    // Brightness Vertical Slider Pill
                    VerticalSliderPill(
                        value = brightness,
                        onValueChange = onBrightnessChange,
                        icon = Icons.Default.WbSunny,
                        modifier = Modifier.weight(0.5f)
                    )

                    // Volume Vertical Slider Pill
                    VerticalSliderPill(
                        value = volume,
                        onValueChange = onVolumeChange,
                        icon = Icons.Default.VolumeUp,
                        modifier = Modifier.weight(0.5f)
                    )
                }

                // Bottom Quick Action Tiles
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Flashlight
                    QuickActionPill(
                        icon = Icons.Default.FlashlightOn,
                        isActive = isFlashlightOn,
                        activeColor = Color.White,
                        iconActiveTint = Color.Black,
                        onClick = onToggleFlashlight
                    )
                    // Calculator
                    QuickActionPill(
                        icon = Icons.Default.Calculate,
                        isActive = false,
                        onClick = {
                            onClose()
                            onOpenApp(AppId.CALCULATOR)
                        }
                    )
                    // Camera
                    QuickActionPill(
                        icon = Icons.Default.CameraAlt,
                        isActive = false,
                        onClick = {
                            onClose()
                            onOpenApp(AppId.CAMERA)
                        }
                    )
                    // Low Power Mode
                    QuickActionPill(
                        icon = Icons.Default.BatteryChargingFull,
                        isActive = isLowPowerMode,
                        activeColor = Color(0xFFFFCC00),
                        iconActiveTint = Color.Black,
                        onClick = onToggleLowPower
                    )
                }
            }
        }
    }
}

@Composable
private fun ControlCircleButton(
    icon: ImageVector,
    isActive: Boolean,
    activeColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(if (isActive) activeColor else Color(0xFF3A3A3C).copy(alpha = 0.8f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isActive) Color.White else Color(0xFFEBEBF5),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun QuickActionPill(
    icon: ImageVector,
    isActive: Boolean,
    activeColor: Color = Color.White,
    iconActiveTint: Color = Color.Black,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(68.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(if (isActive) activeColor else Color(0xFF2C2C2E).copy(alpha = 0.85f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isActive) iconActiveTint else Color.White,
            modifier = Modifier.size(26.dp)
        )
    }
}

@Composable
private fun VerticalSliderPill(
    value: Float,
    onValueChange: (Float) -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    var dragRatio by remember(value) { mutableFloatStateOf(value) }

    Box(
        modifier = modifier
            .height(148.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFF2C2C2E).copy(alpha = 0.85f))
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val deltaRatio = -dragAmount.y / 350f
                    dragRatio = (dragRatio + deltaRatio).coerceIn(0.05f, 1f)
                    onValueChange(dragRatio)
                }
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        // Filled height based on ratio
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(value.coerceIn(0.05f, 1f))
                .background(Color.White)
        )

        // Centered bottom icon
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (value > 0.25f) Color(0xFF333333) else Color.White,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(22.dp)
        )
    }
}
