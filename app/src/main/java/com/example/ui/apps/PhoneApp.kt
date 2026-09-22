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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PhoneApp(
    isCallActive: Boolean,
    callContact: String,
    callDuration: String,
    onStartCall: (String) -> Unit,
    onEndCall: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("Keypad") }
    val tabs = listOf("Favorites", "Recents", "Contacts", "Keypad", "Voicemail")
    var dialedNumber by remember { mutableStateOf("") }

    val contacts = listOf(
        "Tim Cook" to "Apple Inc.",
        "Craig Federighi" to "Software Engineering",
        "Sarah Jenkins" to "Mobile",
        "Design Team" to "Cupertino",
        "Alex Rivera" to "Home",
        "Support Hotline" to "1-800-MY-APPLE"
    )

    val recents = listOf(
        Triple("Tim Cook", "Outgoing", "9:41 AM"),
        Triple("Sarah Jenkins", "Missed", "Yesterday"),
        Triple("Unknown Caller", "Spam", "Sep 20"),
        Triple("Craig Federighi", "Incoming", "Sep 18")
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (isCallActive) {
            // In-Call Active Screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 40.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = callContact,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "iPhone 18 Pro Max HD Voice • $callDuration",
                        color = Color(0xFF34C759),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Call Actions Grid (Mute, Keypad, Speaker, Add Call, FaceTime, Contacts)
                Column(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CallActionButton(icon = Icons.Default.MicOff, label = "mute")
                        CallActionButton(icon = Icons.Default.Dialpad, label = "keypad")
                        CallActionButton(icon = Icons.Default.VolumeUp, label = "speaker")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CallActionButton(icon = Icons.Default.Add, label = "add call")
                        CallActionButton(icon = Icons.Default.Videocam, label = "FaceTime")
                        CallActionButton(icon = Icons.Default.AccountCircle, label = "contacts")
                    }
                }

                // End Call Button
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF3B30))
                        .clickable { onEndCall() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CallEnd,
                        contentDescription = "End Call",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }
        } else {
            // Main Phone Tabs Screen
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    when (selectedTab) {
                        "Keypad" -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // Dialed number display
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = dialedNumber,
                                        color = Color.White,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1
                                    )
                                }

                                // Keypad Matrix (1-9, *, 0, #)
                                val keyRows = listOf(
                                    listOf("1" to "", "2" to "A B C", "3" to "D E F"),
                                    listOf("4" to "G H I", "5" to "J K L", "6" to "M N O"),
                                    listOf("7" to "P Q R S", "8" to "T U V", "9" to "W X Y Z"),
                                    listOf("*" to "", "0" to "+", "#" to "")
                                )

                                Column(
                                    verticalArrangement = Arrangement.spacedBy(14.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    keyRows.forEach { row ->
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            row.forEach { (digit, letters) ->
                                                KeypadButton(
                                                    digit = digit,
                                                    letters = letters,
                                                    onClick = { dialedNumber += digit }
                                                )
                                            }
                                        }
                                    }

                                    // Green Call Button Row with Backspace
                                    Row(
                                        modifier = Modifier.fillMaxWidth(0.8f),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.size(72.dp))

                                        // Call Button
                                        Box(
                                            modifier = Modifier
                                                .size(72.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFF34C759))
                                                .clickable {
                                                    val target = dialedNumber.ifEmpty { "Tim Cook" }
                                                    onStartCall(target)
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Phone,
                                                contentDescription = "Call",
                                                tint = Color.White,
                                                modifier = Modifier.size(34.dp)
                                            )
                                        }

                                        // Backspace Button
                                        if (dialedNumber.isNotEmpty()) {
                                            IconButton(onClick = { dialedNumber = dialedNumber.dropLast(1) }) {
                                                Icon(
                                                    imageVector = Icons.Default.Backspace,
                                                    contentDescription = "Delete",
                                                    tint = Color.Gray,
                                                    modifier = Modifier.size(24.dp)
                                                )
                                            }
                                        } else {
                                            Spacer(modifier = Modifier.size(48.dp))
                                        }
                                    }
                                }
                            }
                        }
                        "Contacts" -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    text = "Contacts",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    items(contacts) { (name, type) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { onStartCall(name) }
                                                .padding(vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(CircleShape)
                                                    .background(Color(0xFF2C2C2E)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = name.take(1),
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(14.dp))
                                            Column {
                                                Text(text = name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                                Text(text = type, color = Color.Gray, fontSize = 12.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        "Recents" -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp, vertical = 12.dp)
                            ) {
                                Text(
                                    text = "Recents",
                                    color = Color.White,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    items(recents) { (caller, type, time) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { onStartCall(caller) }
                                                .padding(vertical = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(
                                                    text = caller,
                                                    color = if (type == "Missed") Color(0xFFFF3B30) else Color.White,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                                Text(text = type, color = Color.Gray, fontSize = 12.sp)
                                            }
                                            Text(text = time, color = Color.Gray, fontSize = 13.sp)
                                        }
                                    }
                                }
                            }
                        }
                        else -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = "$selectedTab Tab", color = Color.Gray, fontSize = 16.sp)
                            }
                        }
                    }
                }

                // Phone Bottom Tab Bar
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
                                    "Favorites" -> Icons.Default.Star
                                    "Recents" -> Icons.Default.AccessTime
                                    "Contacts" -> Icons.Default.Person
                                    "Keypad" -> Icons.Default.Dialpad
                                    else -> Icons.Default.Voicemail
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
        }
    }
}

@Composable
private fun KeypadButton(
    digit: String,
    letters: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Color(0xFF2C2C2E))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = digit,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )
            if (letters.isNotEmpty()) {
                Text(
                    text = letters,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
private fun CallActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color(0xFF2C2C2E)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = label, color = Color.White, fontSize = 12.sp)
    }
}
