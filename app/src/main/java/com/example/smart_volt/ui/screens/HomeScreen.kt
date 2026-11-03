package com.example.smart_volt.ui.screens

import android.bluetooth.BluetoothManager
import android.content.Context
import android.location.LocationManager
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smart_volt.ui.theme.SmartVoltTheme
import com.example.smart_volt.ui.theme.VoltNavy
import com.example.smart_volt.ui.theme.VoltYellow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// ─── Paleta exclusiva de esta pantalla (tema claro) ───────────────────────────
private val HomeBg         = Color(0xFFF4F7FA)
private val HomeCardWhite  = Color.White
private val HomeCardNavy   = Color(0xFF0D1B2A)
private val HomeAccentBlue = Color(0xFF1565C0)
private val HomePrimary    = Color(0xFF1C2432)
private val HomeSecondary  = Color(0xFF6B7280)
private val HomeGreen      = Color(0xFF22C55E)
private val HomeAmber      = Color(0xFFF59E0B)

// ─────────────────────────────────────────────────────────────────────────────
// Pantalla principal (pre-entrada)
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun HomeScreen(
    workerId    : String = "OP-0142",
    onMarkEntry : () -> Unit = {}
) {
    val context  = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val locale   = Locale("es", "CL")

    // Fecha larga en español
    val dateString = remember {
        SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", locale)
            .format(calendar.time)
            .replaceFirstChar { it.uppercaseChar() }
    }

    // Saludo según hora del día
    val greeting = remember {
        when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 6..11  -> "Buen día"
            in 12..19 -> "Buenas tardes"
            else      -> "Buenas noches"
        }
    }

    // Hora de última sincronización
    val syncTime = remember {
        SimpleDateFormat("HH:mm", locale).format(calendar.time)
    }

    // Estado real de GPS y Bluetooth
    val gpsEnabled = remember {
        runCatching {
            (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
        }.getOrDefault(false)
    }

    val bluetoothEnabled = remember {
        runCatching {
            (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)
                .adapter?.isEnabled == true
        }.getOrDefault(false)
    }

    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = HomeBg,
        bottomBar = {
            // ── Botón + barra de navegación ───────────────────────────────────
            Column(
                modifier = Modifier
                    .shadow(elevation = 12.dp)
                    .background(HomeBg)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Button(
                        onClick = onMarkEntry,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = HomeAccentBlue,
                            contentColor   = Color.White
                        )
                    ) {
                        Text(
                            text       = "Marcar entrada",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.3.sp
                        )
                    }
                }

                NavigationBar(
                    containerColor = HomeCardWhite,
                    tonalElevation = 0.dp
                ) {
                    listOf(
                        Triple(Icons.Filled.Home,    "Inicio",      0),
                        Triple(Icons.Filled.History, "Historial",   1),
                        Triple(Icons.Filled.Sync,    "Sincronizar", 2)
                    ).forEach { (icon, label, index) ->
                        NavigationBarItem(
                            selected  = selectedTab == index,
                            onClick   = { selectedTab = index },
                            icon      = { Icon(icon, contentDescription = label) },
                            label     = { Text(label, fontSize = 11.sp) },
                            colors    = NavigationBarItemDefaults.colors(
                                selectedIconColor   = HomeAccentBlue,
                                selectedTextColor   = HomeAccentBlue,
                                unselectedIconColor = HomeSecondary,
                                unselectedTextColor = HomeSecondary,
                                indicatorColor      = Color(0xFFDEEAFB)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Barra superior ────────────────────────────────────────────────
            HomeTopBar(workerId)

            // ── Saludo + fecha ────────────────────────────────────────────────
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text     = dateString,
                    fontSize = 13.sp,
                    color    = HomeSecondary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text       = "$greeting, $workerId",
                    fontSize   = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color      = HomePrimary
                )
                Text(
                    text     = "Revisa tu faena y comienza tu jornada.",
                    fontSize = 14.sp,
                    color    = HomeSecondary
                )
            }

            // ── Tarjetas ──────────────────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                FaenaCard()

                Spacer(Modifier.height(14.dp))

                EntryStatusCard()

                Spacer(Modifier.height(14.dp))

                DeviceStatusCard(gpsEnabled, bluetoothEnabled)

                Spacer(Modifier.height(14.dp))

                SyncFooter(syncTime)

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Barra superior
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun HomeTopBar(workerId: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HomeCardNavy)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(VoltYellow),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector    = Icons.Filled.Bolt,
                contentDescription = null,
                tint           = HomeCardNavy,
                modifier       = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text          = "Smart Volt",
                fontSize      = 17.sp,
                fontWeight    = FontWeight.ExtraBold,
                color         = VoltYellow,
                letterSpacing = 0.5.sp
            )
            Text(
                text     = "Operario · $workerId",
                fontSize = 12.sp,
                color    = Color(0xFF8A9BB0)
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector    = Icons.Filled.Help,
                contentDescription = "Ayuda",
                tint           = Color(0xFF8A9BB0),
                modifier       = Modifier.size(22.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tarjeta 1: Faena asignada
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun FaenaCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(HomeCardNavy)
            .padding(20.dp)
    ) {
        // Etiqueta
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(7.dp))
                .background(Color(0x33FFC107))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text          = "TU FAENA ASIGNADA",
                fontSize      = 10.sp,
                fontWeight    = FontWeight.Bold,
                color         = VoltYellow,
                letterSpacing = 0.8.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text       = "Subestación Los Maitenes",
            fontSize   = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color      = Color.White
        )

        Spacer(Modifier.height(5.dp))

        Text(
            text     = "F-042 · Frente A / Obras civiles",
            fontSize = 13.sp,
            color    = Color(0xFF8A9BB0)
        )

        Spacer(Modifier.height(18.dp))

        HorizontalDivider(
            thickness = 1.dp,
            color     = Color(0xFF1E3347)
        )

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF122030)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector    = Icons.Filled.AccessTime,
                    contentDescription = null,
                    tint           = VoltYellow,
                    modifier       = Modifier.size(20.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Text(
                    text     = "Jornada de hoy",
                    fontSize = 11.sp,
                    color    = Color(0xFF8A9BB0)
                )
                Text(
                    text       = "08:00 – 17:00",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color.White
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tarjeta 2: Estado de entrada
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun EntryStatusCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(HomeCardWhite)
            .padding(20.dp)
    ) {
        // Badge de estado
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFFEF3C7))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(HomeAmber)
            )
            Spacer(Modifier.width(7.dp))
            Text(
                text       = "Aún sin entrada",
                fontSize   = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color      = Color(0xFF92400E)
            )
        }

        Spacer(Modifier.height(14.dp))

        Text(
            text       = "Todavía no has marcado",
            fontSize   = 17.sp,
            fontWeight = FontWeight.ExtraBold,
            color      = HomePrimary
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text       = "La entrada se registra después de verificar la presencia de tu dispositivo.",
            fontSize   = 13.sp,
            color      = HomeSecondary,
            lineHeight = 20.sp
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tarjeta 3: Estado del dispositivo
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun DeviceStatusCard(gpsEnabled: Boolean, bluetoothEnabled: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(HomeCardWhite)
            .padding(20.dp)
    ) {
        Text(
            text       = "Estado del dispositivo",
            fontSize   = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            color      = HomePrimary
        )

        Spacer(Modifier.height(16.dp))

        DeviceStatusRow(
            icon     = Icons.Filled.LocationOn,
            iconTint = HomeGreen,
            iconBg   = Color(0xFFDCFCE7),
            title    = if (gpsEnabled) "GPS disponible" else "GPS no disponible",
            subtitle = if (gpsEnabled) "Ubicación activada" else "Activa el GPS en ajustes",
            isOk     = gpsEnabled
        )

        Spacer(Modifier.height(14.dp))
        HorizontalDivider(color = Color(0xFFF1F5F9), thickness = 1.dp)
        Spacer(Modifier.height(14.dp))

        DeviceStatusRow(
            icon     = Icons.Filled.Bluetooth,
            iconTint = HomeAccentBlue,
            iconBg   = Color(0xFFDBEAFE),
            title    = if (bluetoothEnabled) "Bluetooth disponible" else "Bluetooth no disponible",
            subtitle = if (bluetoothEnabled) "Listo para buscar la baliza" else "Activa el Bluetooth",
            isOk     = bluetoothEnabled
        )
    }
}

@Composable
private fun DeviceStatusRow(
    icon     : ImageVector,
    iconTint : Color,
    iconBg   : Color,
    title    : String,
    subtitle : String,
    isOk     : Boolean
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector    = icon,
                contentDescription = null,
                tint           = iconTint,
                modifier       = Modifier.size(22.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text       = title,
                fontSize   = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color      = HomePrimary
            )
            Text(
                text     = subtitle,
                fontSize = 12.sp,
                color    = HomeSecondary
            )
        }

        Icon(
            imageVector    = if (isOk) Icons.Filled.CheckCircle else Icons.Filled.Warning,
            contentDescription = null,
            tint           = if (isOk) HomeGreen else HomeAmber,
            modifier       = Modifier.size(20.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Pie de tarjetas – estado de sincronización
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun SyncFooter(syncTime: String) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Icon(
            imageVector    = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint           = HomeGreen,
            modifier       = Modifier.size(14.dp)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text     = "Sin marcas pendientes · Actualizado $syncTime",
            fontSize = 12.sp,
            color    = HomeSecondary
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    SmartVoltTheme {
        HomeScreen(workerId = "OP-0142")
    }
}
