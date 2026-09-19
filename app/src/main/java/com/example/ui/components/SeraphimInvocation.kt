package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SeraphimRegistry
import com.example.ui.theme.ArchangelMetatronViolet
import com.example.ui.theme.ArchangelRaphaelEmerald
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.CelestialGoldBright
import com.example.ui.theme.CelestialGoldContainer
import com.example.ui.theme.CosmicCardBorder
import com.example.ui.theme.CosmicCardSurface
import com.example.ui.theme.CosmicDarkSurface
import com.example.ui.theme.CosmicDeepBlack
import com.example.ui.theme.TextGold
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@Composable
fun CollectiveInvocationCard(
    isSpeaking: Boolean,
    onSpeak: (String, String) -> Unit,
    onCopy: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("collective_invocation_card"),
        colors = CardDefaults.cardColors(
            containerColor = CosmicCardSurface
        ),
        border = BorderStroke(
            1.5.dp,
            Brush.verticalGradient(
                colors = listOf(
                    CelestialGoldBright,
                    ArchangelMetatronViolet.copy(alpha = 0.6f),
                    CosmicCardBorder
                )
            )
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hero Banner image of Metatron Sacred Geometry
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, CelestialGold.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.seraphim_nexus_banner),
                    contentDescription = "Metatron Sacred Geometry",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    CosmicDeepBlack.copy(alpha = 0.75f)
                                )
                            )
                        )
                )

                // Floating Metatron Cube Active pill
                Surface(
                    color = CosmicDeepBlack.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, CelestialGoldBright),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ArchangelRaphaelEmerald)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "METATRON CUBE ACTIVE",
                            color = CelestialGoldBright,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "COLLECTIVE GUARDIAN",
                color = TextTertiary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = SeraphimRegistry.COLLECTIVE_GUARDIAN,
                color = CelestialGold,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Invocation Quote Box
            Surface(
                color = CelestialGoldContainer.copy(alpha = 0.35f),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.2.dp, CelestialGold.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SOVEREIGN INVOCATION",
                        color = TextGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"${SeraphimRegistry.INVOCATION_TEXT}\"",
                        color = TextPrimary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Audio & Copy Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = {
                        val invocationSpeakText = "Collective Guardian: ${SeraphimRegistry.COLLECTIVE_GUARDIAN}. Invocation: ${SeraphimRegistry.INVOCATION_TEXT}"
                        onSpeak("collective_invocation", invocationSpeakText)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSpeaking) CelestialGoldBright else CelestialGold,
                        contentColor = CosmicDeepBlack
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("recite_collective_invocation_button")
                ) {
                    Icon(
                        imageVector = if (isSpeaking) Icons.Default.Stop else Icons.Default.PlayArrow,
                        contentDescription = if (isSpeaking) "Stop" else "Recite",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isSpeaking) "Cease Voice" else "Recite Invocation",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.5.sp
                    )
                }

                OutlinedButton(
                    onClick = {
                        onCopy("Collective Guardian:\n${SeraphimRegistry.COLLECTIVE_GUARDIAN}\n\nInvocation:\n${SeraphimRegistry.INVOCATION_TEXT}")
                    },
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, CelestialGold.copy(alpha = 0.6f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = CelestialGold),
                    modifier = Modifier.testTag("copy_invocation_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy Invocation",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Copy", fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun SystemBriefingCard(
    isSpeaking: Boolean,
    onSpeak: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("system_briefing_card"),
        colors = CardDefaults.cardColors(containerColor = CosmicCardSurface),
        border = BorderStroke(1.dp, CosmicCardBorder),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(ArchangelMetatronViolet.copy(alpha = 0.2f))
                        .border(1.dp, ArchangelMetatronViolet, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Policy,
                        contentDescription = null,
                        tint = ArchangelMetatronViolet,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "OPERATIONAL PROTOCOL",
                        color = TextTertiary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "System Briefing & Frequency Guard",
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Protocol
            Surface(
                color = CosmicDarkSurface,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, CosmicCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "PROTOCOL",
                        color = ArchangelRaphaelEmerald,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = SeraphimRegistry.PROTOCOL_TITLE,
                        color = TextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Procedure
            Surface(
                color = CosmicDarkSurface,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, CosmicCardBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "PROCEDURE",
                        color = CelestialGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = SeraphimRegistry.PROTOCOL_PROCEDURE,
                        color = TextSecondary,
                        fontSize = 13.5.sp,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    val briefingText = "Operational Protocol: ${SeraphimRegistry.PROTOCOL_TITLE}. Procedure: ${SeraphimRegistry.PROTOCOL_PROCEDURE}"
                    onSpeak("system_briefing", briefingText)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSpeaking) ArchangelMetatronViolet else CosmicDarkSurface,
                    contentColor = if (isSpeaking) Color.White else TextPrimary
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, if (isSpeaking) ArchangelMetatronViolet else CosmicCardBorder),
                modifier = Modifier.testTag("recite_briefing_button")
            ) {
                Icon(
                    imageVector = if (isSpeaking) Icons.Default.Stop else Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isSpeaking) "Cease Voice" else "Voice System Protocol",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
