package com.example.electricapp.model

data class EditarPerfilState(

    val idUsuario : Int = 0,
    val nombre : String = "",
    val telefono : String = "",
    val correo : String = "",
    val rut : String = "",
    val rol : Rol = Rol.CLIENTE,
    val puedeEditarCorreo : Boolean = false,

    val errorNombre : String? = null,
    val errorCorreo : String? = null,
    val errorTelefono : String? = null,
    val errorGeneral : String? = null

)
