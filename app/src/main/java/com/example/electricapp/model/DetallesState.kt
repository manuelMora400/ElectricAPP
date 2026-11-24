package com.example.electricapp.model

data class DetallesState(
    val idProtocolo: Long = 0,
    val Tag: String = "",
    val Instalacion: String = "",
    val medicion1Min: String = "",
    val medicion10Min: String = "",
    val devanado: String = "",
    val tensionPrueba: String = "",
    val resultado: String = "",
    val estado: String = "",
    val nombreIngeniero: String? = null,
    val fechaPrueba: String = "",
    val listaDetalles: List<DetallesState> = emptyList(),
    val latitud: Double? = null,
    val longitud: Double? = null,

    val cargando: Boolean = false,
    val error: String? = null
)