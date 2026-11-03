package com.example.smart_volt.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.smart_volt.ui.theme.SmartVoltTheme
import com.example.smart_volt.ui.theme.VoltError
import com.example.smart_volt.ui.theme.VoltGray
import com.example.smart_volt.ui.theme.VoltGrayLight
import com.example.smart_volt.ui.theme.VoltNavy
import com.example.smart_volt.ui.theme.VoltNavyLight
import com.example.smart_volt.ui.theme.VoltSurface
import com.example.smart_volt.ui.theme.VoltWhite
import com.example.smart_volt.ui.theme.VoltYellow

// ─────────────────────────────────────────────────────────────────────────────
// Pantalla: Preparar dispositivo
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun DeviceSetupScreen(
    onSetupComplete: () -> Unit = {}
) {
    val context = LocalContext.current

    // ── Estado de permisos ────────────────────────────────────────────────────

    fun isLocationGranted() =
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    fun isBluetoothGranted() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH
            ) == PackageManager.PERMISSION_GRANTED
        }

    fun isNotificationsGranted() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true

    var locationGranted    by remember { mutableStateOf(isLocationGranted()) }
    var bluetoothGranted   by remember { mutableStateOf(isBluetoothGranted()) }
    var notifGranted       by remember { mutableStateOf(isNotificationsGranted()) }

    // Estados de error por campo (solo se muestran al pulsar Continuar)
    var locationError  by remember { mutableStateOf(false) }
    var bluetoothError by remember { mutableStateOf(false) }

    // ── Launchers de permisos ─────────────────────────────────────────────────

    val locationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        locationGranted = granted
        if (granted) locationError = false
    }

    val bluetoothLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        bluetoothGranted = perms.values.all { it }
        if (bluetoothGranted) bluetoothError = false
    }

    val notifLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> notifGranted = granted }

    // ── UI ───────────────────────────────────────────────────────────────────

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(VoltNavy, VoltSurface))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(56.dp))

            // ── Título ────────────────────────────────────────────────────────
            Text(
                text = "Preparar dispositivo",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = VoltWhite,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Necesitamos estos permisos para registrar\ntu asistencia en la faena.",
                fontSize = 14.sp,
                color = VoltGrayLight,
                textAlign = TextAlign.Center,
                lineHeight = 21.sp
            )

            Spacer(Modifier.height(36.dp))

            // ── Tarjeta 1: Ubicación (OBLIGATORIO) ────────────────────────────
            PermissionCard(
                icon            = Icons.Filled.LocationOn,
                iconBackground  = Color(0xFF1B3A2B),
                iconTint        = Color(0xFF4CAF50),
                label           = "Ubicación",
                subtitle        = "Ubicación durante el uso",
                description     = "Compara tu ubicación con el perímetro al marcar.",
                isRequired      = true,
                isGranted       = locationGranted,
                hasError        = locationError,
                errorMessage    = "Activa el permiso de ubicación para continuar.",
                onClick = {
                    locationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            )

            Spacer(Modifier.height(14.dp))

            // ── Tarjeta 2: Bluetooth (OBLIGATORIO) ────────────────────────────
            PermissionCard(
                icon            = Icons.Filled.Bluetooth,
                iconBackground  = Color(0xFF0D2A40),
                iconTint        = Color(0xFF29B6F6),
                label           = "Bluetooth",
                subtitle        = "Dispositivos cercanos",
                description     = "Bluetooth detecta la baliza autorizada del frente de trabajo.",
                isRequired      = true,
                isGranted       = bluetoothGranted,
                hasError        = bluetoothError,
                errorMessage    = "Activa el permiso de Bluetooth para continuar.",
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        bluetoothLauncher.launch(
                            arrayOf(
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.BLUETOOTH_CONNECT
                            )
                        )
                    } else {
                        bluetoothLauncher.launch(
                            arrayOf(Manifest.permission.BLUETOOTH)
                        )
                    }
                }
            )

            Spacer(Modifier.height(14.dp))

            // ── Tarjeta 3: Notificaciones (OPCIONAL) ─────────────────────────
            PermissionCard(
                icon            = Icons.Filled.Notifications,
                iconBackground  = Color(0xFF2D1F00),
                iconTint        = VoltYellow,
                label           = "Notificaciones",
                subtitle        = "Notificaciones",
                description     = "Avisos locales sobre marcas pendientes de envío. Puedes cambiarlos en ajustes.",
                isRequired      = false,
                isGranted       = notifGranted,
                hasError        = false,
                errorMessage    = "",
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        notifLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            )

            Spacer(Modifier.height(36.dp))

            // ── Botón Continuar ───────────────────────────────────────────────
            Button(
                onClick = {
                    locationError  = !locationGranted
                    bluetoothError = !bluetoothGranted

                    if (locationGranted && bluetoothGranted) {
                        onSetupComplete()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VoltYellow,
                    contentColor   = VoltNavy
                )
            ) {
                Text(
                    text = "Continuar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Composable reutilizable: PermissionCard
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PermissionCard(
    icon           : ImageVector,
    iconBackground : Color,
    iconTint       : Color,
    label          : String,
    subtitle       : String,
    description    : String,
    isRequired     : Boolean,
    isGranted      : Boolean,
    hasError       : Boolean,
    errorMessage   : String,
    onClick        : () -> Unit
) {
    val borderColor = when {
        hasError  -> VoltError
        isGranted -> Color(0xFF2E7D32)
        else      -> VoltNavyLight
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(VoltNavyLight)
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono del permiso
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(iconBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            // Textos
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = VoltWhite
                    )
                    // Badge OBLIGATORIO / OPCIONAL
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                if (isRequired) Color(0xFF3A2500) else Color(0xFF1A2D42)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (isRequired) "OBLIGATORIO" else "OPCIONAL",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isRequired) VoltYellow else VoltGray,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
                Spacer(Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VoltGrayLight
                )
            }

            Spacer(Modifier.width(8.dp))

            // Indicador de estado (derecha)
            if (isGranted) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Permiso concedido",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        // Descripción
        Text(
            text = description,
            fontSize = 13.sp,
            color = VoltGray,
            lineHeight = 19.sp,
            modifier = Modifier.padding(start = 62.dp)
        )

        // Error message (animado)
        AnimatedVisibility(
            visible = hasError,
            enter   = fadeIn() + expandVertically(),
            exit    = fadeOut() + shrinkVertically()
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF3B0A0A))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = VoltError,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = errorMessage,
                    fontSize = 12.sp,
                    color = VoltError,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DeviceSetupPreview() {
    SmartVoltTheme {
        DeviceSetupScreen()
    }
}
