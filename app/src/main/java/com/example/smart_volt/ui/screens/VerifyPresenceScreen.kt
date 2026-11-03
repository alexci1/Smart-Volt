package com.example.smart_volt.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smart_volt.ui.theme.SmartVoltTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// ─── Paleta de esta pantalla ──────────────────────────────────────────────────
private val VrfBg          = Color(0xFFF4F7FA)
private val VrfCardWhite   = Color.White
private val VrfNavy        = Color(0xFF0D1B2A)
private val VrfAccentBlue  = Color(0xFF1565C0)
private val VrfPrimary     = Color(0xFF1C2432)
private val VrfSecondary   = Color(0xFF6B7280)
private val VrfGreen       = Color(0xFF22C55E)
private val VrfDivider     = Color(0xFFF1F5F9)
private val VrfInfoBg      = Color(0xFFEFF6FF)
private val VrfInfoBorder  = Color(0xFFBFDBFE)
private val VrfInfoText    = Color(0xFF1D4ED8)

// ─────────────────────────────────────────────────────────────────────────────
// Pantalla: Verificar presencia (Paso 1 de 2)
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun VerifyPresenceScreen(
    workerId  : String = "OP-0142",
    onBack    : () -> Unit = {},
    onConfirm : () -> Unit = {}
) {
    val locale     = Locale("es", "CL")
    val dateString = remember {
        SimpleDateFormat("d MMM yyyy", locale)
            .format(Calendar.getInstance().time)
    }

    Scaffold(
        containerColor = VrfBg,
        topBar  = { VerifyTopBar(onBack) },
        bottomBar = { VerifyBottomBar(onConfirm) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(Modifier.height(22.dp))

            // ── Insignia de flujo ─────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFDEEAFB))
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text          = "Paso 1 de 2  ·  Verificación",
                    fontSize      = 12.sp,
                    fontWeight    = FontWeight.SemiBold,
                    color         = VrfAccentBlue,
                    letterSpacing = 0.2.sp
                )
            }

            Spacer(Modifier.height(22.dp))

            // ── Encabezado de sección ─────────────────────────────────────────
            Text(
                text       = "Todo listo para marcar",
                fontSize   = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = VrfPrimary
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text       = "Comprobamos la ubicación y el entorno de tu dispositivo.",
                fontSize   = 14.sp,
                color      = VrfSecondary,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(26.dp))

            // ── Bloque 1: Verificaciones del sistema ──────────────────────────
            SystemVerificationsCard()

            Spacer(Modifier.height(14.dp))

            // ── Bloque 2: Resumen del registro ────────────────────────────────
            RegistrationSummaryCard(workerId, dateString)

            Spacer(Modifier.height(14.dp))

            // ── Bloque 3: Cuadro informativo azul ────────────────────────────
            InfoBox()

            Spacer(Modifier.height(8.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Top Bar
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun VerifyTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(VrfNavy)
            .padding(end = 8.dp, top = 4.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint               = Color.White,
                modifier           = Modifier.size(22.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text       = "Verificar presencia",
                fontSize   = 17.sp,
                fontWeight = FontWeight.Bold,
                color      = Color.White
            )
            Text(
                text     = "Smart Volt · Asistencia en faena",
                fontSize = 12.sp,
                color    = Color(0xFF8A9BB0)
            )
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector        = Icons.Filled.Help,
                contentDescription = "Ayuda",
                tint               = Color(0xFF8A9BB0),
                modifier           = Modifier.size(20.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Bloque 1: Verificaciones del sistema
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun SystemVerificationsCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(VrfCardWhite)
            .padding(20.dp)
    ) {
        Text(
            text          = "VERIFICACIONES DEL SISTEMA",
            fontSize      = 11.sp,
            fontWeight    = FontWeight.Bold,
            color         = VrfSecondary,
            letterSpacing = 0.8.sp
        )

        Spacer(Modifier.height(18.dp))

        // GPS
        VerificationRow(
            icon      = Icons.Filled.LocationOn,
            iconTint  = VrfGreen,
            iconBg    = Color(0xFFDCFCE7),
            title     = "Dentro de la faena",
            detail    = "GPS dentro del perímetro autorizado"
        )

        HorizontalDivider(
            modifier  = Modifier.padding(vertical = 14.dp),
            color     = VrfDivider,
            thickness = 1.dp
        )

        // Bluetooth
        VerificationRow(
            icon      = Icons.Filled.Bluetooth,
            iconTint  = VrfAccentBlue,
            iconBg    = Color(0xFFDBEAFE),
            title     = "Baliza autorizada detectada",
            detail    = "BLE-042-A · Señal cercana: -62 dBm"
        )

        HorizontalDivider(
            modifier  = Modifier.padding(vertical = 14.dp),
            color     = VrfDivider,
            thickness = 1.dp
        )

        // Seguridad
        VerificationRow(
            icon      = Icons.Filled.Security,
            iconTint  = Color(0xFF7C3AED),
            iconBg    = Color(0xFFEDE9FE),
            title     = "Integridad revisada",
            detail    = "Sin ubicación simulada ni modo desarrollador detectados"
        )
    }
}

@Composable
private fun VerificationRow(
    icon     : ImageVector,
    iconTint : Color,
    iconBg   : Color,
    title    : String,
    detail   : String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier          = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier           = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment   = Alignment.Center
        ) {
            Icon(
                imageVector        = icon,
                contentDescription = null,
                tint               = iconTint,
                modifier           = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text       = title,
                fontSize   = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color      = VrfPrimary
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text       = detail,
                fontSize   = 12.sp,
                color      = VrfSecondary,
                lineHeight = 17.sp
            )
        }

        Spacer(Modifier.width(10.dp))

        Icon(
            imageVector        = Icons.Filled.CheckCircle,
            contentDescription = "Verificado",
            tint               = VrfGreen,
            modifier           = Modifier.size(22.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Bloque 2: Resumen del registro
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun RegistrationSummaryCard(workerId: String, dateString: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(VrfCardWhite)
            .padding(20.dp)
    ) {
        // Encabezado con punto verde
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(VrfGreen)
            )
            Spacer(Modifier.width(9.dp))
            Text(
                text       = "Vas a registrar una entrada",
                fontSize   = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = VrfPrimary
            )
        }

        Spacer(Modifier.height(16.dp))
        HorizontalDivider(color = VrfDivider, thickness = 1.dp)
        Spacer(Modifier.height(16.dp))

        SummaryRow(label = "Operario", value = workerId)
        Spacer(Modifier.height(12.dp))
        SummaryRow(label = "Faena",    value = "F-042 · Los Maitenes")
        Spacer(Modifier.height(12.dp))
        SummaryRow(label = "Frente",   value = "A / Obras civiles")
        Spacer(Modifier.height(12.dp))
        SummaryRow(label = "Fecha",    value = dateString)
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text     = label,
            fontSize = 13.sp,
            color    = VrfSecondary
        )
        Text(
            text       = value,
            fontSize   = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color      = VrfPrimary
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Bloque 3: Cuadro informativo azul
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun InfoBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.5.dp,
                color = VrfInfoBorder,
                shape = RoundedCornerShape(14.dp)
            )
            .clip(RoundedCornerShape(14.dp))
            .background(VrfInfoBg)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector        = Icons.Filled.Info,
            contentDescription = null,
            tint               = VrfInfoText,
            modifier           = Modifier
                .size(20.dp)
                .padding(top = 1.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text       = "Verificamos presencia del dispositivo",
                fontSize   = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color      = VrfInfoText
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text       = "GPS y Bluetooth no confirman por sí solos la identidad de la persona.",
                fontSize   = 13.sp,
                color      = Color(0xFF3B82F6),
                lineHeight = 19.sp
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Bottom Bar: botón + nota al pie
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun VerifyBottomBar(onConfirm: () -> Unit) {
    Column(
        modifier              = Modifier
            .fillMaxWidth()
            .background(VrfBg)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        Button(
            onClick  = onConfirm,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape  = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = VrfGreen,
                contentColor   = Color.White
            )
        ) {
            Icon(
                imageVector        = Icons.Filled.Check,
                contentDescription = null,
                modifier           = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text       = "Confirmar entrada",
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.3.sp
            )
        }

        Spacer(Modifier.height(10.dp))

        Text(
            text      = "Se registrará la hora al confirmar.",
            fontSize  = 12.sp,
            color     = VrfSecondary,
            textAlign = TextAlign.Center
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyPresencePreview() {
    SmartVoltTheme {
        VerifyPresenceScreen(workerId = "OP-0142")
    }
}
