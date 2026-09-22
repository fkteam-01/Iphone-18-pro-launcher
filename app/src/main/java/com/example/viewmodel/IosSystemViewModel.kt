package com.example.viewmodel

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class IosSystemViewModel : ViewModel() {

    // System Navigation State
    private val _activeApp = MutableStateFlow<AppId?>(null)
    val activeApp: StateFlow<AppId?> = _activeApp.asStateFlow()

    private val _isLocked = MutableStateFlow(false)
    val isLocked: StateFlow<Boolean> = _isLocked.asStateFlow()

    private val _isControlCenterOpen = MutableStateFlow(false)
    val isControlCenterOpen: StateFlow<Boolean> = _isControlCenterOpen.asStateFlow()

    private val _isSpotlightOpen = MutableStateFlow(false)
    val isSpotlightOpen: StateFlow<Boolean> = _isSpotlightOpen.asStateFlow()

    private val _isAppSwitcherOpen = MutableStateFlow(false)
    val isAppSwitcherOpen: StateFlow<Boolean> = _isAppSwitcherOpen.asStateFlow()

    // Hardware frame toggle (True = Titanium iPhone 18 Pro Max Chassis, False = Full Screen)
    private val _showTitaniumFrame = MutableStateFlow(true)
    val showTitaniumFrame: StateFlow<Boolean> = _showTitaniumFrame.asStateFlow()

    // Dynamic Island State
    private val _islandMode = MutableStateFlow(IslandMode.IDLE)
    val islandMode: StateFlow<IslandMode> = _islandMode.asStateFlow()

    private val _isIslandExpanded = MutableStateFlow(false)
    val isIslandExpanded: StateFlow<Boolean> = _isIslandExpanded.asStateFlow()

    // System Appearance & Controls
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _brightness = MutableStateFlow(0.85f)
    val brightness: StateFlow<Float> = _brightness.asStateFlow()

    private val _volume = MutableStateFlow(0.70f)
    val volume: StateFlow<Float> = _volume.asStateFlow()

    private val _isWifiOn = MutableStateFlow(true)
    val isWifiOn: StateFlow<Boolean> = _isWifiOn.asStateFlow()

    private val _isBluetoothOn = MutableStateFlow(true)
    val isBluetoothOn: StateFlow<Boolean> = _isBluetoothOn.asStateFlow()

    private val _isCellularOn = MutableStateFlow(true)
    val isCellularOn: StateFlow<Boolean> = _isCellularOn.asStateFlow()

    private val _isAirplaneMode = MutableStateFlow(false)
    val isAirplaneMode: StateFlow<Boolean> = _isAirplaneMode.asStateFlow()

    private val _isFlashlightOn = MutableStateFlow(false)
    val isFlashlightOn: StateFlow<Boolean> = _isFlashlightOn.asStateFlow()

    private val _isLowPowerMode = MutableStateFlow(false)
    val isLowPowerMode: StateFlow<Boolean> = _isLowPowerMode.asStateFlow()

    private val _wallpaperIndex = MutableStateFlow(0)
    val wallpaperIndex: StateFlow<Int> = _wallpaperIndex.asStateFlow()

    private val _batteryLevel = MutableStateFlow(94)
    val batteryLevel: StateFlow<Int> = _batteryLevel.asStateFlow()

    private val _currentTime = MutableStateFlow("9:41")
    val currentTime: StateFlow<String> = _currentTime.asStateFlow()

    private val _currentDate = MutableStateFlow("Tuesday, September 22")
    val currentDate: StateFlow<String> = _currentDate.asStateFlow()

    // Music Player State
    private val _isPlayingMusic = MutableStateFlow(false)
    val isPlayingMusic: StateFlow<Boolean> = _isPlayingMusic.asStateFlow()

    private val _currentSongTitle = MutableStateFlow("Starboy")
    val currentSongTitle: StateFlow<String> = _currentSongTitle.asStateFlow()

    private val _currentArtist = MutableStateFlow("The Weeknd ft. Daft Punk")
    val currentArtist: StateFlow<String> = _currentArtist.asStateFlow()

    private val _musicProgress = MutableStateFlow(0.35f)
    val musicProgress: StateFlow<Float> = _musicProgress.asStateFlow()

    private var musicJob: Job? = null

    // Timer State
    private val _timerSeconds = MutableStateFlow(0)
    val timerSeconds: StateFlow<Int> = _timerSeconds.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private var timerJob: Job? = null

    // Phone Call Simulation State
    private val _isCallActive = MutableStateFlow(false)
    val isCallActive: StateFlow<Boolean> = _isCallActive.asStateFlow()

    private val _callContactName = MutableStateFlow("Tim Cook")
    val callContactName: StateFlow<String> = _callContactName.asStateFlow()

    private val _callDurationSec = MutableStateFlow("00:00")
    val callDurationSec: StateFlow<String> = _callDurationSec.asStateFlow()
    private var callJob: Job? = null

    // Calculator State
    private val _calcDisplay = MutableStateFlow("0")
    val calcDisplay: StateFlow<String> = _calcDisplay.asStateFlow()

    private var calcFirstOperand: Double? = null
    private var calcPendingOperator: String? = null
    private var calcClearOnNextDigit = false

    // Notes State
    private val _notesList = MutableStateFlow<List<NoteItem>>(
        listOf(
            NoteItem(
                id = "1",
                title = "iPhone 18 Pro Max Specs",
                body = "A19 Pro Bionic with 3nm 2nd Gen, Titanium frame, Under-display Face ID, 200MP Fusion telephoto lens.",
                date = "Today",
                isPinned = true
            ),
            NoteItem(
                id = "2",
                title = "Shopping List",
                body = "Organic oat milk, avocados, matcha powder, espresso beans, sourdough bread.",
                date = "Yesterday",
                isPinned = false
            ),
            NoteItem(
                id = "3",
                title = "WWDC Ideas 2026",
                body = "Dynamic Island multi-tasking widgets, holographic Siri interface, spatial memory capture.",
                date = "Sep 18",
                isPinned = false
            )
        )
    )
    val notesList: StateFlow<List<NoteItem>> = _notesList.asStateFlow()

    // Photos State
    private val _photosList = MutableStateFlow<List<PhotoItem>>(
        listOf(
            PhotoItem("1", "Sierra Mountain Sunset", "Yesterday", listOf(Color(0xFFFF512F), Color(0xFFDD2476))),
            PhotoItem("2", "Pacific Ocean Wave", "Sep 19", listOf(Color(0xFF2193B0), Color(0xFF6DD5ED))),
            PhotoItem("3", "Tokyo Neon Night", "Sep 15", listOf(Color(0xFF8A2387), Color(0xFFE94057), Color(0xFFF27121))),
            PhotoItem("4", "Cupertino Apple Park", "Sep 12", listOf(Color(0xFF11998E), Color(0xFF38EF7D))),
            PhotoItem("5", "Space Black Titanium", "Sep 08", listOf(Color(0xFF232526), Color(0xFF414345))),
            PhotoItem("6", "Aurora Borealis", "Sep 01", listOf(Color(0xFF00C9FF), Color(0xFF92FE9D)))
        )
    )
    val photosList: StateFlow<List<PhotoItem>> = _photosList.asStateFlow()

    // Messages State
    private val _conversations = MutableStateFlow<List<ConversationItem>>(
        listOf(
            ConversationItem(
                id = "1",
                sender = "Tim Cook",
                avatarColor = Color(0xFF007AFF),
                lastMessage = "Welcome to the new iPhone 18 Pro Max experience! You're going to love it.",
                time = "9:41 AM",
                unread = true,
                messages = listOf(
                    MessageItem("m1", "Tim Cook", "Hey there! Testing out the new titanium build.", "9:38 AM", false),
                    MessageItem("m2", "Me", "It feels incredibly fast and smooth!", "9:40 AM", true),
                    MessageItem("m3", "Tim Cook", "Welcome to the new iPhone 18 Pro Max experience! You're going to love it.", "9:41 AM", false)
                )
            ),
            ConversationItem(
                id = "2",
                sender = "Sarah",
                avatarColor = Color(0xFFFF2D55),
                lastMessage = "Are we still meeting at Blue Bottle Coffee?",
                time = "8:30 AM",
                unread = false,
                messages = listOf(
                    MessageItem("m4", "Sarah", "Morning! Coffee later?", "8:15 AM", false),
                    MessageItem("m5", "Me", "Yes! 10:30 works great.", "8:25 AM", true),
                    MessageItem("m6", "Sarah", "Are we still meeting at Blue Bottle Coffee?", "8:30 AM", false)
                )
            ),
            ConversationItem(
                id = "3",
                sender = "Design Team",
                avatarColor = Color(0xFF5856D6),
                lastMessage = "The iOS 18 glassmorphism assets are ready for review.",
                time = "Yesterday",
                unread = false,
                messages = listOf(
                    MessageItem("m7", "Design Team", "The iOS 18 glassmorphism assets are ready for review.", "Yesterday", false)
                )
            )
        )
    )
    val conversations: StateFlow<List<ConversationItem>> = _conversations.asStateFlow()

    val appsList: List<AppItem> = listOf(
        AppItem(AppId.FACETIME, "FaceTime", Brush.linearGradient(listOf(Color(0xFF34C759), Color(0xFF11998E)))),
        AppItem(AppId.PHOTOS, "Photos", Brush.linearGradient(listOf(Color(0xFFFFFFFF), Color(0xFFE5E5EA))), iconColor = Color(0xFFFF9500)),
        AppItem(AppId.CAMERA, "Camera", Brush.linearGradient(listOf(Color(0xFF3A3A3C), Color(0xFF1C1C1E)))),
        AppItem(AppId.WEATHER, "Weather", Brush.linearGradient(listOf(Color(0xFF2C7BF6), Color(0xFF1E5EC7)))),
        AppItem(AppId.CLOCK, "Clock", Brush.linearGradient(listOf(Color(0xFF000000), Color(0xFF1C1C1E)))),
        AppItem(AppId.MAPS, "Maps", Brush.linearGradient(listOf(Color(0xFF34C759), Color(0xFF007AFF)))),
        AppItem(AppId.REMINDERS, "Reminders", Brush.linearGradient(listOf(Color(0xFFFFFFFF), Color(0xFFF2F2F7))), iconColor = Color(0xFF007AFF)),
        AppItem(AppId.NOTES, "Notes", Brush.linearGradient(listOf(Color(0xFFFFCC00), Color(0xFFFF9500)))),
        AppItem(AppId.HEALTH, "Health", Brush.linearGradient(listOf(Color(0xFFFFFFFF), Color(0xFFF2F2F7))), iconColor = Color(0xFFFF2D55)),
        AppItem(AppId.APP_STORE, "App Store", Brush.linearGradient(listOf(Color(0xFF007AFF), Color(0xFF5856D6)))),
        AppItem(AppId.CALCULATOR, "Calculator", Brush.linearGradient(listOf(Color(0xFF2C2C2E), Color(0xFF000000)))),
        AppItem(AppId.VOICE_MEMOS, "Voice Memos", Brush.linearGradient(listOf(Color(0xFF1C1C1E), Color(0xFF000000))), iconColor = Color(0xFFFF3B30)),
        AppItem(AppId.SETTINGS, "Settings", Brush.linearGradient(listOf(Color(0xFF8E8E93), Color(0xFF636366)))),
        AppItem(AppId.FILES, "Files", Brush.linearGradient(listOf(Color(0xFF007AFF), Color(0xFF5AC8FA))))
    )

    init {
        startTimeUpdater()
    }

    private fun startTimeUpdater() {
        viewModelScope.launch {
            while (true) {
                val cal = Calendar.getInstance()
                val timeFormat = SimpleDateFormat("h:mm", Locale.getDefault())
                val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
                _currentTime.value = timeFormat.format(cal.time)
                _currentDate.value = dateFormat.format(cal.time)
                delay(10000)
            }
        }
    }

    // App Navigation Handlers
    fun openApp(appId: AppId) {
        _activeApp.value = appId
        _isControlCenterOpen.value = false
        _isSpotlightOpen.value = false
        _isAppSwitcherOpen.value = false
    }

    fun goHome() {
        _activeApp.value = null
        _isControlCenterOpen.value = false
        _isSpotlightOpen.value = false
        _isAppSwitcherOpen.value = false
        _isIslandExpanded.value = false
    }

    fun openAppSwitcher() {
        _isAppSwitcherOpen.value = true
    }

    fun closeAppSwitcher() {
        _isAppSwitcherOpen.value = false
    }

    fun lockDevice() {
        _isLocked.value = true
        _isControlCenterOpen.value = false
        _isSpotlightOpen.value = false
    }

    fun unlockDevice() {
        _isLocked.value = false
    }

    fun openControlCenter() {
        _isControlCenterOpen.value = true
    }

    fun toggleControlCenter() {
        _isControlCenterOpen.update { !it }
    }

    fun closeControlCenter() {
        _isControlCenterOpen.value = false
    }

    fun toggleSpotlight() {
        _isSpotlightOpen.update { !it }
    }

    fun closeSpotlight() {
        _isSpotlightOpen.value = false
    }

    fun toggleTitaniumFrame() {
        _showTitaniumFrame.update { !it }
    }

    // Dynamic Island
    fun toggleIslandExpand() {
        _isIslandExpanded.update { !it }
    }

    fun closeIsland() {
        _isIslandExpanded.value = false
    }

    // System Settings
    fun toggleDarkMode() {
        _isDarkMode.update { !it }
    }

    fun setBrightness(value: Float) {
        _brightness.value = value.coerceIn(0.1f, 1.0f)
    }

    fun setVolume(value: Float) {
        _volume.value = value.coerceIn(0.0f, 1.0f)
    }

    fun adjustVolume(delta: Float) {
        _volume.update { (it + delta).coerceIn(0.0f, 1.0f) }
    }

    fun toggleWifi() { _isWifiOn.update { !it } }
    fun toggleBluetooth() { _isBluetoothOn.update { !it } }
    fun toggleCellular() { _isCellularOn.update { !it } }
    fun toggleAirplaneMode() {
        _isAirplaneMode.update { !it }
        if (_isAirplaneMode.value) {
            _isWifiOn.value = false
            _isCellularOn.value = false
        }
    }
    fun toggleFlashlight() { _isFlashlightOn.update { !it } }
    fun toggleLowPowerMode() { _isLowPowerMode.update { !it } }

    fun setWallpaperIndex(index: Int) {
        _wallpaperIndex.value = index
    }

    // Music Player Logic
    fun togglePlayPauseMusic() {
        _isPlayingMusic.update { !it }
        if (_isPlayingMusic.value) {
            if (_islandMode.value == IslandMode.IDLE) {
                _islandMode.value = IslandMode.MUSIC
            }
            startMusicLoop()
        } else {
            musicJob?.cancel()
            if (_islandMode.value == IslandMode.MUSIC && !_isTimerRunning.value && !_isCallActive.value) {
                // leave or keep
            }
        }
    }

    fun nextTrack() {
        val tracks = listOf(
            Pair("Starboy", "The Weeknd ft. Daft Punk"),
            Pair("Blinding Lights", "The Weeknd"),
            Pair("Midnight City", "M83"),
            Pair("Save Your Tears", "The Weeknd")
        )
        val currentIndex = tracks.indexOfFirst { it.first == _currentSongTitle.value }
        val nextIndex = (currentIndex + 1) % tracks.size
        _currentSongTitle.value = tracks[nextIndex].first
        _currentArtist.value = tracks[nextIndex].second
        _musicProgress.value = 0.0f
    }

    fun prevTrack() {
        _musicProgress.value = 0.0f
    }

    private fun startMusicLoop() {
        musicJob?.cancel()
        musicJob = viewModelScope.launch {
            while (_isPlayingMusic.value) {
                delay(1000)
                _musicProgress.update {
                    if (it >= 1.0f) {
                        nextTrack()
                        0.0f
                    } else {
                        it + 0.015f
                    }
                }
            }
        }
    }

    // Timer Logic
    fun startTimer(durationSeconds: Int) {
        _timerSeconds.value = durationSeconds
        _isTimerRunning.value = true
        _islandMode.value = IslandMode.TIMER
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timerSeconds.value > 0 && _isTimerRunning.value) {
                delay(1000)
                _timerSeconds.update { it - 1 }
            }
            _isTimerRunning.value = false
            if (_isPlayingMusic.value) {
                _islandMode.value = IslandMode.MUSIC
            } else {
                _islandMode.value = IslandMode.IDLE
            }
        }
    }

    fun stopTimer() {
        _isTimerRunning.value = false
        timerJob?.cancel()
        _timerSeconds.value = 0
        if (_isPlayingMusic.value) {
            _islandMode.value = IslandMode.MUSIC
        } else {
            _islandMode.value = IslandMode.IDLE
        }
    }

    // Phone Call Simulation
    fun startCall(contact: String) {
        _callContactName.value = contact
        _isCallActive.value = true
        _islandMode.value = IslandMode.CALL
        var seconds = 0
        callJob?.cancel()
        callJob = viewModelScope.launch {
            while (_isCallActive.value) {
                delay(1000)
                seconds++
                val m = seconds / 60
                val s = seconds % 60
                _callDurationSec.value = String.format(Locale.getDefault(), "%02d:%02d", m, s)
            }
        }
    }

    fun endCall() {
        _isCallActive.value = false
        callJob?.cancel()
        if (_isTimerRunning.value) {
            _islandMode.value = IslandMode.TIMER
        } else if (_isPlayingMusic.value) {
            _islandMode.value = IslandMode.MUSIC
        } else {
            _islandMode.value = IslandMode.IDLE
        }
    }

    // Calculator Actions
    fun onCalculatorButton(action: String) {
        when (action) {
            "AC" -> {
                _calcDisplay.value = "0"
                calcFirstOperand = null
                calcPendingOperator = null
                calcClearOnNextDigit = false
            }
            "+/-" -> {
                val current = _calcDisplay.value.toDoubleOrNull() ?: 0.0
                val negated = -current
                _calcDisplay.value = if (negated % 1 == 0.0) negated.toLong().toString() else negated.toString()
            }
            "%" -> {
                val current = _calcDisplay.value.toDoubleOrNull() ?: 0.0
                val percent = current / 100.0
                _calcDisplay.value = percent.toString()
            }
            "+", "-", "×", "÷" -> {
                val current = _calcDisplay.value.toDoubleOrNull() ?: 0.0
                calcFirstOperand = current
                calcPendingOperator = action
                calcClearOnNextDigit = true
            }
            "=" -> {
                val first = calcFirstOperand
                val op = calcPendingOperator
                val second = _calcDisplay.value.toDoubleOrNull()
                if (first != null && op != null && second != null) {
                    val result = when (op) {
                        "+" -> first + second
                        "-" -> first - second
                        "×" -> first * second
                        "÷" -> if (second != 0.0) first / second else Double.NaN
                        else -> second
                    }
                    _calcDisplay.value = if (result.isNaN()) "Error" else if (result % 1 == 0.0) result.toLong().toString() else result.toString()
                    calcFirstOperand = null
                    calcPendingOperator = null
                    calcClearOnNextDigit = true
                }
            }
            "." -> {
                if (calcClearOnNextDigit) {
                    _calcDisplay.value = "0."
                    calcClearOnNextDigit = false
                } else if (!_calcDisplay.value.contains(".")) {
                    _calcDisplay.value += "."
                }
            }
            else -> { // Digits 0-9
                if (_calcDisplay.value == "0" || calcClearOnNextDigit) {
                    _calcDisplay.value = action
                    calcClearOnNextDigit = false
                } else if (_calcDisplay.value.length < 9) {
                    _calcDisplay.value += action
                }
            }
        }
    }

    // Notes Actions
    fun addNote(title: String, body: String) {
        val newNote = NoteItem(
            id = UUID.randomUUID().toString(),
            title = title.ifEmpty { "New Note" },
            body = body,
            date = "Just now",
            isPinned = false
        )
        _notesList.update { listOf(newNote) + it }
    }

    fun deleteNote(id: String) {
        _notesList.update { it.filterNot { note -> note.id == id } }
    }

    // Photos Actions: Add captured photo
    fun addCapturedPhoto() {
        val palettes = listOf(
            listOf(Color(0xFF4A00E0), Color(0xFF8E2DE2)),
            listOf(Color(0xFFF12711), Color(0xFFF5AF19)),
            listOf(Color(0xFF00B4DB), Color(0xFF0083B0)),
            listOf(Color(0xFF11998E), Color(0xFF38EF7D))
        )
        val randomColors = palettes.random()
        val newPhoto = PhotoItem(
            id = UUID.randomUUID().toString(),
            title = "Shot on iPhone 18 Pro Max",
            date = "Just now",
            gradientColors = randomColors,
            isFavorite = true
        )
        _photosList.update { listOf(newPhoto) + it }
    }

    // Messages Actions
    fun sendMessage(conversationId: String, text: String) {
        if (text.isBlank()) return
        val newMsg = MessageItem(
            id = UUID.randomUUID().toString(),
            sender = "Me",
            text = text,
            time = _currentTime.value,
            isFromMe = true
        )
        _conversations.update { list ->
            list.map { conv ->
                if (conv.id == conversationId) {
                    conv.copy(
                        lastMessage = text,
                        time = _currentTime.value,
                        messages = conv.messages + newMsg
                    )
                } else conv
            }
        }

        // Automatic simulated reply after 1.5 seconds
        viewModelScope.launch {
            delay(1500)
            val replyText = when {
                text.contains("hello", ignoreCase = true) || text.contains("hey", ignoreCase = true) -> "Hey! How do you like the iOS 18 fluid animations?"
                text.contains("camera", ignoreCase = true) -> "The 200MP fusion sensor captures insane dynamic range!"
                text.contains("island", ignoreCase = true) -> "Tap the Dynamic Island up top to expand music or active calls!"
                else -> "Got it! Loving the iPhone 18 Pro Max titanium experience."
            }
            val replyMsg = MessageItem(
                id = UUID.randomUUID().toString(),
                sender = _conversations.value.find { it.id == conversationId }?.sender ?: "Contact",
                text = replyText,
                time = _currentTime.value,
                isFromMe = false
            )
            _conversations.update { list ->
                list.map { conv ->
                    if (conv.id == conversationId) {
                        conv.copy(
                            lastMessage = replyText,
                            time = _currentTime.value,
                            messages = conv.messages + replyMsg
                        )
                    } else conv
                }
            }
        }
    }
}
