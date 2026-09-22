package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
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
import com.example.model.AppId

@Composable
fun AppSwitcherSheet(
    isOpen: Boolean,
    onClose: () -> Unit,
    onSelectApp: (AppId) -> Unit,
    modifier: Modifier = Modifier
) {
    val recentApps = listOf(
        Triple(AppId.CAMERA, "Camera", Brush.linearGradient(listOf(Color(0xFF2C3E50), Color(0xFF000000)))),
        Triple(AppId.SAFARI, "Safari", Brush.linearGradient(listOf(Color(0xFF007AFF), Color(0xFF00C6FF)))),
        Triple(AppId.MESSAGES, "Messages", Brush.linearGradient(listOf(Color(0xFF34C759), Color(0xFF11998E)))),
        Triple(AppId.MUSIC, "Music", Brush.linearGradient(listOf(Color(0xFFFA2D48), Color(0xFFFF512F)))),
        Triple(AppId.SETTINGS, "Settings", Brush.linearGradient(listOf(Color(0xFF8E8E93), Color(0xFF2C2C2E)))),
        Triple(AppId.WEATHER, "Weather", Brush.linearGradient(listOf(Color(0xFF1E5EC7), Color(0xFF2C7BF6))))
    )

    AnimatedVisibility(
        visible = isOpen,
        enter = fadeIn(tween(200)) + scaleIn(initialScale = 0.85f, animationSpec = tween(250)),
        exit = fadeOut(tween(150)) + scaleOut(targetScale = 0.85f, animationSpec = tween(200))
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.75f))
                .clickable { onClose() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "App Switcher",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(recentApps) { (appId, name, brush) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(220.dp)
                                .clickable {
                                    onSelectApp(appId)
                                    onClose()
                                }
                        ) {
                            // Card header
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp)
                            ) {
                                Text(
                                    text = name,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Card Window
                            Box(
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(380.dp)
                                    .shadow(16.dp, RoundedCornerShape(28.dp))
                                    .clip(RoundedCornerShape(28.dp))
                                    .background(brush)
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = when (appId) {
                                            AppId.CAMERA -> Icons.Default.CameraAlt
                                            AppId.SAFARI -> Icons.Default.Explore
                                            AppId.MESSAGES -> Icons.Default.Chat
                                            AppId.MUSIC -> Icons.Default.MusicNote
                                            AppId.SETTINGS -> Icons.Default.Settings
                                            AppId.WEATHER -> Icons.Default.WbSunny
                                            else -> Icons.Default.Apps
                                        },
                                        contentDescription = name,
                                        tint = Color.White,
                                        modifier = Modifier.size(54.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Tap to resume $name",
                                        color = Color.White.copy(alpha = 0.85f),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0x33FFFFFF))
                        .clickable { onClose() }
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Close Switcher",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
