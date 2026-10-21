package com.example.electricapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.electricapp.ui.screens.LoginScreens
import com.example.electricapp.ui.screens.ScreensLogin
import com.example.electricapp.ui.screens.VistaJefeScreens
import com.example.electricapp.viewModel.FormularioLoginViewModel

@Composable
fun AppNavegacion(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.Login.rutas){

        composable(Rutas.Login.rutas){
            ScreensLogin(
                onLogeado = {
                    navController.navigate(Rutas.VistaJefe.rutas){
                        popUpTo(Rutas.Login.rutas){inclusive = true}
                    }
                }
            )
        }

        composable(Rutas.VistaJefe.rutas) {
            VistaJefeScreens (
                onClick = {
                    navController.navigate(Rutas.Registros.rutas)
                }
            )
        }

    }
}