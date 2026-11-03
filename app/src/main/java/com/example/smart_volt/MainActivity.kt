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
import com.example.smart_volt.ui.screens.HomeScreen
import com.example.smart_volt.ui.screens.LoginScreen
import com.example.smart_volt.ui.screens.VerifyPresenceScreen
import com.example.smart_volt.ui.theme.SmartVoltTheme

enum class Screen {
    LOGIN,
    DEVICE_SETUP,
    HOME,
    VERIFY_PRESENCE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartVoltTheme {
                var currentScreen by remember { mutableStateOf(Screen.LOGIN) }
                var workerId      by remember { mutableStateOf("") }

                when (currentScreen) {

                    Screen.LOGIN -> LoginScreen(
                        onLoginClick = { identifier, _ ->
                            workerId      = identifier.ifBlank { "OP-0000" }
                            currentScreen = Screen.DEVICE_SETUP
                        }
                    )

                    Screen.DEVICE_SETUP -> DeviceSetupScreen(
                        onSetupComplete = { currentScreen = Screen.HOME }
                    )

                    Screen.HOME -> HomeScreen(
                        workerId    = workerId,
                        onMarkEntry = { currentScreen = Screen.VERIFY_PRESENCE }
                    )

                    Screen.VERIFY_PRESENCE -> VerifyPresenceScreen(
                        workerId  = workerId,
                        onBack    = { currentScreen = Screen.HOME },
                        onConfirm = {
                            // TODO: Paso 2 – confirmar y guardar marca
                        }
                    )
                }
            }
        }
    }
}
