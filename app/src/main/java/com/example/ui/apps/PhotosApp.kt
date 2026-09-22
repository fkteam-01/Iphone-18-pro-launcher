package com.example.ui.apps

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PhotoItem

@Composable
fun PhotosApp(
    photos: List<PhotoItem>,
    modifier: Modifier = Modifier
) {
    var selectedPhoto by remember { mutableStateOf<PhotoItem?>(null) }
    var selectedTab by remember { mutableStateOf("Library") }
    val tabs = listOf("Library", "For You", "Albums", "Search")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Photos Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Photos",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Select",
                        color = Color(0xFF007AFF),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Photos Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(photos) { photo ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(Brush.linearGradient(photo.gradientColors))
                            .clickable { selectedPhoto = photo },
                        contentAlignment = Alignment.BottomStart
                    ) {
                        if (photo.isFavorite) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorite",
                                tint = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(14.dp)
                            )
                        }
                    }
                }
            }

            // Bottom Navigation Bar
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
                                "Library" -> Icons.Default.PhotoLibrary
                                "For You" -> Icons.Default.Favorite
                                "Albums" -> Icons.Default.Folder
                                else -> Icons.Default.Search
                            },
                            contentDescription = tab,
                            tint = if (selectedTab == tab) Color(0xFF007AFF) else Color.Gray,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = tab,
                            color = if (selectedTab == tab) Color(0xFF007AFF) else Color.Gray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Fullscreen Photo Viewer Dialog
        selectedPhoto?.let { photo ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { selectedPhoto = null }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = photo.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(text = photo.date, color = Color.Gray, fontSize = 12.sp)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.MoreHoriz, contentDescription = "More", tint = Color.White)
                        }
                    }

                    // Main Photo Display
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Brush.linearGradient(photo.gradientColors)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Shot on iPhone 18 Pro Max",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "24mm • ƒ/1.6 • 200MP Fusion Sensor",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Bottom Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color(0xFF007AFF), modifier = Modifier.size(24.dp))
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite", tint = Color(0xFF007AFF), modifier = Modifier.size(24.dp))
                        Icon(Icons.Default.Info, contentDescription = "Details", tint = Color(0xFF007AFF), modifier = Modifier.size(24.dp))
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFF3B30), modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
    }
}
