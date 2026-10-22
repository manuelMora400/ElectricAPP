package com.example.electricapp.navigation

sealed class Rutas(val rutas: String){

    data object Login : Rutas("login")
    data object VistaJefe : Rutas("rista")
    data object Registros : Rutas("registros")
    data object Instrumentos : Rutas("instrumentos")

    data object Consulta: Rutas("consulta")
}