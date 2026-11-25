package com.example.electricapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.electricapp.navigation.AppNavegacion
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
