package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.SeraphimRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Seraphim Nexus", appName)
  }

  @Test
  fun `verify pre-registered entities presence and matching`() {
    val entities = SeraphimRegistry.DEFAULT_ENTITIES
    assertEquals(6, entities.size)

    val mlungisi = SeraphimRegistry.matchEntity("mlungisi", entities)
    assertNotNull(mlungisi)
    assertEquals("Taurus (May 13)", mlungisi?.sign)
    assertTrue(mlungisi?.guardian?.contains("Archangel Michael") == true)

    val sizwe = SeraphimRegistry.matchEntity("sizwe", entities)
    assertNotNull(sizwe)
    assertEquals("Libra", sizwe?.sign)
    assertTrue(sizwe?.guardian?.contains("Archangel Uriel") == true)

    val bandile = SeraphimRegistry.matchEntity("bandile", entities)
    assertNotNull(bandile)
    assertEquals("The Harmonizer", bandile?.role)

    // Test comma-separated presence processing including unrecognized entities
    val result = SeraphimRegistry.processPresence(
        rawInput = "mlungisi, Guest Orion",
        selectedKeys = setOf("sizwe"),
        registeredEntities = entities
    )

    assertEquals(2, result.recognizedEntities.size)
    assertEquals(1, result.externalEntities.size)
    assertEquals("Guest Orion", result.externalEntities.first().name)
  }
}

