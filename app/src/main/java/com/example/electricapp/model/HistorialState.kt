package com.example.electricapp.model

data class HistorialState(
    val tagBuscar: String = "",
    val instalacion: String = "",
    //Errores
    val tagError: String? = null,
    val intalacionError: String? = null,
)