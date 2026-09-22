package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DailyForecast
import com.example.model.HourlyForecast

@Composable
fun WeatherApp(
    modifier: Modifier = Modifier
) {
    val hourlyList = listOf(
        HourlyForecast("Now", 72, "Sunny"),
        HourlyForecast("10 AM", 73, "Sunny"),
        HourlyForecast("11 AM", 75, "Sunny"),
        HourlyForecast("12 PM", 76, "Partly Cloudy"),
        HourlyForecast("1 PM", 76, "Partly Cloudy"),
        HourlyForecast("2 PM", 75, "Sunny"),
        HourlyForecast("3 PM", 74, "Sunny"),
        HourlyForecast("4 PM", 72, "Sunny"),
        HourlyForecast("5 PM", 70, "Sunny"),
        HourlyForecast("6 PM", 68, "Sunset")
    )

    val dailyList = listOf(
        DailyForecast("Today", "Sunny", 58, 76),
        DailyForecast("Wed", "Partly Cloudy", 57, 74),
        DailyForecast("Thu", "Sunny", 59, 78),
        DailyForecast("Fri", "Sunny", 60, 80),
        DailyForecast("Sat", "Cloudy", 56, 73),
        DailyForecast("Sun", "Showers", 54, 69),
        DailyForecast("Mon", "Sunny", 55, 71)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF2C7BF6), Color(0xFF1E5EC7), Color(0xFF103A7C))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // City & Current Weather Header
            Text(
                text = "Cupertino",
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "72°",
                color = Color.White,
                fontSize = 86.sp,
                fontWeight = FontWeight.Thin,
                letterSpacing = (-2).sp
            )
            Text(
                text = "Mostly Sunny",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "H:76°  L:58°",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Hourly Forecast Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0x33000000))
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = "Sunny conditions expected around 10:00 AM.",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(hourlyList) { hour ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = hour.time, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                Spacer(modifier = Modifier.height(6.dp))
                                Icon(
                                    imageVector = if (hour.condition == "Sunset") Icons.Default.NightsStay else Icons.Default.WbSunny,
                                    contentDescription = null,
                                    tint = if (hour.condition == "Sunset") Color(0xFFFF9500) else Color(0xFFFFCC00),
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = "${hour.temp}°", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 10-Day Forecast Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0x33000000))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("10-DAY FORECAST", color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    dailyList.forEach { daily ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = daily.day, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.width(50.dp))
                            Icon(Icons.Default.WbSunny, contentDescription = null, tint = Color(0xFFFFCC00), modifier = Modifier.size(18.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "${daily.lowTemp}°", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(Color(0xFF34C759), Color(0xFFFFCC00))
                                            )
                                        )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "${daily.highTemp}°", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Grid Metrics (UV Index, Air Quality, Wind, Humidity)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WeatherMetricCard(
                    title = "UV INDEX",
                    value = "3",
                    subtitle = "Moderate for the rest of the day",
                    modifier = Modifier.weight(1f)
                )
                WeatherMetricCard(
                    title = "WIND",
                    value = "6 mph",
                    subtitle = "WNW • Gusts up to 10 mph",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun WeatherMetricCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(130.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0x33000000))
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, color = Color.LightGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text(text = value, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            Text(text = subtitle, color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
        }
    }
}
