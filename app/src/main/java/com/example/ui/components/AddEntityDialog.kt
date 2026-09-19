package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CosmicCardBorder
import com.example.ui.theme.CosmicCardSurface
import com.example.ui.theme.CosmicDarkSurface
import com.example.ui.theme.CosmicDeepBlack
import com.example.ui.theme.TextGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AddEntityDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, sign: String, role: String, reading: String, guardian: String, prayer: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var sign by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var reading by remember { mutableStateOf("") }
    var guardian by remember { mutableStateOf("") }
    var prayer by remember { mutableStateOf("") }

    val isValid = name.isNotBlank() && sign.isNotBlank() && reading.isNotBlank() && prayer.isNotBlank()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("add_entity_dialog"),
            colors = CardDefaults.cardColors(containerColor = CosmicCardSurface),
            border = BorderStroke(1.2.dp, CelestialGold.copy(alpha = 0.5f)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Register Entity into Nexus",
                    color = CelestialGold,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Add an entity to the permanent pre-registered matrix registry.",
                    color = TextSecondary,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                val tfColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CelestialGold,
                    unfocusedBorderColor = CosmicCardBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedLabelColor = TextGold,
                    unfocusedLabelColor = TextSecondary,
                    cursorColor = CelestialGold,
                    focusedContainerColor = CosmicDarkSurface,
                    unfocusedContainerColor = CosmicDarkSurface
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Entity Full Name & Title *") },
                    placeholder = { Text("e.g. Kagiso (The Sovereign Strategist)") },
                    modifier = Modifier.fillMaxWidth().testTag("add_name_field"),
                    colors = tfColors,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = sign,
                    onValueChange = { sign = it },
                    label = { Text("Zodiac / Matrix Sign *") },
                    placeholder = { Text("e.g. Scorpio (Nov 12)") },
                    modifier = Modifier.fillMaxWidth().testTag("add_sign_field"),
                    colors = tfColors,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Role in Collective") },
                    placeholder = { Text("e.g. Master Strategist / Builder") },
                    modifier = Modifier.fillMaxWidth().testTag("add_role_field"),
                    colors = tfColors,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = guardian,
                    onValueChange = { guardian = it },
                    label = { Text("Angelic Guardian") },
                    placeholder = { Text("e.g. Archangel Michael / Uriel") },
                    modifier = Modifier.fillMaxWidth().testTag("add_guardian_field"),
                    colors = tfColors,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = reading,
                    onValueChange = { reading = it },
                    label = { Text("Solar Matrix Reading *") },
                    placeholder = { Text("Cosmic currents and strategic focus guidance...") },
                    modifier = Modifier.fillMaxWidth().height(100.dp).testTag("add_reading_field"),
                    colors = tfColors,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = prayer,
                    onValueChange = { prayer = it },
                    label = { Text("Catered Protection Prayer *") },
                    placeholder = { Text("Divine Source, fortify and shield...") },
                    modifier = Modifier.fillMaxWidth().height(100.dp).testTag("add_prayer_field"),
                    colors = tfColors,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, CosmicCardBorder),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary)
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            if (isValid) {
                                onConfirm(
                                    name,
                                    sign,
                                    role.ifBlank { "Sovereign Node" },
                                    reading,
                                    guardian.ifBlank { "Guardian Angels of Light" },
                                    prayer
                                )
                            }
                        },
                        enabled = isValid,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CelestialGold,
                            contentColor = CosmicDeepBlack,
                            disabledContainerColor = CosmicCardBorder,
                            disabledContentColor = TextSecondary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("confirm_add_entity_button")
                    ) {
                        Text("Register", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
