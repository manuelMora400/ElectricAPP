package com.example.electricapp.navigation

sealed class Rutas(val rutas: String){

    data object Login : Rutas("login")
    data object VistaJefe : Rutas("Vista")
    data object Registros : Rutas("Registros")
    data object Instrumentos : Rutas("Instrumentos")
    
}