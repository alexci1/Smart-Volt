package com.example.smart_volt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smart_volt.ui.theme.SmartVoltTheme
import com.example.smart_volt.ui.theme.VoltGray
import com.example.smart_volt.ui.theme.VoltGrayLight
import com.example.smart_volt.ui.theme.VoltNavy
import com.example.smart_volt.ui.theme.VoltNavyLight
import com.example.smart_volt.ui.theme.VoltSurface
import com.example.smart_volt.ui.theme.VoltWhite
import com.example.smart_volt.ui.theme.VoltYellow

@Composable
fun LoginScreen(
    onLoginClick: (identifier: String, password: String) -> Unit = { _, _ -> }
) {
    var identifier by rememberSaveable { mutableStateOf("") }
    var password   by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(VoltNavy, VoltSurface)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.height(48.dp))

            // ── Logo + Marca ──────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(VoltYellow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Bolt,
                    contentDescription = "SmartVolt logo",
                    tint = VoltNavy,
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "SmartVolt",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = VoltYellow,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Ingresa con el identificador y la clave\nque te entregó la empresa.",
                fontSize = 14.sp,
                color = VoltGrayLight,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(40.dp))

            // ── Tarjeta de formulario ─────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(VoltNavyLight)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Campo: Identificador de trabajador
                OutlinedTextField(
                    value = identifier,
                    onValueChange = { identifier = it },
                    label = {
                        Text("Identificador de trabajador")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = voltTextFieldColors()
                )

                // Campo: Clave con toggle de visibilidad a la izquierda
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text("Clave")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (showPassword) VisualTransformation.None
                                          else PasswordVisualTransformation(),
                    leadingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Filled.VisibilityOff
                                              else Icons.Filled.Visibility,
                                contentDescription = if (showPassword) "Ocultar clave"
                                                     else "Mostrar clave",
                                tint = VoltGray
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = voltTextFieldColors()
                )

                Spacer(Modifier.height(4.dp))

                // Botón Iniciar Sesión
                Button(
                    onClick = { onLoginClick(identifier, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VoltYellow,
                        contentColor   = VoltNavy
                    )
                ) {
                    Text(
                        text = "Iniciar sesión",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

            // Footer
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SmartVolt © 2026",
                    fontSize = 12.sp,
                    color = VoltGray
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun voltTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor        = VoltWhite,
    unfocusedTextColor      = VoltWhite,
    focusedBorderColor      = VoltYellow,
    unfocusedBorderColor    = VoltGray,
    focusedLabelColor       = VoltYellow,
    unfocusedLabelColor     = VoltGray,
    cursorColor             = VoltYellow,
    focusedContainerColor   = Color.Transparent,
    unfocusedContainerColor = Color.Transparent
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    SmartVoltTheme {
        LoginScreen()
    }
}
