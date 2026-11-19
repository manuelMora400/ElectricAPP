package com.example.electricapp.model

data class EditarPerfilState(

    val idUsuario : Int = 0,
    val nombre : String = "",
    val telefono : String = "",
    val cargo : String = "",
    val correo : String = "",
    val rut : String = "",
    val rol : Rol = Rol.CLIENTE,

    val errorNombre : String? = null,
    val errorTelefono : String? = null,
    val errorCargo : String? = null,
    val errorGeneral : String? = null

)
