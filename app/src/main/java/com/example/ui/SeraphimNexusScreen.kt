package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.NexusEntity
import com.example.ui.components.AddEntityDialog
import com.example.ui.components.CollectiveInvocationCard
import com.example.ui.components.EntityReadingCard
import com.example.ui.components.ExternalEntityCard
import com.example.ui.components.SystemBriefingCard
import com.example.ui.theme.ArchangelMetatronViolet
import com.example.ui.theme.ArchangelRaphaelEmerald
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CelestialGoldBright
import com.example.ui.theme.CelestialGoldContainer
import com.example.ui.theme.CelestialGoldGlow
import com.example.ui.theme.CelestialThemeMode
import com.example.ui.theme.CosmicCardBorder
import com.example.ui.theme.CosmicCardSurface
import com.example.ui.theme.CosmicDarkSurface
import com.example.ui.theme.CosmicDeepBlack
import com.example.ui.theme.EclipseAbyssBlack
import com.example.ui.theme.EclipseCardBorder
import com.example.ui.theme.EclipseCardSurface
import com.example.ui.theme.EclipseSurfaceDark
import com.example.ui.theme.TextGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SeraphimNexusScreen(
    viewModel: SeraphimNexusViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    val copyToClipboard: (String) -> Unit = { text ->
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Seraphim Nexus", text)
        clipboard.setPrimaryClip(clip)
        viewModel.showMessage("Copied to clipboard")
    }

    if (uiState.showAddDialog) {
        AddEntityDialog(
            onDismiss = { viewModel.showAddEntityDialog(false) },
            onConfirm = { name, sign, role, reading, guardian, prayer ->
                viewModel.addCustomEntity(name, sign, role, reading, guardian, prayer)
                viewModel.showMessage("Entity registered in Nexus Matrix")
            }
        )
    }

    val isHighContrast = uiState.celestialThemeMode == CelestialThemeMode.HIGH_CONTRAST
    val currentBgColor = MaterialTheme.colorScheme.background
    val currentSurfaceColor = MaterialTheme.colorScheme.surface
    val currentOutlineColor = MaterialTheme.colorScheme.outline
    val currentCardSurface = MaterialTheme.colorScheme.surfaceVariant

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(currentBgColor),
        containerColor = currentBgColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = currentSurfaceColor,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .border(
                        BorderStroke(if (isHighContrast) 1.dp else 0.5.dp, currentOutlineColor),
                        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .testTag("nexus_bottom_navigation")
            ) {
                val navItems = listOf(
                    Triple(NexusTab.PRESENCE, "Presence", Icons.Default.Groups),
                    Triple(NexusTab.READINGS, "Solar Matrix", Icons.Default.AutoAwesome),
                    Triple(NexusTab.INVOCATION, "Invocation", Icons.Default.Shield),
                    Triple(NexusTab.PROTOCOLS, "Briefing", Icons.Default.Policy)
                )

                navItems.forEach { (tab, label, icon) ->
                    val selected = uiState.activeTab == tab
                    NavigationBarItem(
                        selected = selected,
                        onClick = { viewModel.setTab(tab) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = CelestialGoldBright,
                            indicatorColor = CelestialGoldBright,
                            unselectedIconColor = TextTertiary,
                            unselectedTextColor = TextTertiary
                        ),
                        modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Cosmic App Header
            Surface(
                color = currentSurfaceColor,
                border = BorderStroke(if (isHighContrast) 1.dp else 0.5.dp, currentOutlineColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "SERAPHIM NEXUS",
                                color = CelestialGoldBright,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.2.sp
                            )
                        }
                        Text(
                            text = if (isHighContrast) "OLED Eclipse • High Contrast Mode" else "Deep Cosmic • Indigo Starlight",
                            color = if (isHighContrast) CelestialGoldGlow else TextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // High-Contrast Celestial Theme Mode Toggle Pill
                        Surface(
                            color = if (isHighContrast) CelestialGoldContainer else CosmicDeepBlack,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(
                                1.2.dp,
                                if (isHighContrast) CelestialGoldBright else CosmicCardBorder
                            ),
                            modifier = Modifier
                                .clickable { viewModel.toggleThemeMode() }
                                .testTag("toggle_celestial_dark_mode")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isHighContrast) Icons.Default.Contrast else Icons.Default.Brightness4,
                                    contentDescription = "Toggle Theme Contrast",
                                    tint = if (isHighContrast) CelestialGoldBright else TextTertiary,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isHighContrast) "HIGH-CONTRAST" else "COSMIC DARK",
                                    color = if (isHighContrast) CelestialGoldBright else TextSecondary,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }

                        // Metatron Status Indicator Pill
                        Surface(
                            color = currentBgColor,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, CelestialGold.copy(alpha = 0.6f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(ArchangelRaphaelEmerald)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "METATRON",
                                    color = CelestialGold,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Main Content Area based on Active Tab
            when (uiState.activeTab) {
                NexusTab.PRESENCE -> {
                    PresenceEngineView(
                        uiState = uiState,
                        onToggleEntity = { viewModel.toggleEntitySelection(it) },
                        onSelectAll = { viewModel.selectAll() },
                        onClearAll = { viewModel.clearAll() },
                        onManualInputChange = { viewModel.updateManualInput(it) },
                        onEngage = { viewModel.engageMatrix() },
                        onOpenAddDialog = { viewModel.showAddEntityDialog(true) }
                    )
                }

                NexusTab.READINGS -> {
                    SolarMatrixReadingsView(
                        uiState = uiState,
                        onSpeak = { id, text -> viewModel.speak(id, text) },
                        onCopy = copyToClipboard,
                        onNavigateToPresence = { viewModel.setTab(NexusTab.PRESENCE) }
                    )
                }

                NexusTab.INVOCATION -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            CollectiveInvocationCard(
                                isSpeaking = uiState.currentlySpeakingId == "collective_invocation",
                                onSpeak = { id, text -> viewModel.speak(id, text) },
                                onCopy = copyToClipboard
                            )
                        }
                    }
                }

                NexusTab.PROTOCOLS -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            SystemBriefingCard(
                                isSpeaking = uiState.currentlySpeakingId == "system_briefing",
                                onSpeak = { id, text -> viewModel.speak(id, text) }
                            )
                        }

                        item {
                            // Extra Operational Insights Card
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = CosmicCardSurface),
                                border = BorderStroke(1.dp, CosmicCardBorder),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(18.dp)) {
                                    Text(
                                        text = "ARCHITECTURAL MATRIX GUIDELINES",
                                        color = TextGold,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "• Physical Output & Code Architecture: Prioritize foundation building, eliminate deceptive urgency, concentrate strictly on what is within direct control.",
                                        color = TextPrimary,
                                        fontSize = 13.sp,
                                        lineHeight = 19.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "• The Balanced Radar: Maintain high intuitive scanning in every conversation, honor unspoken red flags, and navigate with divine diplomacy.",
                                        color = TextPrimary,
                                        fontSize = 13.sp,
                                        lineHeight = 19.sp
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "• Grounding Anchor: Remain an anchor for the collective, never an energetic battery for others to drain.",
                                        color = TextPrimary,
                                        fontSize = 13.sp,
                                        lineHeight = 19.sp
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PresenceEngineView(
    uiState: NexusUiState,
    onToggleEntity: (String) -> Unit,
    onSelectAll: () -> Unit,
    onClearAll: () -> Unit,
    onManualInputChange: (String) -> Unit,
    onEngage: () -> Unit,
    onOpenAddDialog: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Information Box
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("presence_info_card"),
                colors = CardDefaults.cardColors(containerColor = CosmicCardSurface),
                border = BorderStroke(1.dp, CosmicCardBorder),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Groups,
                            contentDescription = null,
                            tint = CelestialGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "PRE-REGISTERED DATA REGISTRY",
                            color = TextGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toggle present entities or type comma-separated names to engage the adaptive solar horoscope matrix and Archangel alignments.",
                        color = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )
                }
            }
        }

        // Quick Entity Toggle Chips Section
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pre-Registered Entities (${uiState.selectedEntityIds.size}/${uiState.registeredEntities.size} Present)",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        IconButton(
                            onClick = onSelectAll,
                            modifier = Modifier.size(36.dp).testTag("select_all_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.SelectAll,
                                contentDescription = "Select All",
                                tint = CelestialGold,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = onClearAll,
                            modifier = Modifier.size(36.dp).testTag("clear_all_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear All",
                                tint = TextTertiary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.registeredEntities.forEach { entity ->
                        val isSelected = uiState.selectedEntityIds.contains(entity.id)
                        val accentColor = Color(entity.accentColor)

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) CosmicDarkSurface else CosmicDeepBlack,
                            border = BorderStroke(
                                if (isSelected) 1.5.dp else 1.dp,
                                if (isSelected) accentColor else CosmicCardBorder
                            ),
                            modifier = Modifier
                                .clickable { onToggleEntity(entity.id) }
                                .testTag("chip_entity_${entity.id}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) accentColor else CosmicCardBorder),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    } else {
                                        Text(
                                            text = entity.id.take(1).uppercase(),
                                            color = TextSecondary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Column {
                                    Text(
                                        text = entity.id.replaceFirstChar { it.titlecase() },
                                        color = if (isSelected) TextPrimary else TextSecondary,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                    Text(
                                        text = entity.role,
                                        color = if (isSelected) accentColor else TextTertiary,
                                        fontSize = 10.5.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Manual / External Presence Input (mirroring python: input("\nEnter who is present today: "))
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CosmicCardSurface),
                border = BorderStroke(1.dp, CosmicCardBorder),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "MANUAL / EXTERNAL PRESENCE INPUT",
                        color = TextGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Enter names (comma-separated). Unrecognized names will receive baseline grounded protection.",
                        color = TextSecondary,
                        fontSize = 12.5.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = uiState.manualInputText,
                        onValueChange = onManualInputChange,
                        placeholder = { Text("e.g. mlungisi, sizwe, Guest Orion...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("manual_presence_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CelestialGold,
                            unfocusedBorderColor = CosmicCardBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            cursorColor = CelestialGold,
                            focusedContainerColor = CosmicDarkSurface,
                            unfocusedContainerColor = CosmicDarkSurface
                        ),
                        singleLine = true
                    )
                }
            }
        }

        // Action Buttons: Engage Matrix & Add Entity
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onEngage,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CelestialGold,
                        contentColor = CosmicDeepBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("engage_nexus_matrix_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ENGAGE NEXUS MATRIX",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }

                OutlinedButton(
                    onClick = onOpenAddDialog,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CelestialGold),
                    border = BorderStroke(1.dp, CelestialGold.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("register_new_entity_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Register Custom Entity",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun SolarMatrixReadingsView(
    uiState: NexusUiState,
    onSpeak: (String, String) -> Unit,
    onCopy: (String) -> Unit,
    onNavigateToPresence: () -> Unit
) {
    val result = uiState.presenceResult
    val recognized = result?.recognizedEntities.orEmpty()
    val external = result?.externalEntities.orEmpty()

    if (recognized.isEmpty() && external.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CosmicCardSurface),
                border = BorderStroke(1.dp, CosmicCardBorder),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Groups,
                        contentDescription = null,
                        tint = CelestialGold,
                        modifier = Modifier.size(44.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "System On Standby",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "No entities are currently marked present in the Matrix. Select registered entities or type names to activate their solar readings.",
                        color = TextSecondary,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 19.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onNavigateToPresence,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CelestialGold,
                            contentColor = CosmicDeepBlack
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Open Presence Engine", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Recognized Entities Header
            if (recognized.isNotEmpty()) {
                item {
                    Text(
                        text = "INDIVIDUAL SOLAR MATRIX READINGS (${recognized.size})",
                        color = CelestialGold,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }

                items(recognized, key = { it.id }) { entity ->
                    EntityReadingCard(
                        entity = entity,
                        isSpeaking = uiState.currentlySpeakingId == entity.id,
                        onSpeak = onSpeak,
                        onCopy = onCopy
                    )
                }
            }

            // External / Unrecognized Entities Section
            if (external.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "NEW / EXTERNAL ENTITIES DETECTED (${external.size})",
                        color = Color(0xFF94A3B8),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }

                items(external, key = { it.name }) { externalEntity ->
                    ExternalEntityCard(external = externalEntity)
                }
            }
        }
    }
}
