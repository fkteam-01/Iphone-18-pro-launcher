package com.example.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

enum class AppId {
    PHONE,
    MESSAGES,
    SAFARI,
    MUSIC,
    CAMERA,
    PHOTOS,
    WEATHER,
    CALCULATOR,
    SETTINGS,
    CLOCK,
    NOTES,
    HEALTH,
    APP_STORE,
    MAPS,
    VOICE_MEMOS,
    REMINDERS,
    FILES,
    FACETIME
}

data class AppItem(
    val id: AppId,
    val name: String,
    val iconBackground: Brush,
    val iconColor: Color = Color.White,
    val badgeCount: Int = 0
)

enum class IslandMode {
    IDLE,
    MUSIC,
    TIMER,
    CALL
}

data class NoteItem(
    val id: String,
    val title: String,
    val body: String,
    val date: String,
    val isPinned: Boolean = false
)

data class PhotoItem(
    val id: String,
    val title: String,
    val date: String,
    val gradientColors: List<Color>,
    val isFavorite: Boolean = false
)

data class CallHistoryItem(
    val id: String,
    val contactName: String,
    val type: String, // "Incoming", "Outgoing", "Missed"
    val timeAgo: String,
    val isMissed: Boolean = false
)

data class MessageItem(
    val id: String,
    val sender: String,
    val text: String,
    val time: String,
    val isFromMe: Boolean
)

data class ConversationItem(
    val id: String,
    val sender: String,
    val avatarColor: Color,
    val lastMessage: String,
    val time: String,
    val unread: Boolean = false,
    val messages: List<MessageItem>
)

data class HourlyForecast(
    val time: String,
    val temp: Int,
    val condition: String,
    val pop: Int = 0
)

data class DailyForecast(
    val day: String,
    val condition: String,
    val lowTemp: Int,
    val highTemp: Int
)

data class AlarmItem(
    val id: String,
    val time: String,
    val label: String,
    val isEnabled: Boolean
)

data class LapItem(
    val lapNumber: Int,
    val lapTimeMs: Long,
    val totalTimeMs: Long
)
