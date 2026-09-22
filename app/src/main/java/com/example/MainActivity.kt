package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.AppId
import com.example.model.AppItem
import com.example.ui.apps.*
import com.example.ui.components.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.IosSystemViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: IosSystemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val activeApp by viewModel.activeApp.collectAsStateWithLifecycle()
                val isControlCenterOpen by viewModel.isControlCenterOpen.collectAsStateWithLifecycle()
                val isAppSwitcherOpen by viewModel.isAppSwitcherOpen.collectAsStateWithLifecycle()

                // Android hardware back button routes back to iOS Home screen or closes sheets
                BackHandler(enabled = isControlCenterOpen || isAppSwitcherOpen || activeApp != null) {
                    if (isControlCenterOpen) {
                        viewModel.closeControlCenter()
                    } else if (isAppSwitcherOpen) {
                        viewModel.closeAppSwitcher()
                    } else if (activeApp != null) {
                        viewModel.goHome()
                    }
                }

                IPhone18ProMaxContainer(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun IPhone18ProMaxContainer(
    viewModel: IosSystemViewModel,
    modifier: Modifier = Modifier
) {
    val activeApp by viewModel.activeApp.collectAsStateWithLifecycle()
    val isLocked by viewModel.isLocked.collectAsStateWithLifecycle()
    val isControlCenterOpen by viewModel.isControlCenterOpen.collectAsStateWithLifecycle()
    val isAppSwitcherOpen by viewModel.isAppSwitcherOpen.collectAsStateWithLifecycle()
    val showTitaniumFrame by viewModel.showTitaniumFrame.collectAsStateWithLifecycle()
    val islandMode by viewModel.islandMode.collectAsStateWithLifecycle()
    val isIslandExpanded by viewModel.isIslandExpanded.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val brightness by viewModel.brightness.collectAsStateWithLifecycle()
    val volume by viewModel.volume.collectAsStateWithLifecycle()
    val isWifiOn by viewModel.isWifiOn.collectAsStateWithLifecycle()
    val isBluetoothOn by viewModel.isBluetoothOn.collectAsStateWithLifecycle()
    val isCellularOn by viewModel.isCellularOn.collectAsStateWithLifecycle()
    val isAirplaneMode by viewModel.isAirplaneMode.collectAsStateWithLifecycle()
    val isFlashlightOn by viewModel.isFlashlightOn.collectAsStateWithLifecycle()
    val isLowPowerMode by viewModel.isLowPowerMode.collectAsStateWithLifecycle()
    val wallpaperIndex by viewModel.wallpaperIndex.collectAsStateWithLifecycle()
    val batteryLevel by viewModel.batteryLevel.collectAsStateWithLifecycle()
    val currentTime by viewModel.currentTime.collectAsStateWithLifecycle()
    val currentDate by viewModel.currentDate.collectAsStateWithLifecycle()
    val isPlayingMusic by viewModel.isPlayingMusic.collectAsStateWithLifecycle()
    val currentSongTitle by viewModel.currentSongTitle.collectAsStateWithLifecycle()
    val currentArtist by viewModel.currentArtist.collectAsStateWithLifecycle()
    val musicProgress by viewModel.musicProgress.collectAsStateWithLifecycle()
    val timerSeconds by viewModel.timerSeconds.collectAsStateWithLifecycle()
    val isTimerRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()
    val isCallActive by viewModel.isCallActive.collectAsStateWithLifecycle()
    val callContactName by viewModel.callContactName.collectAsStateWithLifecycle()
    val callDurationSec by viewModel.callDurationSec.collectAsStateWithLifecycle()
    val calcDisplay by viewModel.calcDisplay.collectAsStateWithLifecycle()
    val notesList by viewModel.notesList.collectAsStateWithLifecycle()
    val photosList by viewModel.photosList.collectAsStateWithLifecycle()
    val conversations by viewModel.conversations.collectAsStateWithLifecycle()

    IPhoneFrame(
        showFrame = showTitaniumFrame,
        onToggleFrame = { viewModel.toggleTitaniumFrame() },
        onPowerButton = {
            if (isLocked) {
                viewModel.unlockDevice()
            } else {
                viewModel.lockDevice()
            }
        },
        onVolumeUp = { viewModel.adjustVolume(0.1f) },
        onVolumeDown = { viewModel.adjustVolume(-0.1f) },
        onActionButton = { viewModel.toggleFlashlight() },
        modifier = modifier.fillMaxSize(),
        screenContent = {
            // Screen Surface
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                // Background Wallpaper with optional blur
                val wallpaperModifier = if (activeApp != null || isControlCenterOpen) {
                    Modifier.fillMaxSize().blur(24.dp)
                } else {
                    Modifier.fillMaxSize()
                }

                Box(modifier = wallpaperModifier) {
                    Image(
                        painter = painterResource(id = R.drawable.ios18_wallpaper),
                        contentDescription = "iOS 18 Wallpaper",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Main OS Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp)
                ) {
                    // Top iOS Status Bar (with swipe-down detection for Control Center)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDrag = { change, dragAmount ->
                                        if (dragAmount.y > 20f && !isLocked) {
                                            viewModel.openControlCenter()
                                        }
                                    }
                                )
                            }
                    ) {
                        IosStatusBar(
                            time = currentTime,
                            batteryLevel = batteryLevel,
                            isWifiOn = isWifiOn,
                            isCellularOn = isCellularOn,
                            isAirplaneMode = isAirplaneMode,
                            isDarkMode = isDarkMode,
                            onLeftTap = { },
                            onRightTap = { viewModel.openControlCenter() }
                        )
                    }

                    // Dynamic Island View
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp, bottom = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DynamicIsland(
                            mode = islandMode,
                            isExpanded = isIslandExpanded,
                            onToggleExpand = { viewModel.toggleIslandExpand() },
                            songTitle = currentSongTitle,
                            artist = currentArtist,
                            isPlayingMusic = isPlayingMusic,
                            musicProgress = musicProgress,
                            onTogglePlayMusic = { viewModel.togglePlayPauseMusic() },
                            onNextTrack = { viewModel.nextTrack() },
                            timerSeconds = timerSeconds,
                            onStopTimer = { viewModel.stopTimer() },
                            isCallActive = isCallActive,
                            callContact = callContactName,
                            callDuration = callDurationSec,
                            onEndCall = { viewModel.endCall() }
                        )
                    }

                    // Middle Viewport (Active App or Home Screen Springboard)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        AnimatedContent(
                            targetState = activeApp,
                            transitionSpec = {
                                if (targetState != null) {
                                    (scaleIn(initialScale = 0.88f, animationSpec = tween(260)) + fadeIn(tween(200)))
                                        .togetherWith(scaleOut(targetScale = 1.05f, animationSpec = tween(220)) + fadeOut(tween(180)))
                                } else {
                                    (scaleIn(initialScale = 1.05f, animationSpec = tween(240)) + fadeIn(tween(200)))
                                        .togetherWith(scaleOut(targetScale = 0.88f, animationSpec = tween(200)) + fadeOut(tween(160)))
                                }
                            },
                            label = "AppTransition"
                        ) { targetApp ->
                            if (targetApp != null) {
                                // Fullscreen App View
                                when (targetApp) {
                                    AppId.CAMERA -> CameraApp(
                                        latestPhoto = photosList.firstOrNull(),
                                        onCapturePhoto = { viewModel.addCapturedPhoto() },
                                        onOpenPhotos = { viewModel.openApp(AppId.PHOTOS) }
                                    )
                                    AppId.PHOTOS -> PhotosApp(
                                        photos = photosList
                                    )
                                    AppId.PHONE -> PhoneApp(
                                        isCallActive = isCallActive,
                                        callContact = callContactName,
                                        callDuration = callDurationSec,
                                        onStartCall = { contact -> viewModel.startCall(contact) },
                                        onEndCall = { viewModel.endCall() }
                                    )
                                    AppId.MESSAGES -> MessagesApp(
                                        conversations = conversations,
                                        onSendMessage = { convId, msg -> viewModel.sendMessage(convId, msg) }
                                    )
                                    AppId.SAFARI -> SafariApp()
                                    AppId.SETTINGS -> SettingsApp(
                                        isDarkMode = isDarkMode,
                                        onToggleDarkMode = { viewModel.toggleDarkMode() },
                                        isWifiOn = isWifiOn,
                                        onToggleWifi = { viewModel.toggleWifi() },
                                        isBluetoothOn = isBluetoothOn,
                                        onToggleBluetooth = { viewModel.toggleBluetooth() },
                                        isAirplaneMode = isAirplaneMode,
                                        onToggleAirplane = { viewModel.toggleAirplaneMode() },
                                        currentWallpaperIndex = wallpaperIndex,
                                        onSelectWallpaper = { index -> viewModel.setWallpaperIndex(index) }
                                    )
                                    AppId.CALCULATOR -> CalculatorApp(
                                        displayValue = calcDisplay,
                                        onButtonClick = { btn -> viewModel.onCalculatorButton(btn) }
                                    )
                                    AppId.WEATHER -> WeatherApp()
                                    AppId.CLOCK -> ClockApp(
                                        timerSeconds = timerSeconds,
                                        isTimerRunning = isTimerRunning,
                                        onStartTimer = { sec -> viewModel.startTimer(sec) },
                                        onStopTimer = { viewModel.stopTimer() }
                                    )
                                    AppId.MUSIC -> MusicApp(
                                        songTitle = currentSongTitle,
                                        artist = currentArtist,
                                        isPlaying = isPlayingMusic,
                                        progress = musicProgress,
                                        onTogglePlay = { viewModel.togglePlayPauseMusic() },
                                        onNext = { viewModel.nextTrack() },
                                        onPrev = { viewModel.prevTrack() }
                                    )
                                    AppId.NOTES -> NotesApp(
                                        notes = notesList,
                                        onAddNote = { title, body -> viewModel.addNote(title, body) },
                                        onDeleteNote = { id -> viewModel.deleteNote(id) }
                                    )
                                    AppId.HEALTH -> HealthApp()
                                    AppId.APP_STORE -> AppStoreApp()
                                    AppId.MAPS -> MapsApp()
                                    AppId.VOICE_MEMOS -> VoiceMemosApp()
                                    else -> GenericIosAppView(
                                        app = viewModel.appsList.firstOrNull { it.id == targetApp }
                                            ?: AppItem(targetApp, targetApp.name, Brush.linearGradient(listOf(Color(0xFF333333), Color(0xFF666666))))
                                    )
                                }
                            } else {
                                // Springboard + Dock Layout
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(modifier = Modifier.weight(1f)) {
                                        SpringboardView(
                                            apps = viewModel.appsList,
                                            onLaunchApp = { appId -> viewModel.openApp(appId) },
                                            batteryLevel = batteryLevel
                                        )
                                    }

                                    IosDock(
                                        unreadMessagesCount = 3,
                                        onOpenApp = { appId -> viewModel.openApp(appId) }
                                    )
                                }
                            }
                        }
                    }

                    // iOS Bottom Home Bar Gesture
                    HomeIndicator(
                        onGoHome = { viewModel.goHome() },
                        onOpenAppSwitcher = { viewModel.openAppSwitcher() },
                        isDarkMode = isDarkMode
                    )
                }

                // Lock Screen Overlay
                LockScreenView(
                    isLocked = isLocked,
                    currentTime = currentTime,
                    currentDate = currentDate,
                    batteryLevel = batteryLevel,
                    isFlashlightOn = isFlashlightOn,
                    onToggleFlashlight = { viewModel.toggleFlashlight() },
                    onUnlock = { viewModel.unlockDevice() },
                    onOpenCamera = {
                        viewModel.unlockDevice()
                        viewModel.openApp(AppId.CAMERA)
                    }
                )

                // iOS 18 Control Center Sheet Overlay
                ControlCenterSheet(
                    isOpen = isControlCenterOpen,
                    onClose = { viewModel.closeControlCenter() },
                    isWifiOn = isWifiOn,
                    onToggleWifi = { viewModel.toggleWifi() },
                    isBluetoothOn = isBluetoothOn,
                    onToggleBluetooth = { viewModel.toggleBluetooth() },
                    isCellularOn = isCellularOn,
                    onToggleCellular = { viewModel.toggleCellular() },
                    isAirplaneMode = isAirplaneMode,
                    onToggleAirplane = { viewModel.toggleAirplaneMode() },
                    isFlashlightOn = isFlashlightOn,
                    onToggleFlashlight = { viewModel.toggleFlashlight() },
                    isLowPowerMode = isLowPowerMode,
                    onToggleLowPower = { viewModel.toggleLowPowerMode() },
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = { viewModel.toggleDarkMode() },
                    brightness = brightness,
                    onBrightnessChange = { b -> viewModel.setBrightness(b) },
                    volume = volume,
                    onVolumeChange = { v -> viewModel.setVolume(v) },
                    songTitle = currentSongTitle,
                    artist = currentArtist,
                    isPlayingMusic = isPlayingMusic,
                    onTogglePlayMusic = { viewModel.togglePlayPauseMusic() },
                    onOpenApp = { appId ->
                        viewModel.closeControlCenter()
                        viewModel.openApp(appId)
                    }
                )

                // Multitasking App Switcher Sheet Overlay
                AppSwitcherSheet(
                    isOpen = isAppSwitcherOpen,
                    onClose = { viewModel.closeAppSwitcher() },
                    onSelectApp = { appId -> viewModel.openApp(appId) }
                )
            }
        }
    )
}

@Composable
private fun GenericIosAppView(app: AppItem) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(app.iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Apps,
                    contentDescription = app.name,
                    tint = app.iconColor,
                    modifier = Modifier.size(44.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = app.name, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "iOS 18 Pro Max Application", color = Color.Gray, fontSize = 14.sp)
        }
    }
}
