package com.example.ui.apps

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun VoiceMemosApp(
    modifier: Modifier = Modifier
) {
    var isRecording by remember { mutableStateOf(false) }
    var recordingSec by remember { mutableIntStateOf(0) }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            recordingSec = 0
            while (isRecording) {
                delay(1000)
                recordingSec++
            }
        }
    }

    val memos = remember {
        mutableStateListOf(
            Triple("New Recording 1", "00:42", "Today"),
            Triple("Meeting Notes with Tim", "03:15", "Yesterday"),
            Triple("A19 Architecture Idea", "01:28", "Sep 18")
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Text(
                text = "Voice Memos",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            // Waveform animation box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFF1C1C1E)),
                contentAlignment = Alignment.Center
            ) {
                if (isRecording) {
                    WaveformVisualizer()
                } else {
                    Text(
                        text = "Tap red button to record",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            // Saved Recordings List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(memos) { (name, duration, date) ->
                    var isPlaying by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF161618))
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = name, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "$date • $duration", color = Color.Gray, fontSize = 12.sp)
                        }

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2C2C2E))
                                .clickable { isPlaying = !isPlaying },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Bottom Recording Controls
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isRecording) {
                    val m = recordingSec / 60
                    val s = recordingSec % 60
                    Text(
                        text = String.format("%02d:%02d", m, s),
                        color = Color(0xFFFF3B30),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Big Red Record / Stop Button
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            if (isRecording) {
                                memos.add(0, Triple("New Recording ${memos.size + 1}", String.format("%02d:%02d", recordingSec / 60, recordingSec % 60), "Just now"))
                            }
                            isRecording = !isRecording
                        }
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(if (isRecording) 28.dp else 56.dp)
                            .clip(if (isRecording) RoundedCornerShape(6.dp) else CircleShape)
                            .background(Color(0xFFFF3B30))
                    )
                }
            }
        }
    }
}

@Composable
private fun WaveformVisualizer() {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    val factor by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(300, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "waveFactor"
    )

    Canvas(modifier = Modifier.fillMaxWidth(0.85f).height(60.dp)) {
        val bars = 30
        val barWidth = (size.width / bars) * 0.6f
        val spacing = (size.width / bars) * 0.4f

        for (i in 0 until bars) {
            val randomH = (((i * 13) % 10) / 10f) * factor * size.height
            val x = i * (barWidth + spacing)
            val y = (size.height - randomH) / 2f
            drawRoundRect(
                color = Color(0xFFFF3B30),
                topLeft = Offset(x, y),
                size = Size(barWidth, randomH.coerceAtLeast(4f)),
                cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
            )
        }
    }
}
