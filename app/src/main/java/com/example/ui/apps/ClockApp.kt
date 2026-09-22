package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AlarmItem
import com.example.model.LapItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ClockApp(
    timerSeconds: Int,
    isTimerRunning: Boolean,
    onStartTimer: (Int) -> Unit,
    onStopTimer: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Stopwatch") }
    val tabs = listOf("World Clock", "Alarm", "Stopwatch", "Timer")

    // Stopwatch State
    var stopwatchRunning by remember { mutableStateOf(false) }
    var elapsedMs by remember { mutableLongStateOf(0L) }
    val laps = remember { mutableStateListOf<LapItem>() }
    var lastLapMs by remember { mutableLongStateOf(0L) }

    LaunchedEffect(stopwatchRunning) {
        if (stopwatchRunning) {
            val startTime = System.currentTimeMillis() - elapsedMs
            while (stopwatchRunning) {
                elapsedMs = System.currentTimeMillis() - startTime
                delay(30)
            }
        }
    }

    val alarms = remember {
        mutableStateListOf(
            AlarmItem("1", "6:30 AM", "Wake up", true),
            AlarmItem("2", "7:15 AM", "Gym Session", true),
            AlarmItem("3", "9:00 AM", "Apple Keynote", false)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                when (selectedTab) {
                    "Stopwatch" -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp, vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Large Stopwatch Display
                            val minutes = (elapsedMs / 60000)
                            val seconds = (elapsedMs % 60000) / 1000
                            val millis = (elapsedMs % 1000) / 10

                            Text(
                                text = String.format("%02d:%02d.%02d", minutes, seconds, millis),
                                color = Color.White,
                                fontSize = 64.sp,
                                fontWeight = FontWeight.Thin,
                                modifier = Modifier.padding(vertical = 30.dp)
                            )

                            // Stopwatch Control Buttons (Lap/Reset & Start/Stop)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Lap / Reset Button
                                Box(
                                    modifier = Modifier
                                        .size(76.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF2C2C2E))
                                        .clickable {
                                            if (stopwatchRunning) {
                                                val lapTime = elapsedMs - lastLapMs
                                                lastLapMs = elapsedMs
                                                laps.add(0, LapItem(laps.size + 1, lapTime, elapsedMs))
                                            } else {
                                                elapsedMs = 0L
                                                lastLapMs = 0L
                                                laps.clear()
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (stopwatchRunning) "Lap" else "Reset",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                // Start / Stop Button
                                Box(
                                    modifier = Modifier
                                        .size(76.dp)
                                        .clip(CircleShape)
                                        .background(if (stopwatchRunning) Color(0xFF331F00) else Color(0xFF0A2E12))
                                        .clickable { stopwatchRunning = !stopwatchRunning },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (stopwatchRunning) "Stop" else "Start",
                                        color = if (stopwatchRunning) Color(0xFFFF9500) else Color(0xFF34C759),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Laps Table
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(laps) { lap ->
                                    val lapSec = (lap.lapTimeMs % 60000) / 1000
                                    val lapMil = (lap.lapTimeMs % 1000) / 10
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "Lap ${lap.lapNumber}", color = Color.White, fontSize = 15.sp)
                                        Text(
                                            text = String.format("%02d.%02d", lapSec, lapMil),
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                    "Timer" -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp, vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "Timer",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )

                            val min = timerSeconds / 60
                            val sec = timerSeconds % 60
                            Text(
                                text = String.format("%02d:%02d", min, sec),
                                color = if (isTimerRunning) Color(0xFFFF9500) else Color.White,
                                fontSize = 72.sp,
                                fontWeight = FontWeight.ExtraLight
                            )

                            // Quick timer presets
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                listOf(60 to "1 min", 180 to "3 min", 300 to "5 min", 600 to "10 min").forEach { (duration, label) ->
                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(Color(0xFF2C2C2E))
                                            .clickable { onStartTimer(duration) }
                                            .padding(horizontal = 14.dp, vertical = 8.dp)
                                    ) {
                                        Text(text = label, color = Color.White, fontSize = 13.sp)
                                    }
                                }
                            }

                            // Start / Cancel Timer Buttons
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(76.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF2C2C2E))
                                        .clickable { onStopTimer() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Cancel", color = Color.White, fontSize = 16.sp)
                                }

                                Box(
                                    modifier = Modifier
                                        .size(76.dp)
                                        .clip(CircleShape)
                                        .background(if (isTimerRunning) Color(0xFF331F00) else Color(0xFF0A2E12))
                                        .clickable {
                                            if (isTimerRunning) onStopTimer() else onStartTimer(300)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (isTimerRunning) "Pause" else "Start",
                                        color = if (isTimerRunning) Color(0xFFFF9500) else Color(0xFF34C759),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                    "Alarm" -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {
                            Text("Alarm", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(16.dp))

                            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                                items(alarms) { alarm ->
                                    var enabled by remember { mutableStateOf(alarm.isEnabled) }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(text = alarm.time, color = if (enabled) Color.White else Color.Gray, fontSize = 38.sp, fontWeight = FontWeight.Light)
                                            Text(text = alarm.label, color = Color.Gray, fontSize = 13.sp)
                                        }
                                        Switch(
                                            checked = enabled,
                                            onCheckedChange = { enabled = it },
                                            colors = SwitchDefaults.colors(
                                                checkedThumbColor = Color.White,
                                                checkedTrackColor = Color(0xFF34C759)
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            Text("World Clock", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(20.dp))
                            listOf("Cupertino, USA" to "9:41 AM", "London, UK" to "5:41 PM", "Tokyo, Japan" to "1:41 AM").forEach { (city, time) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(city, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                                    Text(time, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Light)
                                }
                            }
                        }
                    }
                }
            }

            // Bottom Navigation Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF161618))
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEach { tab ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { selectedTab = tab }
                    ) {
                        Icon(
                            imageVector = when (tab) {
                                "World Clock" -> Icons.Default.Language
                                "Alarm" -> Icons.Default.Alarm
                                "Stopwatch" -> Icons.Default.Timer
                                else -> Icons.Default.HourglassBottom
                            },
                            contentDescription = tab,
                            tint = if (selectedTab == tab) Color(0xFFFF9500) else Color.Gray,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = tab,
                            color = if (selectedTab == tab) Color(0xFFFF9500) else Color.Gray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
