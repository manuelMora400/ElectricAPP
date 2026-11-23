package com.example.electricapp.navigation

sealed class Rutas(val rutas: String){

    data object Login : Rutas("login")
    data object VistaJefe : Rutas("vista")
    data object Registros : Rutas("registros")
    data object Instrumentos : Rutas("instrumentos")
    data object Consulta: Rutas("consulta")
    data object TecnicoTerreno : Rutas("tecnico")
    data object CrearProtocolos : Rutas("crear_protocolos")

    data object DetalleProtocolo: Rutas("detalles_protocolo")

    data object MenuCliente : Rutas("menu_cliente")
    data object VistaIngeniero : Rutas("ingeniero_panel")
    data object RegistrarCuentas : Rutas("crear_cuenta")

    // Equipos  lista y formulario
    data object Equipos : Rutas("equipos")
    data object RegistrarEquipo : Rutas("registrar_equipo")




    data object Perfil : Rutas("perfil")
    data object EditarPerfil : Rutas("editar_perfil/{idUsuario}") {
        fun crear(idUsuario : Long) = "editar_perfil/$idUsuario"
    }
    data object CambiarClave : Rutas("cambiar_clave/{idUsuario}") {
        fun crear(idUsuario : Long) = "cambiar_clave/$idUsuario"
    }
    data object CrearContrasema : Rutas("crear_contrasena")

}