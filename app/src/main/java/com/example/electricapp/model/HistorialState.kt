package com.example.electricapp.model

data class HistorialState(

    val tagBuscar: String = "",
    val instalacion: String = "",

    val fechaPrueba: String? = null,
    val tagError: String? = null,
    val intalacionError: String? = null,

    val busquedaExitosa: Boolean = false,

    val rolUsuario: Rol = Rol.CLIENTE,

    val tituloResultado: String = "",
    val listaProtocolos: List<ProtocoloItem> = emptyList()
)