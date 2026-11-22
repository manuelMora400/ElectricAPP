package com.example.electricapp.model

data class PerfilUsuario(

    val idUsuario : Long = 0,
    val nombre : String = "",
    val correo : String = "",
    val rut : String = "",
    val telefono : String = "",
    val cargo : String = "",
    val rol : Rol = Rol.CLIENTE

){

    val iniciales : String
        get() = nombre.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .joinToString("") { it.first().uppercase() }

}