package com.example.electricapp.model

data class FormularioInstrumentosStates(

    val nombre : String = "",
    val marca  : String = "",
    val modelo : String = "",
    val numSerie : String = "" ,

    val fechaCalibracion : String = "" ,
    val fechaVencimiento : String = "",

    val errorNombre : String? = null,
    val errorMarca  : String? = null,
    val errorModelo : String? = null,
    val errorNumeroSerie : String? = null,

    val errorFechaCalibracion : String? = null,
    val errorFechaVencimiento : String? = null,

    val mensajeExitososo : String = "",
    val  mensajeExito : Boolean = false,

    val id : Int = 0,
    val vencido : Boolean = false )