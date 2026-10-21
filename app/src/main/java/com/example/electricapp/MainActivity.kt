package com.example.electricapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.ui.screens.ScreensLogin
import com.example.electricapp.ui.screens.screensRegistro
import com.example.electricapp.ui.theme.ElectricAppTheme

enum class Pantalla{REGISTRO}


private val REGISTRO_LOL  = Pantalla.REGISTRO
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElectricAppTheme {
                 when(REGISTRO_LOL){
                     Pantalla.REGISTRO -> screensRegistro()
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