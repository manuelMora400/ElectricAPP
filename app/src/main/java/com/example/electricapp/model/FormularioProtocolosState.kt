package com.example.electricapp.model

data class FormularioProtocolosState(

    val equipo : String = "",
    val fechaHora : String = "",
    val tecnico : String = "",
    val temperatura : String = "",
    val humedad : String = "",

    val devanado : String = "",
    val tensionPrueba : String = "",
    val lecturaUnMinuto : String = "",
    val lecturaDiezMinutos : String = "",

    val instrumento : String = "",

    val indicePolarizacion : Double? = null,
    val resultado : ResultadoPrueba? = null,

    val errorEquipo : String? = null,
    val errorTemperatura : String? = null,
    val errorHumedad : String? = null,
    val errorDevanado : String? = null,
    val errorTensionPrueba : String? = null,
    val errorLecturaUnMinuto : String? = null,
    val errorLecturaDiezMinutos : String? = null,
    val errorInstrumento : String? = null,

    val mensajeExitoso : String = "",
    val mensajeExito : Boolean = false
)


data class InstrumentoEjemplo(
    val nombre : String,
    val fechaVencimiento : String
)
