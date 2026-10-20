package com.example.electricapp.model

data class FormularioInstrumentosStates(

    // Datos de instrumentos
    val nombre : String = "",
    val marca  : String = "",
    val modelo : String = "",
    val numSerie : String = "" ,

    //Calibracion
    val fechaCalibracion : String = "" ,
    val fechaVencimiento : String = "",

    val errorNombre : String? = null,
    val errorMarca  : String? = null,
    val errorModelo : String? = null,
    val errorNumeroSerie : String? = null,

    val errorFechaCalibracion : String? = null,
    val errorFechaVencimiento : String? = null,

    val mensajeExitososo : String = "",
    val  mensajeExito : Boolean = false )