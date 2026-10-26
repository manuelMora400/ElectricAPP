package com.example.electricapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.electricapp.R
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.screens.MenuClienteScreens
import com.example.electricapp.ui.screens.ScreensLogin
import com.example.electricapp.ui.screens.VistaJefeScreens
import com.example.electricapp.ui.screens.VistaTecnicoScrees
import com.example.electricapp.ui.screens.buscarProtocolo
import com.example.electricapp.ui.screens.screensCrearProtocolos
import com.example.electricapp.ui.screens.screensRegistro
import com.example.electricapp.ui.screens.screensRegistrosInstrumentos
import com.example.electricapp.viewModel.FormularioLoginViewModel

@Composable
fun AppNavegacion(){


    val loginViewModel: FormularioLoginViewModel = viewModel()
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    val navController = rememberNavController()
    val contexto = LocalContext.current


    fun enDesarrollo(){
        Toast.makeText(contexto, contexto.getString(R.string.en_desarrollo), Toast.LENGTH_SHORT).show()
    }


    fun cerrarSesion(rutaActual : String){
        loginViewModel.onCerrarSesion()
        navController.navigate(Rutas.Login.rutas){
            popUpTo(rutaActual){inclusive = true}
        }
    }

    NavHost(navController = navController, startDestination = Rutas.Login.rutas){

        composable(Rutas.Login.rutas){
            ScreensLogin(
                viewModel = loginViewModel,
                onLogeado = { rol ->

                   val destino = when(rol){

                       Rol.JEFE_TECNICO -> Rutas.VistaJefe.rutas
                       Rol.TECNICO -> Rutas.TecnicoTerreno.rutas
                       Rol.CLIENTE -> Rutas.MenuCliente.rutas
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
                onRegistrarClientes = {
                    navController.navigate(Rutas.Registros.rutas)
                },
                onInstrumentos = {
                    navController.navigate(Rutas.Instrumentos.rutas)
                },
                onHistorial = {
                    navController.navigate(Rutas.Consulta.rutas)
                },
                onCrearProtocolos = {
                    navController.navigate(Rutas.CrearProtocolos.rutas)
                },
                onRevisarProtocolos = {
                    enDesarrollo()
                },
                onAnularProtocolos = {
                    enDesarrollo()
                },
                onCerrarSesion = {
                    cerrarSesion(Rutas.VistaJefe.rutas)
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
                rol = uiState.rol,
                Buscado = {
                }
            )
        }

        composable(Rutas.CrearProtocolos.rutas) {
            screensCrearProtocolos(
                idTecnico = uiState.idUsuario,
                nombreTecnico = uiState.nombre,
                onRegistrado = {
                }
            )
        }

        composable(Rutas.MenuCliente.rutas) {
            MenuClienteScreens(
                onClick = {
                    navController.navigate(Rutas.Consulta.rutas)
                },
                onCerrarSesion = {
                    cerrarSesion(Rutas.MenuCliente.rutas)
                }
            )
        }

        composable(Rutas.TecnicoTerreno.rutas) {
            VistaTecnicoScrees(
                onCrearProtocolos = {
                    navController.navigate(Rutas.CrearProtocolos.rutas)
                },
                onCerrarSesion = {
                    cerrarSesion(Rutas.TecnicoTerreno.rutas)
                },
                onMisProtocolos = {
                    enDesarrollo()
                },
                onRegistrarEquipo = {
                    enDesarrollo()
                },
                onHistorialDeEquipo = {
                    navController.navigate(Rutas.Consulta.rutas)
                }
            )
    }
}}
