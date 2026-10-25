package com.example.electricapp.model

data class FormularioRegistros(

    val rut : String = "",
    val nombre : String = "",
    val telefono : String = "",
    val correo : String = "",
    val password : String = "",
    val rol : Rol = Rol.CLIENTE,
    val activo : Boolean = true,
    val giro : String = "",
    val contactoCargo : String = "",

    val errorRut : String? = null,
    val errorNombre : String? = null,
    val errorTelefono : String? = null,
    val errorCorreo : String? = null,
    val errorPassword : String? = null,
    val errorGiro : String? = null,
    val errorCotacto : String? = null,
    val errorGeneral : String?= null,
    val registradoExito : Boolean = false

){
    val Cliente : Boolean  get() = rol == Rol.CLIENTE
}
