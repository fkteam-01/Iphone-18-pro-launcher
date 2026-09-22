package com.example.ui.apps

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HealthApp(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Summary",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Activity Rings Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF1C1C1E))
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("ACTIVITY", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)

                        Column {
                            Text("Move", color = Color(0xFFFA114F), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("540 / 600 KCAL", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                        Column {
                            Text("Exercise", color = Color(0xFFA1E700), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("35 / 30 MIN", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                        Column {
                            Text("Stand", color = Color(0xFF00E6FF), fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("10 / 12 HRS", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Rings
                    Canvas(modifier = Modifier.size(110.dp)) {
                        val center = Offset(size.width / 2, size.height / 2)
                        val strokeW = 10.dp.toPx()

                        // Move Ring
                        val r1 = (size.width / 2) - (strokeW / 2)
                        drawCircle(Color(0xFFFA114F).copy(alpha = 0.2f), r1, center, style = Stroke(strokeW))
                        drawArc(Color(0xFFFA114F), -90f, 300f, false, Offset(center.x - r1, center.y - r1), Size(r1 * 2, r1 * 2), style = Stroke(strokeW, cap = StrokeCap.Round))

                        // Exercise Ring
                        val r2 = r1 - strokeW - 2.dp.toPx()
                        drawCircle(Color(0xFFA1E700).copy(alpha = 0.2f), r2, center, style = Stroke(strokeW))
                        drawArc(Color(0xFFA1E700), -90f, 260f, false, Offset(center.x - r2, center.y - r2), Size(r2 * 2, r2 * 2), style = Stroke(strokeW, cap = StrokeCap.Round))

                        // Stand Ring
                        val r3 = r2 - strokeW - 2.dp.toPx()
                        drawCircle(Color(0xFF00E6FF).copy(alpha = 0.2f), r3, center, style = Stroke(strokeW))
                        drawArc(Color(0xFF00E6FF), -90f, 210f, false, Offset(center.x - r3, center.y - r3), Size(r3 * 2, r3 * 2), style = Stroke(strokeW, cap = StrokeCap.Round))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Metrics Grid (Steps, Heart Rate, Sleep)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Steps
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF1C1C1E))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DirectionsWalk, contentDescription = null, tint = Color(0xFFFF9500), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("STEPS", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("8,421", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text("4.2 mi walked", color = Color.LightGray, fontSize = 12.sp)
                    }
                }

                // Heart Rate
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color(0xFF1C1C1E))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFFF2D55), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("HEART RATE", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("68 BPM", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text("Resting • Normal", color = Color(0xFF34C759), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
