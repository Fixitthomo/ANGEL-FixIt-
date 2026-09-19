package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.data.SeraphimRegistry
import com.example.model.NexusEntity
import com.example.model.PresenceEngineResult
import com.example.tts.NexusSpeaker
import com.example.ui.theme.CelestialThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class NexusTab(val title: String) {
    PRESENCE("Presence Engine"),
    READINGS("Solar Matrix"),
    INVOCATION("Metatron Invocation"),
    PROTOCOLS("System Briefing")
}

data class NexusUiState(
    val registeredEntities: List<NexusEntity> = SeraphimRegistry.DEFAULT_ENTITIES,
    val selectedEntityIds: Set<String> = setOf("mlungisi", "sizwe", "bandile", "thabang", "abigail", "baby brown"),
    val manualInputText: String = "",
    val activeTab: NexusTab = NexusTab.PRESENCE,
    val presenceResult: PresenceEngineResult? = null,
    val isEngineEngaged: Boolean = true,
    val isSpeaking: Boolean = false,
    val currentlySpeakingId: String? = null,
    val showAddDialog: Boolean = false,
    val snackbarMessage: String? = null,
    val celestialThemeMode: CelestialThemeMode = CelestialThemeMode.HIGH_CONTRAST
)

class SeraphimNexusViewModel(application: Application) : AndroidViewModel(application) {

    private val speaker = NexusSpeaker(application)

    private val _uiState = MutableStateFlow(NexusUiState())
    val uiState: StateFlow<NexusUiState> = _uiState.asStateFlow()

    init {
        // Run initial engine with full pre-registered presence
        runEngine()

        // Observe TTS state
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).run {
            kotlinx.coroutines.GlobalScope.run {
                // speaker states
            }
        }
    }

    fun toggleEntitySelection(entityId: String) {
        _uiState.update { current ->
            val updatedSelection = if (current.selectedEntityIds.contains(entityId)) {
                current.selectedEntityIds - entityId
            } else {
                current.selectedEntityIds + entityId
            }
            current.copy(selectedEntityIds = updatedSelection)
        }
        runEngine()
    }

    fun selectAll() {
        _uiState.update { current ->
            current.copy(selectedEntityIds = current.registeredEntities.map { it.id }.toSet())
        }
        runEngine()
    }

    fun clearAll() {
        _uiState.update { current ->
            current.copy(selectedEntityIds = emptySet(), manualInputText = "")
        }
        runEngine()
    }

    fun updateManualInput(input: String) {
        _uiState.update { it.copy(manualInputText = input) }
    }

    fun engageMatrix() {
        runEngine()
        _uiState.update { it.copy(isEngineEngaged = true, activeTab = NexusTab.READINGS) }
    }

    private fun runEngine() {
        val current = _uiState.value
        val result = SeraphimRegistry.processPresence(
            rawInput = current.manualInputText,
            selectedKeys = current.selectedEntityIds,
            registeredEntities = current.registeredEntities
        )
        _uiState.update { it.copy(presenceResult = result) }
    }

    fun setTab(tab: NexusTab) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    fun speak(id: String, text: String) {
        speaker.speak(id, text)
        _uiState.update {
            it.copy(
                isSpeaking = speaker.isSpeaking.value,
                currentlySpeakingId = speaker.currentlySpeakingId.value
            )
        }
    }

    fun stopSpeaking() {
        speaker.stop()
        _uiState.update {
            it.copy(
                isSpeaking = false,
                currentlySpeakingId = null
            )
        }
    }

    fun showAddEntityDialog(show: Boolean) {
        _uiState.update { it.copy(showAddDialog = show) }
    }

    fun addCustomEntity(
        name: String,
        sign: String,
        role: String,
        reading: String,
        guardian: String,
        prayer: String
    ) {
        val cleanId = name.trim().lowercase().filter { it.isLetterOrDigit() }
        val newEntity = NexusEntity(
            id = cleanId.ifEmpty { "custom_${System.currentTimeMillis()}" },
            name = name.trim(),
            sign = sign.trim(),
            role = role.trim(),
            reading = reading.trim(),
            guardian = guardian.trim(),
            prayer = prayer.trim(),
            accentColor = 0xFFA855F7,
            isCustom = true
        )

        _uiState.update { current ->
            val updatedList = current.registeredEntities + newEntity
            val updatedSelection = current.selectedEntityIds + newEntity.id
            current.copy(
                registeredEntities = updatedList,
                selectedEntityIds = updatedSelection,
                showAddDialog = false
            )
        }
        runEngine()
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun showMessage(message: String) {
        _uiState.update { it.copy(snackbarMessage = message) }
    }

    fun setThemeMode(mode: CelestialThemeMode) {
        _uiState.update { it.copy(celestialThemeMode = mode) }
    }

    fun toggleThemeMode() {
        _uiState.update { current ->
            val nextMode = if (current.celestialThemeMode == CelestialThemeMode.HIGH_CONTRAST) {
                CelestialThemeMode.COSMIC_MIDNIGHT
            } else {
                CelestialThemeMode.HIGH_CONTRAST
            }
            current.copy(celestialThemeMode = nextMode)
        }
    }

    override fun onCleared() {
        super.onCleared()
        speaker.shutdown()
    }
}
