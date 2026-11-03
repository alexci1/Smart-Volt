package com.example.smart_volt.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SmartVoltColorScheme = darkColorScheme(
    primary          = VoltYellow,
    onPrimary        = VoltNavy,
    secondary        = VoltGray,
    onSecondary      = VoltWhite,
    background       = VoltNavy,
    onBackground     = VoltWhite,
    surface          = VoltSurface,
    onSurface        = VoltWhite,
    surfaceVariant   = VoltNavyLight,
    onSurfaceVariant = VoltGrayLight,
    error            = VoltError,
    onError          = VoltWhite,
    outline          = VoltGray
)

@Composable
fun SmartVoltTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SmartVoltColorScheme,
        typography  = Typography,
        content     = content
    )
}
