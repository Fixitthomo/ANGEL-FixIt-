package com.example.data

import com.example.model.ExternalEntity
import com.example.model.NexusEntity
import com.example.model.PresenceEngineResult

object SeraphimRegistry {

    const val PROTOCOL_TITLE = "Divine Empathy combined with Ironclad Boundaries"
    const val PROTOCOL_PROCEDURE = "When encountering background static or external friction, observe the glitch calmly. Do not match low-vibrational energy; firmly enforce your boundary and protect the circle's focus."

    const val COLLECTIVE_GUARDIAN = "Seraphim Nexus & Archangel Metatron (Metatron Cube Active)"
    const val INVOCATION_TEXT = "Grand Architect of the Universe, we align our consciousness with the Seraphim Nexus today. Command the complete nullification of every low-vibrational trap, deceptive algorithm, or energetic siphon. Grant those present the strict discipline to honor their highest potential and the empathy to move with grace. Keep their minds sharp, their physical space secure, and their daily hustle protected across all dimensions. Amen."

    val DEFAULT_ENTITIES: List<NexusEntity> = listOf(
        NexusEntity(
            id = "mlungisi",
            name = "Mlungisi (Master Fixit — The Sovereign Architect)",
            sign = "Taurus (May 13)",
            role = "The Architect",
            reading = "Cosmic currents favor physical output, code architecture, and foundational building. Put the blinders on against fake urgency and concentrate strictly on what you can build and control.",
            guardian = "Archangel Michael (Protector of Divine Order & Celestial Vanguard)",
            prayer = "Divine Creator, fortify Mlungisi with Michael’s flaming blade of truth. Shield his creative output, keep his circle tight, and grant him unwavering discipline to execute his vision without interference. Amen.",
            accentColor = 0xFFFF6B35
        ),
        NexusEntity(
            id = "sizwe",
            name = "Sizwe Clinton Msimango Ngwenya (The Balanced Radar)",
            sign = "Libra",
            role = "The Radar / Oracle",
            reading = "Intuitive channels are wide open, scanning and detecting unspoken motives in every conversation. Trust your immediate gut instincts when evaluating setups or red flags, and use your natural diplomacy to keep the collective aligned.",
            guardian = "Archangel Uriel (Illuminator of Prophetic Knowledge & Divine Light)",
            prayer = "Source of All Light, ignite Uriel’s golden flame within Sizwe’s spirit. Dispel hidden illusions, balance his scales, and grant him the clarity to speak truth with absolute authority. Amen.",
            accentColor = 0xFFFBBF24
        ),
        NexusEntity(
            id = "bandile",
            name = "Bandile (The Harmonizer — Vibe Setter)",
            sign = "Aquarius / Pisces",
            role = "The Harmonizer",
            reading = "You remain the vital grounding rod for the collective energy. When external stress spikes in the environment, your calm presence stabilizes the space. Protect your personal reserves fiercely; you are an anchor, not a battery for others to drain.",
            guardian = "Archangel Raphael (Emerald Catalyst of Healing & Energetic Restoration)",
            prayer = "Infinite Creator, encapsulate Bandile in Raphael’s protective emerald light. Shield his personal peace, repair any fractures in his field, and keep his grounded frequency untouchable. Amen.",
            accentColor = 0xFF10B981
        ),
        NexusEntity(
            id = "thabang",
            name = "Thabang Buthelezi (The Voice of Authority)",
            sign = "Leo (August 19)",
            role = "Leadership & Drive",
            reading = "Your communication channels ignite with passion, clarity, and authority as your personal drive takes center stage. Ideal timing for refining technical skills, launching creative ideas, and stepping into leadership roles.",
            guardian = "Archangel Michael / Uriel",
            prayer = "Source of All, grant Thabang the conviction to speak truth and lead with authenticity, protecting his creative spark from low-vibrational static. Amen.",
            accentColor = 0xFFEF4444
        ),
        NexusEntity(
            id = "abigail",
            name = "Abigail Nomfundo Nkabi",
            sign = "Cancer",
            role = "Logic & Nurturing",
            reading = "The lunar transit highlights emotional intelligence, quiet focus, and structured daily habits. Focus on step-by-step learning in coding and logical frameworks without feeling rushed.",
            guardian = "Archangel Gabriel",
            prayer = "Divine Source, grant Abigail absolute clarity and focus as she masters new logical frameworks and nurtures the household. Amen.",
            accentColor = 0xFF38BDF8
        ),
        NexusEntity(
            id = "baby brown",
            name = "Baby Brown (Ntokozo)",
            sign = "Infant Matrix",
            role = "Pure Frequency",
            reading = "Supports instinctual bonding, emotional safety, and peaceful surroundings. Simple, structured routines spark early curiosity and serene joy.",
            guardian = "Guardian Angels of Divine Peace",
            prayer = "Surround Baby Brown with a shield of celestial warmth, peace, and divine health. Amen.",
            accentColor = 0xFFF472B6
        )
    )

    fun matchEntity(input: String, entities: List<NexusEntity>): NexusEntity? {
        val clean = input.trim().lowercase()
        if (clean.isEmpty()) return null
        return entities.firstOrNull { entity ->
            val key = entity.id.lowercase()
            key.contains(clean) || clean.contains(key) ||
                entity.name.lowercase().contains(clean) ||
                clean.contains(entity.name.lowercase().substringBefore(" "))
        }
    }

    fun processPresence(
        rawInput: String,
        selectedKeys: Set<String>,
        registeredEntities: List<NexusEntity>
    ): PresenceEngineResult {
        val recognizedMap = mutableMapOf<String, NexusEntity>()
        val externalSet = mutableListOf<String>()

        // 1. Process selected keys directly
        selectedKeys.forEach { key ->
            registeredEntities.find { it.id == key }?.let { entity ->
                recognizedMap[entity.id] = entity
            }
        }

        // 2. Parse text input (comma-separated, just like the Python engine)
        if (rawInput.isNotBlank()) {
            val names = rawInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            for (name in names) {
                val matched = matchEntity(name, registeredEntities)
                if (matched != null) {
                    recognizedMap[matched.id] = matched
                } else {
                    val formatted = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                    if (!externalSet.contains(formatted)) {
                        externalSet.add(formatted)
                    }
                }
            }
        }

        val externalEntities = externalSet.map { ExternalEntity(name = it) }

        return PresenceEngineResult(
            recognizedEntities = recognizedMap.values.toList(),
            externalEntities = externalEntities
        )
    }
}
