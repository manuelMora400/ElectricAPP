package com.example.electricapp.model

// Esta data class no es la base de datos
data class FormularioRegistros(

    val rut : String = "",
    val nombre : String = "",
    val telefono : String = "",
    val correo : String = "",
    val password : String = "", // Este sera por ahora creada a mano
    val rol : Rol = Rol.CLIENTE,

    //------------------- SOLO CLIENTE, NULLS PARA EL PERSONAL -------------------//
    val giro : String = "",
    val contactoCargo : String = "",

    val errorRut : String? = null,
    val errorNombre : String? = null,
    val errorTelefono : String? = null,
    val errorCorreo : String? = null,
    val errorPassword : String? = null,
    val errorGiro : String? = null,
    val errorCotacto : String? = null,

    val registradoExito : Boolean = false

){
    // Esto es para desplegar una opcion extra al formulario si es solo cliente
    val Cliente : Boolean  get() = rol == Rol.CLIENTE
}

// Clase para probar datos ejemplos
data class UsuarioEjemplo(
    val id : Long = 0,
    val nombre: String,
    val rol : Rol,
    val correo : String,
    val activo : Boolean = true
)
