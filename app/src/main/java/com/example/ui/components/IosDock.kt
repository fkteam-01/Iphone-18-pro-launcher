package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppId

@Composable
fun IosDock(
    unreadMessagesCount: Int,
    onOpenApp: (AppId) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        // Frosted Glass Dock Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(86.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(36.dp),
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .clip(RoundedCornerShape(36.dp))
                .background(Color.White.copy(alpha = 0.22f))
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Phone
                DockAppIcon(
                    icon = Icons.Default.Phone,
                    gradient = Brush.linearGradient(listOf(Color(0xFF34C759), Color(0xFF30D158))),
                    onClick = { onOpenApp(AppId.PHONE) }
                )

                // Safari
                DockAppIcon(
                    icon = Icons.Default.Explore,
                    gradient = Brush.linearGradient(listOf(Color(0xFF007AFF), Color(0xFF5AC8FA))),
                    onClick = { onOpenApp(AppId.SAFARI) }
                )

                // Messages
                DockAppIcon(
                    icon = Icons.Default.Chat,
                    gradient = Brush.linearGradient(listOf(Color(0xFF34C759), Color(0xFF28CD41))),
                    badgeCount = unreadMessagesCount,
                    onClick = { onOpenApp(AppId.MESSAGES) }
                )

                // Music
                DockAppIcon(
                    icon = Icons.Default.MusicNote,
                    gradient = Brush.linearGradient(listOf(Color(0xFFFA2D48), Color(0xFFFF375F))),
                    onClick = { onOpenApp(AppId.MUSIC) }
                )
            }
        }
    }
}

@Composable
fun DockAppIcon(
    icon: ImageVector,
    gradient: Brush,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed) 0.86f else 1.0f

    Box(
        modifier = Modifier
            .scale(scale)
            .size(58.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        // App squircle
        Box(
            modifier = Modifier
                .size(56.dp)
                .shadow(6.dp, RoundedCornerShape(14.dp), ambientColor = Color.Black)
                .clip(RoundedCornerShape(14.dp))
                .background(gradient),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        // Notification Badge
        if (badgeCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-4).dp)
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF3B30)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeCount.toString(),
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
