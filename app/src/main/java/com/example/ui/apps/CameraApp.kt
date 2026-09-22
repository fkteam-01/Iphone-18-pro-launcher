package com.example.ui.apps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppId
import com.example.model.PhotoItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CameraApp(
    latestPhoto: PhotoItem?,
    onCapturePhoto: () -> Unit,
    onOpenPhotos: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedZoom by remember { mutableStateOf("1x") }
    val zoomOptions = listOf("0.5", "1x", "2", "5")
    var selectedMode by remember { mutableStateOf("PHOTO") }
    val modes = listOf("CINEMATIC", "VIDEO", "PHOTO", "PORTRAIT", "PANO")
    var flashMode by remember { mutableStateOf("Auto") }
    var isFrontCamera by remember { mutableStateOf(false) }
    var shutterFlash by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val shutterInteractionSource = remember { MutableInteractionSource() }
    val isShutterPressed by shutterInteractionSource.collectIsPressedAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Shutter flash overlay effect
        AnimatedVisibility(
            visible = shutterFlash,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Camera Bar (Flash, Exposure, Live Photo, 48MP indicator)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Flash toggle
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0x44FFFFFF))
                        .clickable {
                            flashMode = when (flashMode) {
                                "Auto" -> "On"
                                "On" -> "Off"
                                else -> "Auto"
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (flashMode == "Off") Icons.Default.FlashOff else Icons.Default.FlashOn,
                        contentDescription = "Flash",
                        tint = if (flashMode == "On") Color(0xFFFFCC00) else Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // 200MP Fusion Sensor badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x44FFFFFF))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "200MP RAW",
                        color = Color(0xFFFFCC00),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Live Photo toggle
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0x44FFFFFF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterVintage,
                        contentDescription = "Live Photo",
                        tint = Color(0xFFFFCC00),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Viewfinder simulated preview frame
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            if (isFrontCamera)
                                listOf(Color(0xFF2C3E50), Color(0xFF000000), Color(0xFF1A252F))
                            else
                                listOf(Color(0xFF1E272C), Color(0xFF121619), Color(0xFF263238))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Viewfinder focus brackets
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .border(1.5.dp, Color(0xFFFFCC00).copy(alpha = 0.8f), RoundedCornerShape(12.dp))
                )

                // Simulated Viewfinder subject indicator
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isFrontCamera) "TrueDepth FaceTime Lens" else "iPhone 18 Pro Max • 24mm ƒ/1.6",
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Auto-Focus: Subject Detected",
                        color = Color(0xFFFFCC00).copy(alpha = 0.8f),
                        fontSize = 10.sp
                    )
                }

                // Zoom Level Selector Pill
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0x66000000))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    zoomOptions.forEach { zoom ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (selectedZoom == zoom) Color(0x88FFFFFF) else Color.Transparent)
                                .clickable { selectedZoom = zoom },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = zoom,
                                color = if (selectedZoom == zoom) Color(0xFFFFCC00) else Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Camera Modes Carousel (PHOTO, VIDEO, PORTRAIT, etc.)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                modes.forEach { mode ->
                    Text(
                        text = mode,
                        color = if (selectedMode == mode) Color(0xFFFFCC00) else Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp,
                        fontWeight = if (selectedMode == mode) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.clickable { selectedMode = mode }
                    )
                }
            }

            // Bottom Shutter Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Photos Library Thumbnail preview
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            if (latestPhoto != null)
                                Brush.linearGradient(latestPhoto.gradientColors)
                            else
                                Brush.linearGradient(listOf(Color(0xFF333333), Color(0xFF555555)))
                        )
                        .border(1.5.dp, Color.White, RoundedCornerShape(14.dp))
                        .clickable { onOpenPhotos() },
                    contentAlignment = Alignment.Center
                ) {
                    if (latestPhoto == null) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }

                // Authentic iOS Shutter Button
                Box(
                    modifier = Modifier
                        .scale(if (isShutterPressed) 0.92f else 1.0f)
                        .size(76.dp)
                        .border(4.dp, Color.White, CircleShape)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable(
                            interactionSource = shutterInteractionSource,
                            indication = null,
                            onClick = {
                                coroutineScope.launch {
                                    shutterFlash = true
                                    delay(80)
                                    shutterFlash = false
                                }
                                onCapturePhoto()
                            }
                        )
                )

                // Front/Rear Camera Flip Button
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(Color(0x44FFFFFF))
                        .clickable { isFrontCamera = !isFrontCamera },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FlipCameraAndroid,
                        contentDescription = "Flip Camera",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    }
}
