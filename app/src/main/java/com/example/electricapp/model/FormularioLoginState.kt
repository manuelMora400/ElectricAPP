package com.example.electricapp.model
data class FormularioLoginState(

    val idUsuario : Int = 0,
    val nombre : String = "",
    val correo : String = "",
    val passwordHash : String = "",
    val rol : Rol = Rol.CLIENTE,
    val activo : Boolean = true,

    // actualizacion en data class login para usarla para que el usuario cambie contraseña
    // obligatoriamente despues del sms
    val cambiarClave : Boolean = false,
    val nuevaClave : String = "",
    val confirmarClave : String = "",
    val errorNuevaClave : String? = "",
    val errorConfirmarClave : String? = "",

    // Recupera contraseña
    val idRecuperacion : String = "",
    val errorRecuperacion : String? = null,
    val mensajeRecuperacionExito : String? = null,
    val telefonoRecuperacion : String? = null,
    val claveTemporal : String? = null,

    val cumpleLargoPassword : Boolean = false,
    val cumpleMayusculaPassword : Boolean = false,
    val cumpleSiboloPassword : Boolean = false,

    val errorCorreo : String? = null,
    val errorPassword : String? = null,
    val errorGeneral : String?  = null,

    val mensajeExitoso : String = "",
    val logeadoExitoso : Boolean = false,


    val passwordVisible : Boolean = false
)

