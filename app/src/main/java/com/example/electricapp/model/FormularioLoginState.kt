package com.example.electricapp.model
data class FormularioLoginState(

    val correo : String = "",
    val passwordHash : String = "",

    // Posibles errores
    val errorCorreo : String? = null,
    val errorPassword : String? = null,

    // Exito
    val logeadoExitoso : Boolean = false,

    // Accebilidad
    val passwordVisible : Boolean = false
)

