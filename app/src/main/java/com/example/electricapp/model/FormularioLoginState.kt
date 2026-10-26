package com.example.electricapp.model
data class FormularioLoginState(

    val idUsuario : Int = 0,
    val nombre : String = "",

    val correo : String = "",
    val passwordHash : String = "",
    val rol : Rol = Rol.CLIENTE,
    val activo : Boolean = true,

    val errorCorreo : String? = null,
    val errorPassword : String? = null,
    val errorGeneral : String?  = null,

    val mensajeExitoso : String = "",
    val logeadoExitoso : Boolean = false,


    val passwordVisible : Boolean = false
)

