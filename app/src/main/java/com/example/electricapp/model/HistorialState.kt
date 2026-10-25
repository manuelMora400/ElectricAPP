package com.example.electricapp.model

data class HistorialState(

    val tagBuscar: String = "",
    val instalacion: String = "",

    val fechaPrueba: String? = null,
    val tagError: String? = null,
    val intalacionError: String? = null,

    val busquedaExitosa: Boolean = false,

    val rolUsuario: Rol = Rol.CLIENTE,
    val estado: Estado = Estado.APROBADO,
    val tituloResultado: String = "",


){
    val Cliente : Boolean get() = rolUsuario == Rol.CLIENTE
}