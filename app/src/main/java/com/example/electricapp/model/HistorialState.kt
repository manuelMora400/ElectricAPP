package com.example.electricapp.model

data class HistorialState(

    val tagBuscar: String = "",
    val instalacion: String = "",

    val fechaPrueba: String = "",
    val tagError: String? = null,
    val intalacionError: String? = null,

    val busquedaExitosa: Boolean = false,

    val rolUsuario: Rol = Rol.CLIENTE,
    val estado: Estado = Estado.APROBADO,
    val tituloResultado: String = "",
    val listaProtocolo: List<HistorialState> = emptyList()

){
    val Cliente : Boolean get() = rolUsuario == Rol.CLIENTE
}