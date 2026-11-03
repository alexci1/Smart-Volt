package com.example.smart_volt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.smart_volt.ui.screens.LoginScreen
import com.example.smart_volt.ui.theme.SmartVoltTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartVoltTheme {
                LoginScreen(
                    onLoginClick = { identifier, password ->
                        // TODO: implementar lógica de autenticación
                    }
                )
            }
        }
    }
}
