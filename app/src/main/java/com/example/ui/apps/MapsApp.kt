package com.example.ui.apps

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MapsApp(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF1E242B))
    ) {
        // Simulated Vector Map Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Roads / Highways
            drawLine(Color(0xFF2C353F), Offset(0f, h * 0.3f), Offset(w, h * 0.45f), strokeWidth = 16f)
            drawLine(Color(0xFF2C353F), Offset(w * 0.25f, 0f), Offset(w * 0.4f, h), strokeWidth = 20f)
            drawLine(Color(0xFF384350), Offset(0f, h * 0.6f), Offset(w, h * 0.7f), strokeWidth = 14f)
            drawLine(Color(0xFF384350), Offset(w * 0.7f, 0f), Offset(w * 0.6f, h), strokeWidth = 12f)

            // Apple Park Ring Shape
            drawCircle(
                color = Color(0xFF163820),
                radius = 110f,
                center = Offset(w * 0.5f, h * 0.48f)
            )
            drawCircle(
                color = Color(0xFF34C759).copy(alpha = 0.4f),
                radius = 120f,
                center = Offset(w * 0.5f, h * 0.48f),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8f)
            )
        }

        // Top Search Bar Capsule
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF2C2C2E).copy(alpha = 0.9f))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search Maps or enter address", color = Color.Gray, fontSize = 14.sp)
            }
        }

        // Center Location Pin for Apple Park
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF007AFF))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("Apple Park", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Icon(Icons.Default.LocationOn, contentDescription = "Pin", tint = Color(0xFFFF3B30), modifier = Modifier.size(36.dp))
        }

        // Bottom Navigation Direction Card
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1C1C1E))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Apple Park • 1 Apple Park Way", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("Cupertino, CA • 14 min drive (5.8 mi)", color = Color.Gray, fontSize = 13.sp)
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color(0xFF007AFF))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Directions", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}
