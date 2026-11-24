package com.example.electricapp.model

data class HistorialState(

    val idProtocolo: Long = 0,

    val tagBuscar: String = "",
    val instalacion: String = "",

    val fechaPrueba: String = "",
    val tagError: String? = null,
    val intalacionError: String? = null,

    val busquedaExitosa: Boolean = false,

    val rolUsuario: Rol = Rol.CLIENTE,
    val estado: Estado = Estado.APROBADO,
    val tituloResultado: String = "",
    val listaProtocolo: List<HistorialState> = emptyList(),

    val formalizado: Boolean = false,
    val zonaCliente: String = "",

    val cargando: Boolean = false

){
    val Cliente : Boolean get() = rolUsuario == Rol.CLIENTE
}