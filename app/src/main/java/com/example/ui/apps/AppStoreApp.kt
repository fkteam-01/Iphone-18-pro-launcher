package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun AppStoreApp(
    modifier: Modifier = Modifier
) {
    val storeApps = remember {
        mutableStateListOf(
            Triple("Procreate Dreams", "Animation for Everyone", "GET"),
            Triple("Final Cut Pro", "Pro Video Editing on A19 Pro", "GET"),
            Triple("Logic Pro Mobile", "Music Studio in your pocket", "GET"),
            Triple("Flightradar24", "Live Air Traffic Telemetry", "OPEN"),
            Triple("Duolingo", "Learn 40+ Languages Free", "OPEN")
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("TUESDAY, SEPTEMBER 22", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("Today", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2C2C2E)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Account", tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Featured Hero Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF1E1B4B))
                    .padding(20.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Text("FEATURED APP", color = Color(0xFF818CF8), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text("Spatial Creator Pro", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Built for iPhone 18 Pro Max A19 Pro Neural Engine", color = Color.LightGray, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("POPULAR ON iOS 18", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                items(storeApps) { (name, desc, status) ->
                    var btnStatus by remember { mutableStateOf(status) }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF2C2C2E)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Apps, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = name, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = desc, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF2C2C2E))
                                .clickable {
                                    btnStatus = if (btnStatus == "GET") "OPEN" else "GET"
                                }
                                .padding(horizontal = 18.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = btnStatus,
                                color = Color(0xFF007AFF),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
