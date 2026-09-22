package com.example.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.IslandMode

@Composable
fun DynamicIsland(
    mode: IslandMode,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    // Music props
    songTitle: String,
    artist: String,
    isPlayingMusic: Boolean,
    musicProgress: Float,
    onTogglePlayMusic: () -> Unit,
    onNextTrack: () -> Unit,
    // Timer props
    timerSeconds: Int,
    onStopTimer: () -> Unit,
    // Call props
    isCallActive: Boolean,
    callContact: String,
    callDuration: String,
    onEndCall: () -> Unit,
    modifier: Modifier = Modifier
) {
    val springSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMediumLow
    )

    // Animated dimensions
    val islandWidth by animateDpAsState(
        targetValue = when {
            isExpanded -> 350.dp
            mode == IslandMode.MUSIC -> 190.dp
            mode == IslandMode.TIMER -> 170.dp
            mode == IslandMode.CALL -> 200.dp
            else -> 126.dp
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "islandWidth"
    )

    val islandHeight by animateDpAsState(
        targetValue = when {
            isExpanded -> when (mode) {
                IslandMode.MUSIC -> 175.dp
                IslandMode.TIMER -> 150.dp
                IslandMode.CALL -> 160.dp
                else -> 120.dp
            }
            else -> 36.dp
        },
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "islandHeight"
    )

    Box(
        modifier = modifier
            .width(islandWidth)
            .height(islandHeight)
            .shadow(
                elevation = if (isExpanded) 20.dp else 4.dp,
                shape = RoundedCornerShape(if (isExpanded) 38.dp else 20.dp),
                ambientColor = Color.Black,
                spotColor = Color.Black
            )
            .clip(RoundedCornerShape(if (isExpanded) 38.dp else 20.dp))
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onToggleExpand
            )
            .padding(horizontal = if (isExpanded) 18.dp else 12.dp, vertical = if (isExpanded) 14.dp else 4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!isExpanded) {
            // Compact State
            when (mode) {
                IslandMode.MUSIC -> {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Left: Mini Album Art
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFFFF2D55), Color(0xFFFA2D48))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MusicNote,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        // Right: Animated Equalizer Wave
                        EqualizerWave(isPlaying = isPlayingMusic)
                    }
                }
                IslandMode.TIMER -> {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = Color(0xFFFF9500),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        val min = timerSeconds / 60
                        val sec = timerSeconds % 60
                        Text(
                            text = String.format("%d:%02d", min, sec),
                            color = Color(0xFFFF9500),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                IslandMode.CALL -> {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = Color(0xFF34C759),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = callDuration,
                                color = Color(0xFF34C759),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        EqualizerWave(isPlaying = true, color = Color(0xFF34C759))
                    }
                }
                IslandMode.IDLE -> {
                    // Sleek Camera & TrueDepth cutouts
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        // Front camera lens with subtle glare
                        Box(
                            modifier = Modifier
                                .size(11.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF0F1115))
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .align(Alignment.Center)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1E2638))
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        } else {
            // Expanded Dynamic Island
            when (mode) {
                IslandMode.MUSIC -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Album Art
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFFFC3C44), Color(0xFF8E2DE2))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = songTitle,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = artist,
                                    color = Color(0xFFAAAAAA),
                                    fontSize = 13.sp,
                                    maxLines = 1
                                )
                            }
                            // Equalizer icon
                            EqualizerWave(isPlaying = isPlayingMusic, modifier = Modifier.padding(end = 6.dp))
                        }

                        // Progress bar
                        Column {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color(0xFF333338))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(musicProgress.coerceIn(0f, 1f))
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(Color.White)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("1:12", color = Color(0xFF888888), fontSize = 11.sp)
                                Text("-2:38", color = Color(0xFF888888), fontSize = 11.sp)
                            }
                        }

                        // Playback Controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.SkipPrevious,
                                    contentDescription = "Prev",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            IconButton(onClick = onTogglePlayMusic) {
                                Icon(
                                    imageVector = if (isPlayingMusic) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = Color.White,
                                    modifier = Modifier.size(34.dp)
                                )
                            }
                            IconButton(onClick = onNextTrack) {
                                Icon(
                                    imageVector = Icons.Default.SkipNext,
                                    contentDescription = "Next",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }
                }
                IslandMode.TIMER -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = Color(0xFFFF9500),
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Timer",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            val min = timerSeconds / 60
                            val sec = timerSeconds % 60
                            Text(
                                text = String.format("%d:%02d", min, sec),
                                color = Color(0xFFFF9500),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color(0xFF331F00))
                                    .clickable { onStopTimer() }
                                    .padding(horizontal = 18.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "Stop",
                                    color = Color(0xFFFF9500),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
                IslandMode.CALL -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF34C759)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = callContact,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "iPhone 18 Pro Max HD Voice • $callDuration",
                                        color = Color(0xFF34C759),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2C2C2E)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.MicOff, contentDescription = "Mute", tint = Color.White)
                            }
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2C2C2E)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Speaker", tint = Color.White)
                            }
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFF3B30))
                                    .clickable { onEndCall() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.CallEnd, contentDescription = "End", tint = Color.White)
                            }
                        }
                    }
                }
                IslandMode.IDLE -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Dynamic Island",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "No active background tasks",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EqualizerWave(
    isPlaying: Boolean,
    color: Color = Color(0xFF34C759),
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveTransition")
    val h1 by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(400, easing = LinearEasing), RepeatMode.Reverse),
        label = "h1"
    )
    val h2 by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 0.2f,
        animationSpec = infiniteRepeatable(tween(550, easing = LinearEasing), RepeatMode.Reverse),
        label = "h2"
    )
    val h3 by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 1.0f,
        animationSpec = infiniteRepeatable(tween(350, easing = LinearEasing), RepeatMode.Reverse),
        label = "h3"
    )

    Canvas(modifier = modifier.size(width = 16.dp, height = 12.dp)) {
        val barWidth = 2.5.dp.toPx()
        val spacing = 2.dp.toPx()

        val heights = if (isPlaying) listOf(h1, h2, h3) else listOf(0.3f, 0.5f, 0.3f)

        heights.forEachIndexed { i, factor ->
            val barH = size.height * factor
            val x = i * (barWidth + spacing)
            val y = (size.height - barH) / 2f
            drawRoundRect(
                color = color,
                topLeft = Offset(x, y),
                size = androidx.compose.ui.geometry.Size(barWidth, barH),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.dp.toPx(), 1.dp.toPx())
            )
        }
    }
}
