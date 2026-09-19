package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

enum class CelestialThemeMode(val title: String, val description: String) {
    HIGH_CONTRAST("Celestial Eclipse", "Pure OLED abyss black with luminous gold & vivid celestial contrasts"),
    COSMIC_MIDNIGHT("Cosmic Deep Space", "Subtle deep cosmic indigo with radiant golden halos")
}

// Ultra High-Contrast Celestial Eclipse Dark Scheme (OLED true pitch black, vivid gold & radiant white)
val CelestialHighContrastDarkScheme =
  darkColorScheme(
    primary = CelestialGoldBright,
    onPrimary = Color.Black,
    primaryContainer = CelestialGoldContainer,
    onPrimaryContainer = CelestialGoldGlow,
    secondary = ArchangelMetatronViolet,
    onSecondary = Color.White,
    secondaryContainer = EclipseCardSurface,
    onSecondaryContainer = Color(0xFFF3E8FF),
    tertiary = ArchangelRaphaelEmerald,
    onTertiary = Color.Black,
    background = EclipseAbyssBlack,
    onBackground = Color.White,
    surface = EclipseSurfaceDark,
    onSurface = Color.White,
    surfaceVariant = EclipseCardSurface,
    onSurfaceVariant = Color(0xFFF1F5F9),
    outline = EclipseCardBorder,
    outlineVariant = EclipseBorderVibrant
  )

// Cosmic Deep Midnight Dark Scheme (Deep Starlight Indigo)
val CosmicMidnightDarkScheme =
  darkColorScheme(
    primary = CelestialGold,
    onPrimary = CosmicDeepBlack,
    primaryContainer = CelestialGoldContainer,
    onPrimaryContainer = CelestialGoldBright,
    secondary = ArchangelMetatronViolet,
    onSecondary = Color.White,
    secondaryContainer = CosmicSurfaceHighlight,
    onSecondaryContainer = Color(0xFFE9D5FF),
    tertiary = ArchangelRaphaelEmerald,
    onTertiary = CosmicDeepBlack,
    background = CosmicDeepBlack,
    onBackground = TextPrimary,
    surface = CosmicDarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = CosmicCardSurface,
    onSurfaceVariant = TextSecondary,
    outline = CosmicCardBorder,
    outlineVariant = CelestialGold.copy(alpha = 0.6f)
  )

@Composable
fun SeraphimNexusTheme(
  darkTheme: Boolean = true,
  themeMode: CelestialThemeMode = CelestialThemeMode.HIGH_CONTRAST,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme: ColorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        dynamicDarkColorScheme(context)
      }
      themeMode == CelestialThemeMode.HIGH_CONTRAST -> CelestialHighContrastDarkScheme
      else -> CosmicMidnightDarkScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

// Backward compatibility alias
@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  SeraphimNexusTheme(darkTheme = true, themeMode = CelestialThemeMode.HIGH_CONTRAST, dynamicColor = dynamicColor, content = content)
}


