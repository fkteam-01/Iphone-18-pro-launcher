package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppId
import com.example.model.AppItem

@Composable
fun SpringboardView(
    apps: List<AppItem>,
    onLaunchApp: (AppId) -> Unit,
    batteryLevel: Int,
    isCharging: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top iOS System Widgets (Weather 2x2 & Battery 2x2)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(140.dp)
            ) {
                WeatherWidget(onOpenWeather = { onLaunchApp(AppId.WEATHER) })
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(140.dp)
            ) {
                BatteryWidget(iphoneBattery = batteryLevel)
            }
        }

        // 4x4 Grid of iOS App Icons
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f, fill = false)
        ) {
            items(apps) { app ->
                AppIconItem(
                    app = app,
                    onClick = { onLaunchApp(app.id) }
                )
            }
        }

        // Page Indicator & Spotlight Search Pill
        Row(
            modifier = Modifier
                .padding(bottom = 6.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0x44000000))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Search",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun AppIconItem(
    app: AppItem,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed) 0.88f else 1.0f

    val iconVector = when (app.id) {
        AppId.PHONE -> Icons.Default.Phone
        AppId.MESSAGES -> Icons.Default.Chat
        AppId.SAFARI -> Icons.Default.Explore
        AppId.MUSIC -> Icons.Default.MusicNote
        AppId.CAMERA -> Icons.Default.CameraAlt
        AppId.PHOTOS -> Icons.Default.PhotoLibrary
        AppId.WEATHER -> Icons.Default.WbSunny
        AppId.CALCULATOR -> Icons.Default.Calculate
        AppId.SETTINGS -> Icons.Default.Settings
        AppId.CLOCK -> Icons.Default.AccessTime
        AppId.NOTES -> Icons.Default.StickyNote2
        AppId.HEALTH -> Icons.Default.Favorite
        AppId.APP_STORE -> Icons.Default.Apps
        AppId.MAPS -> Icons.Default.Map
        AppId.VOICE_MEMOS -> Icons.Default.Mic
        AppId.REMINDERS -> Icons.Default.Checklist
        AppId.FILES -> Icons.Default.Folder
        AppId.FACETIME -> Icons.Default.Videocam
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .background(app.iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = app.name,
                tint = app.iconColor,
                modifier = Modifier.size(34.dp)
            )

            // Badge counter (e.g. unread count)
            if (app.badgeCount > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 2.dp, y = (-2).dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF3B30)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${app.badgeCount}",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = app.name,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
