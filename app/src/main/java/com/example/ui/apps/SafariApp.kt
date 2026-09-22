package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SafariApp(
    modifier: Modifier = Modifier
) {
    var urlText by remember { mutableStateOf("apple.com") }
    var currentWebPage by remember { mutableStateOf("Apple") }

    val bookmarks = listOf(
        "Apple" to Color(0xFF000000),
        "Google" to Color(0xFF4285F4),
        "Wikipedia" to Color(0xFF666666),
        "GitHub" to Color(0xFF24292E),
        "YouTube" to Color(0xFFFF0000),
        "Reddit" to Color(0xFFFF4500)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Web Page Content Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF121214))
            ) {
                when (currentWebPage) {
                    "Apple" -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = " iPhone 18 Pro Max",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Titanium. So strong. So light. So Pro.",
                                color = Color(0xFFA1A1A6),
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(24.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color(0xFF1E1E24))
                                    .padding(20.dp)
                            ) {
                                Column {
                                    Text("Highlights", color = Color(0xFF2997FF), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("• A19 Pro Bionic with 6-core GPU", color = Color.White, fontSize = 14.sp)
                                    Text("• 200MP Fusion Camera with 5x Telephoto", color = Color.White, fontSize = 14.sp)
                                    Text("• ProMotion 1-120Hz Super Retina XDR OLED", color = Color.White, fontSize = 14.sp)
                                    Text("• Grade-5 Titanium Chassis & Action Button", color = Color.White, fontSize = 14.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(Color(0xFF0071E3))
                                    .padding(horizontal = 24.dp, vertical = 10.dp)
                            ) {
                                Text("Pre-order Now", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            }
                        }
                    }
                    else -> {
                        // Start Page with Bookmarks
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {
                            Text(
                                text = "Favorites",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(bookmarks) { (name, color) ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.clickable {
                                            currentWebPage = name
                                            urlText = "${name.lowercase()}.com"
                                        }
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(color),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = name.take(2),
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(text = name, color = Color.White, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Safari Bottom Address Bar & Toolbar (iOS 18 layout)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1C1C1E))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                // Address Bar Capsule
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF2C2C2E))
                        .padding(horizontal = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("aA", color = Color.LightGray, fontSize = 13.sp, fontWeight = FontWeight.Bold)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Lock, contentDescription = "SSL", tint = Color.LightGray, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = urlText,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reload",
                            tint = Color.LightGray,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { currentWebPage = "Apple" }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Navigation Icons (Back, Forward, Share, Bookmarks, Tabs)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color(0xFF007AFF),
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                currentWebPage = "Favorites"
                                urlText = "apple.com"
                            }
                    )
                    Icon(Icons.Default.ArrowForwardIos, contentDescription = "Forward", tint = Color.Gray, modifier = Modifier.size(18.dp))
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = Color(0xFF007AFF), modifier = Modifier.size(20.dp))
                    Icon(Icons.Default.BookmarkBorder, contentDescription = "Bookmarks", tint = Color(0xFF007AFF), modifier = Modifier.size(20.dp))
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF007AFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("1", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
