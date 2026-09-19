package com.example.model

data class NexusEntity(
    val id: String,
    val name: String,
    val sign: String,
    val role: String,
    val reading: String,
    val guardian: String,
    val prayer: String,
    val accentColor: Long,
    val isCustom: Boolean = false
)

data class ExternalEntity(
    val name: String,
    val status: String = "Active in presence. Baseline grounded protection applied.",
    val protocol: String = "Maintain respectful communication and strict boundaries while assessing frequency alignment."
)

data class PresenceEngineResult(
    val recognizedEntities: List<NexusEntity>,
    val externalEntities: List<ExternalEntity>,
    val timestamp: Long = System.currentTimeMillis()
)
