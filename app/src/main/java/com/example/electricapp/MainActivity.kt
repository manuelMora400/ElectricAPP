package com.example.electricapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.ui.ui.screens.LoginScreens
import com.example.electricapp.ui.ui.screens.ScreensLogin
import com.example.electricapp.ui.ui.theme.ElectricAppTheme

enum class Pantalla{LOGIN}

private val PANTALLA_INICIAL  = Pantalla.LOGIN
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElectricAppTheme {
                 when(PANTALLA_INICIAL){
                     Pantalla.LOGIN -> ScreensLogin()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElectricAppTheme {
        Greeting("Android")
    }
}