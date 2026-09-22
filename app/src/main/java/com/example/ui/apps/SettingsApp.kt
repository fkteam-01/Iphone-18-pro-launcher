package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsApp(
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    isWifiOn: Boolean,
    onToggleWifi: () -> Unit,
    isBluetoothOn: Boolean,
    onToggleBluetooth: () -> Unit,
    isAirplaneMode: Boolean,
    onToggleAirplane: () -> Unit,
    currentWallpaperIndex: Int,
    onSelectWallpaper: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDarkMode) Color(0xFF000000) else Color(0xFFF2F2F7))
    ) {
        if (selectedSection == "About") {
            // About iPhone 18 Pro Max Sub-page
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { selectedSection = null }
                        .padding(vertical = 12.dp)
                ) {
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back", tint = Color(0xFF007AFF), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Settings", color = Color(0xFF007AFF), fontSize = 16.sp)
                }

                Text(
                    text = "About",
                    color = if (isDarkMode) Color.White else Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                SettingsGroupCard(isDarkMode = isDarkMode) {
                    SettingsRowText("Name", "John's iPhone 18 Pro Max", isDarkMode)
                    HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                    SettingsRowText("iOS Version", "18.2 (22C152)", isDarkMode)
                    HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                    SettingsRowText("Model Name", "iPhone 18 Pro Max", isDarkMode)
                    HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                    SettingsRowText("Model Number", "A3102", isDarkMode)
                    HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                    SettingsRowText("Serial Number", "DNPZ9821K919", isDarkMode)
                    HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                    SettingsRowText("Processor", "Apple A19 Pro Bionic (3nm Gen 2)", isDarkMode)
                    HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                    SettingsRowText("Capacity", "512 GB (74 GB Available)", isDarkMode)
                }

                Spacer(modifier = Modifier.height(16.dp))

                SettingsGroupCard(isDarkMode = isDarkMode) {
                    SettingsRowText("Battery Health", "100% Maximum Capacity", isDarkMode)
                    HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                    SettingsRowText("AppleCare+", "Coverage Active until 2028", isDarkMode)
                }
            }
        } else {
            // Main Settings Menu
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    Text(
                        text = "Settings",
                        color = if (isDarkMode) Color.White else Color.Black,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Apple ID Card
                item {
                    SettingsGroupCard(isDarkMode = isDarkMode) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF8E8E93)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("JA", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text("John Appleseed", color = if (isDarkMode) Color.White else Color.Black, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                                Text("Apple Account, iCloud+, Media & Purchases", color = Color.Gray, fontSize = 12.sp)
                            }
                        }
                    }
                }

                // Connectivity Group (Airplane, Wi-Fi, Bluetooth, Cellular)
                item {
                    SettingsGroupCard(isDarkMode = isDarkMode) {
                        SettingsRowToggle(
                            icon = Icons.Default.AirplanemodeActive,
                            iconBg = Color(0xFFFF9500),
                            title = "Airplane Mode",
                            checked = isAirplaneMode,
                            onCheckedChange = { onToggleAirplane() },
                            isDarkMode = isDarkMode
                        )
                        HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                        SettingsRowToggle(
                            icon = Icons.Default.Wifi,
                            iconBg = Color(0xFF007AFF),
                            title = "Wi-Fi",
                            checked = isWifiOn && !isAirplaneMode,
                            onCheckedChange = { onToggleWifi() },
                            isDarkMode = isDarkMode
                        )
                        HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                        SettingsRowToggle(
                            icon = Icons.Default.Bluetooth,
                            iconBg = Color(0xFF007AFF),
                            title = "Bluetooth",
                            checked = isBluetoothOn,
                            onCheckedChange = { onToggleBluetooth() },
                            isDarkMode = isDarkMode
                        )
                    }
                }

                // System & General
                item {
                    SettingsGroupCard(isDarkMode = isDarkMode) {
                        SettingsRowNav(
                            icon = Icons.Default.Settings,
                            iconBg = Color(0xFF8E8E93),
                            title = "General",
                            detail = "About, AirDrop, Updates",
                            onClick = { selectedSection = "About" },
                            isDarkMode = isDarkMode
                        )
                        HorizontalDivider(color = if (isDarkMode) Color(0xFF38383A) else Color(0xFFE5E5EA))
                        SettingsRowToggle(
                            icon = Icons.Default.DarkMode,
                            iconBg = Color(0xFF5856D6),
                            title = "Dark Appearance",
                            checked = isDarkMode,
                            onCheckedChange = { onToggleDarkMode() },
                            isDarkMode = isDarkMode
                        )
                    }
                }

                // Wallpaper Chooser
                item {
                    Text(
                        text = "WALLPAPER",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                    )
                    SettingsGroupCard(isDarkMode = isDarkMode) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Choose iOS 18 Wallpaper",
                                color = if (isDarkMode) Color.White else Color.Black,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val wallpapers = listOf("Titanium Flow", "Obsidian Deep", "Aurora Glow")
                                wallpapers.forEachIndexed { index, name ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(horizontal = 4.dp)
                                            .height(70.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                when (index) {
                                                    0 -> Color(0xFF1E222A)
                                                    1 -> Color(0xFF0F172A)
                                                    else -> Color(0xFF2C1E3A)
                                                }
                                            )
                                            .clickable { onSelectWallpaper(index) }
                                            .padding(8.dp),
                                        contentAlignment = Alignment.BottomStart
                                    ) {
                                        Text(
                                            text = name,
                                            color = if (currentWallpaperIndex == index) Color(0xFFFFCC00) else Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsGroupCard(
    isDarkMode: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (isDarkMode) Color(0xFF1C1C1E) else Color.White),
        content = content
    )
}

@Composable
private fun SettingsRowToggle(
    icon: ImageVector,
    iconBg: Color,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isDarkMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, color = if (isDarkMode) Color.White else Color.Black, fontSize = 15.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF34C759)
            )
        )
    }
}

@Composable
private fun SettingsRowNav(
    icon: ImageVector,
    iconBg: Color,
    title: String,
    detail: String = "",
    onClick: () -> Unit,
    isDarkMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, color = if (isDarkMode) Color.White else Color.Black, fontSize = 15.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (detail.isNotEmpty()) {
                Text(text = detail, color = Color.Gray, fontSize = 13.sp)
                Spacer(modifier = Modifier.width(6.dp))
            }
            Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
        }
    }
}

@Composable
private fun SettingsRowText(
    label: String,
    value: String,
    isDarkMode: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = if (isDarkMode) Color.White else Color.Black, fontSize = 15.sp)
        Text(text = value, color = Color.Gray, fontSize = 14.sp)
    }
}
