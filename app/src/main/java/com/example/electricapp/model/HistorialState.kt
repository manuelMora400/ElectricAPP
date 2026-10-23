package com.example.electricapp.model

data class HistorialState(
    val tagBuscar: String = "",
    val instalacion: String = "",

    //Errores
    val tagError: String? = null,
    val intalacionError: String? = null,

    val mensajeExitoso : String = "",
    val busquedaExitosa : Boolean = false,

    val rol : Rol = Rol.CLIENTE
){
    val Cliente : Boolean  get() = rol == Rol.CLIENTE
}