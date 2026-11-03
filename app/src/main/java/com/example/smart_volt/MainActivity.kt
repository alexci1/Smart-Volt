package com.example.smart_volt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.smart_volt.ui.screens.DeviceSetupScreen
import com.example.smart_volt.ui.screens.LoginScreen
import com.example.smart_volt.ui.theme.SmartVoltTheme

// Pantallas disponibles en el flujo de la app
enum class Screen {
    LOGIN,
    DEVICE_SETUP
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartVoltTheme {
                var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

                when (currentScreen) {
                    Screen.LOGIN -> LoginScreen(
                        onLoginClick = { _, _ ->
                            currentScreen = Screen.DEVICE_SETUP
                        }
                    )
                    Screen.DEVICE_SETUP -> DeviceSetupScreen(
                        onSetupComplete = {
                            // TODO: navegar al siguiente screen
                        }
                    )
                }
            }
        }
    }
}
