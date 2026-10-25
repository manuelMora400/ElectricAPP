package com.example.electricapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.screens.ScreensLogin
import com.example.electricapp.ui.screens.VistaJefeScreens
import com.example.electricapp.ui.screens.VistaTecnicoScrees
import com.example.electricapp.ui.screens.buscarProtocolo
import com.example.electricapp.ui.screens.screensRegistro
import com.example.electricapp.ui.screens.screensRegistrosInstrumentos
import com.example.electricapp.viewModel.FormularioLoginViewModel

@Composable
fun AppNavegacion(){

    val loginViewModel: FormularioLoginViewModel = viewModel()
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.Login.rutas){

        composable(Rutas.Login.rutas){
            ScreensLogin(
                onLogeado = { rol ->

                   val destino = when(rol){

                       Rol.JEFE_TECNICO -> Rutas.VistaJefe.rutas
                       Rol.TECNICO -> Rutas.TecnicoTerreno.rutas
                       Rol.CLIENTE -> Rutas.Consulta.rutas
                       else -> Rutas.Login.rutas
                   }
                    navController.navigate(destino){
                        popUpTo(Rutas.Login.rutas){inclusive = true}
                    }
                }
            )
        }
        composable(Rutas.VistaJefe.rutas) {
            VistaJefeScreens (

                onGestionUsuarios = {
                    navController.navigate(Rutas.Registros.rutas)
                },
                onDesactivarCuentas = {
                    navController.navigate(Rutas.Registros.rutas)
                },
                onInstrumentos = {
                    navController.navigate(Rutas.Instrumentos.rutas)
                },
                onCerrarSesion = {
                    navController.navigate(Rutas.Login.rutas){
                        popUpTo(Rutas.VistaJefe.rutas){inclusive = true}
                    }
                },
                onClick = {
                    navController.navigate(Rutas.Consulta.rutas)
                }
            )
        }

        composable(Rutas.Registros.rutas) {
            screensRegistro(
                onRegistroExitoso = {
                }
            )
        }

        composable(Rutas.Instrumentos.rutas) {
            screensRegistrosInstrumentos(
                onRegistro = {
                },
                onActualizado = {
                }
            )
        }

        composable(Rutas.Consulta.rutas){
            buscarProtocolo(
                Buscado = {
                    navController.navigate(Rutas.VistaJefe.rutas)
                }
            )
        }

        composable(Rutas.TecnicoTerreno.rutas) {
            VistaTecnicoScrees(
                onCrearProtocolos = {
                    navController.navigate(Rutas.CrearProtocolos.rutas)
                },
                onCerrarSesion = {
                    navController.navigate(Rutas.Login.rutas){
                        popUpTo(Rutas.TecnicoTerreno.rutas){inclusive = true}
                    }
                },
                onMisProtocolos = {
                },
                onRegistrarEquipo = {
                },
                onHistorialDeEquipo = {
                }
            )
    }
}}