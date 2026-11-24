package com.example.electricapp.model

data class ProtocoloRevision(

    val idProtocolo : Int = 0,
    val tag : String = "",
    val instalacion : String = "",
    val fechaPrueba : String = "",
    val tecnico : String = "",

    val devanado : String = "",
    val tensionPrueba : String = "",
    val medicion1Min : String = "",
    val medicion10Min : String = "",

    val resultado : String = "",
    val estado : String = ""

)
