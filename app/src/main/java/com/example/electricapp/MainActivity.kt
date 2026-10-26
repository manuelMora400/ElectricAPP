package com.example.electricapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.navigation.AppNavegacion
import com.example.electricapp.ui.screens.ScreensLogin
import com.example.electricapp.ui.screens.screensRegistro
import com.example.electricapp.ui.theme.ElectricAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElectricAppTheme {
                AppNavegacion()
            }
        }
    }
}
