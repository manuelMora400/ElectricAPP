package com.example.electricapp.model

data class ContadoresPanelJefe(
    val porFormalizar: Int? = 0,
    val instrumentosVencidos: Int = 0,
    val cuentasActivas: Int = 0,
    val solicitudesClave: Int? = 0,
    val instrumentosRegistrados: Int = 0,
    val equipos: Int? = 0
)