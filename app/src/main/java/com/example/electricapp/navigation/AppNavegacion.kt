package com.example.electricapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.electricapp.R
import com.example.electricapp.model.ContadoresPanelIngeniero
import com.example.electricapp.model.ContadoresVistaTecnico
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.screens.DetallesScreen
import com.example.electricapp.ui.screens.EditarPerfilScreens
import com.example.electricapp.ui.screens.MenuClienteScreens
import com.example.electricapp.ui.screens.PerfilScreens
import com.example.electricapp.ui.screens.ScreensLogin
import com.example.electricapp.ui.screens.VistaIngenieroContenido
import com.example.electricapp.ui.screens.VistaJefeScreens
import com.example.electricapp.ui.screens.VistaTecnicoScrees
import com.example.electricapp.ui.screens.buscarProtocolo
import com.example.electricapp.ui.screens.screensCrearContrasena
import com.example.electricapp.ui.screens.screensCrearProtocolos
import com.example.electricapp.ui.screens.screensRegistrarCuentas
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

    fun navegacionRol(rol: Rol) : String{
        return when(rol){
            Rol.JEFE_TECNICO -> Rutas.VistaJefe.rutas
            Rol.TECNICO -> Rutas.TecnicoTerreno.rutas
            Rol.CLIENTE -> Rutas.MenuCliente.rutas
            Rol.INGENIERO -> Rutas.VistaIngeniero.rutas
            else -> Rutas.Login.rutas
        }
    }

    NavHost(navController = navController, startDestination = Rutas.Login.rutas){

        composable(Rutas.Login.rutas){
            ScreensLogin(
                viewModel = loginViewModel,
                onLogeado = { rol ->
                    val destino = if(loginViewModel.uiState.value.cambiarClave){
                        Rutas.CrearContrasema.rutas
                    }else{
                        navegacionRol(rol)
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
                onInstrumentos = {
                    navController.navigate(Rutas.Instrumentos.rutas)
                },
                onHistorial = {
                    navController.navigate(Rutas.Consulta.rutas)
                },
                onCrearProtocolos = {
                    navController.navigate(Rutas.CrearProtocolos.rutas)
                },
                onAnularProtocolos = {
                    enDesarrollo()
                },
                onPerfil = {
                    navController.navigate(Rutas.Perfil.rutas)
                }
            )
        }

        composable(Rutas.Registros.rutas) {
            screensRegistro(

                //rutas -> ir a crear cuenta
                onAgregarCuenta = {
                    navController.navigate(Rutas.RegistrarCuentas.rutas)
                },
                onVolver = { navController.popBackStack() }
            )
        }

        // Pantalla crear cuenta
        composable (Rutas.RegistrarCuentas.rutas){
            screensRegistrarCuentas(
                onRegistroExitoso = {},
                onVolver = {navController.popBackStack()}
            )
        }

        // Crear contraseña
        composable(Rutas.CrearContrasema.rutas){
            screensCrearContrasena(
                 viewModel = loginViewModel,
                onClaveGuardada = {
                    val destino = navegacionRol(loginViewModel.uiState.value.rol)
                    navController.navigate(destino){
                        popUpTo(Rutas.CrearContrasema.rutas){inclusive = true}
                    }

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
                onVolver = {
                    navController.popBackStack()
                },
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
                },
                onPerfil = {
                    navController.navigate(Rutas.Perfil.rutas)
                }
            )
        }

        composable(Rutas.TecnicoTerreno.rutas) {
            VistaTecnicoScrees(

                estado = ContadoresVistaTecnico(),
                nombreTecnico = uiState.nombre.substringBefore(" "),

                onCrearProtocolos = {
                    navController.navigate(Rutas.CrearProtocolos.rutas)
                },
                onCerrarSesion = {
                    cerrarSesion(Rutas.TecnicoTerreno.rutas)
                },
                onMisProtocolos = {
                    enDesarrollo()
                },
                onPerfil = {
                    navController.navigate(Rutas.Perfil.rutas)
                },
                onHistorialDeEquipo = {
                    navController.navigate(Rutas.Consulta.rutas)
                }
            )
    }

        composable(Rutas.VistaIngeniero.rutas) {
            VistaIngenieroContenido(
                estado = ContadoresPanelIngeniero(),
                nombreIngeniero = uiState.nombre.substringBefore(" "),
                onRevisarProtocolos = {
                    enDesarrollo()
                },
                onHistorial = {
                    navController.navigate(Rutas.Consulta.rutas)
                },
                onInstrumentos = {
                    enDesarrollo()
                },
                onPerfil = {
                    navController.navigate(Rutas.Perfil.rutas)
                },
                onFormalizados = {
                    enDesarrollo()
                },
                onCerrarSesion = {
                    cerrarSesion(Rutas.VistaIngeniero.rutas)
                }
            )
        }

        composable(Rutas.Perfil.rutas) {
            PerfilScreens(
                idUsuario = uiState.idUsuario,
                onVolver = {
                    navController.navigateUp()
                },
                onEditarDatos = {
                    navController.navigate(Rutas.EditarPerfil.crear(uiState.idUsuario))
                },
                onCambiarClave = {
                    enDesarrollo()
                },
                onCerrarSesion = {
                    loginViewModel.onCerrarSesion()
                    navController.navigate(Rutas.Login.rutas) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Rutas.EditarPerfil.rutas,
            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
        ) { entrada ->
            val idUsuario = entrada.arguments?.getLong("idUsuario") ?: 0
            EditarPerfilScreens(
                idUsuario = idUsuario,
                rolSesion = uiState.rol,
                onVolver = {
                    navController.navigateUp()
                },
                onGuardado = { nombre ->
                    if (idUsuario == uiState.idUsuario) {
                        loginViewModel.onNombreActualizado(nombre)
                    }
                    Toast.makeText(contexto, contexto.getString(R.string.perfil_datos_actualizados), Toast.LENGTH_SHORT).show()
                    navController.navigateUp()
                }
            )
        }

}}
