package com.example.electricapp.navigation

sealed class Rutas(val rutas: String){

    data object Login : Rutas("login")
    data object VistaJefe : Rutas("vista")
    data object Registros : Rutas("registros")
    data object Instrumentos : Rutas("instrumentos")
    data object Consulta: Rutas("consulta")
    data object TecnicoTerreno : Rutas("tecnico")
    data object CrearProtocolos : Rutas("crear_protocolos")
    data object MenuCliente : Rutas("menu_cliente")
    data object VistaIngeniero : Rutas("ingeniero_panel")
    data object PorRevisar : Rutas("ingeniero_por_revisar")
    data object RegistrarCuentas : Rutas("crear_cuenta")

    data object Perfil : Rutas("perfil")
    data object EditarPerfil : Rutas("editar_perfil/{idUsuario}") {
        fun crear(idUsuario : Int) = "editar_perfil/$idUsuario"
    }
    data object CambiarClave : Rutas("cambiar_clave/{idUsuario}") {
        fun crear(idUsuario : Int) = "cambiar_clave/$idUsuario"
    }
    data object CrearContrasema : Rutas("crear_contrasena")

}