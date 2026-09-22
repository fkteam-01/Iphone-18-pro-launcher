package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppId

@Composable
fun WeatherWidget(
    onOpenWeather: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(12.dp, RoundedCornerShape(26.dp), spotColor = Color(0x33000000))
            .clip(RoundedCornerShape(26.dp))
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF2C7BF6), Color(0xFF1E5EC7), Color(0xFF134599))
                )
            )
            .clickable { onOpenWeather() }
            .padding(14.dp)
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
                Column {
                    Text(
                        text = "Cupertino",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "72°",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Light,
                        letterSpacing = (-1).sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = Color(0xFFFFCC00),
                    modifier = Modifier.size(30.dp)
                )
            }

            Column {
                Text(
                    text = "Mostly Sunny",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "H:76°  L:58°",
                    color = Color.White.copy(alpha = 0.75f),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun FitnessRingsWidget(
    onOpenHealth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(12.dp, RoundedCornerShape(26.dp), spotColor = Color(0x33000000))
            .clip(RoundedCornerShape(26.dp))
            .background(Color(0xFF1C1C1E).copy(alpha = 0.92f))
            .clickable { onOpenHealth() }
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Concentric Activity Rings
            Canvas(modifier = Modifier.size(80.dp)) {
                val center = Offset(size.width / 2, size.height / 2)
                val strokeW = 8.dp.toPx()

                // Move Ring (Red)
                val r1 = (size.width / 2) - (strokeW / 2)
                drawCircle(color = Color(0xFFFA114F).copy(alpha = 0.2f), radius = r1, center = center, style = Stroke(strokeW))
                drawArc(
                    color = Color(0xFFFA114F),
                    startAngle = -90f,
                    sweepAngle = 300f,
                    useCenter = false,
                    topLeft = Offset(center.x - r1, center.y - r1),
                    size = Size(r1 * 2, r1 * 2),
                    style = Stroke(strokeW, cap = StrokeCap.Round)
                )

                // Exercise Ring (Green)
                val r2 = r1 - strokeW - 2.dp.toPx()
                drawCircle(color = Color(0xFFA1E700).copy(alpha = 0.2f), radius = r2, center = center, style = Stroke(strokeW))
                drawArc(
                    color = Color(0xFFA1E700),
                    startAngle = -90f,
                    sweepAngle = 260f,
                    useCenter = false,
                    topLeft = Offset(center.x - r2, center.y - r2),
                    size = Size(r2 * 2, r2 * 2),
                    style = Stroke(strokeW, cap = StrokeCap.Round)
                )

                // Stand Ring (Cyan)
                val r3 = r2 - strokeW - 2.dp.toPx()
                drawCircle(color = Color(0xFF00E6FF).copy(alpha = 0.2f), radius = r3, center = center, style = Stroke(strokeW))
                drawArc(
                    color = Color(0xFF00E6FF),
                    startAngle = -90f,
                    sweepAngle = 210f,
                    useCenter = false,
                    topLeft = Offset(center.x - r3, center.y - r3),
                    size = Size(r3 * 2, r3 * 2),
                    style = Stroke(strokeW, cap = StrokeCap.Round)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = "ACTIVITY",
                    color = Color.Gray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFFA114F)))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("540 / 600 CAL", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFFA1E700)))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("35 / 30 MIN", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(Color(0xFF00E6FF)))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("10 / 12 HRS", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun BatteryWidget(
    iphoneBattery: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(12.dp, RoundedCornerShape(26.dp), spotColor = Color(0x33000000))
            .clip(RoundedCornerShape(26.dp))
            .background(Color(0xFF1C1C1E).copy(alpha = 0.92f))
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "BATTERIES",
                color = Color.Gray,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // iPhone
                BatteryGaugeCircle(
                    name = "iPhone",
                    level = iphoneBattery,
                    color = Color(0xFF34C759)
                )

                // AirPods Pro
                BatteryGaugeCircle(
                    name = "AirPods",
                    level = 88,
                    color = Color(0xFF34C759)
                )

                // Apple Watch Ultra
                BatteryGaugeCircle(
                    name = "Watch",
                    level = 76,
                    color = Color(0xFF34C759)
                )
            }
        }
    }
}

@Composable
private fun BatteryGaugeCircle(
    name: String,
    level: Int,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(44.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeW = 4.dp.toPx()
                val radius = (size.width / 2) - strokeW
                val center = Offset(size.width / 2, size.height / 2)

                drawCircle(
                    color = color.copy(alpha = 0.2f),
                    radius = radius,
                    center = center,
                    style = Stroke(strokeW)
                )

                val sweep = (level / 100f) * 360f
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(strokeW, cap = StrokeCap.Round)
                )
            }
            Text(
                text = "$level%",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = name,
            color = Color.LightGray,
            fontSize = 10.sp
        )
    }
}

@Composable
fun CalendarWidget(
    currentDate: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(12.dp, RoundedCornerShape(26.dp), spotColor = Color(0x33000000))
            .clip(RoundedCornerShape(26.dp))
            .background(Color(0xFF1C1C1E).copy(alpha = 0.92f))
            .padding(14.dp)
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
                Column {
                    Text(
                        text = "TUESDAY",
                        color = Color(0xFFFF3B30),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "22",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF3B30).copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("•", color = Color(0xFFFF3B30), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF2C2C2E))
                    .padding(8.dp)
            ) {
                Text(
                    text = "4:00 PM - 5:30 PM",
                    color = Color(0xFFFF9500),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Apple Event: Special Keynote",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
