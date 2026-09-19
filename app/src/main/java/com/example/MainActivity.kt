package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.SeraphimNexusScreen
import com.example.ui.SeraphimNexusViewModel
import com.example.ui.theme.SeraphimNexusTheme

class MainActivity : ComponentActivity() {
  private val viewModel: SeraphimNexusViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val uiState = viewModel.uiState.collectAsState()
      SeraphimNexusTheme(themeMode = uiState.value.celestialThemeMode) {
        SeraphimNexusScreen(
          viewModel = viewModel,
          modifier = Modifier.fillMaxSize()
        )
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Seraphim Nexus: $name", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  SeraphimNexusTheme { Greeting("Matrix Active") }
}

