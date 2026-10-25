package com.example.electricapp.navigation

sealed class Rutas(val rutas: String){
    data object Login : Rutas("login")
    data object VistaJefe : Rutas("vista")
    data object Registros : Rutas("registros")
    data object Instrumentos : Rutas("instrumentos")
    data object Consulta: Rutas("consulta")
    data object TecnicoTerreno : Rutas("tecnico")
    data object CrearProtocolos : Rutas("crear_protocolos")
}