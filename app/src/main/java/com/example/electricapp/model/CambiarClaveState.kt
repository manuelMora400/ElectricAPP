package com.example.electricapp.model

data class CambiarClaveState(

    val claveActual : String = "",
    val nuevaClave : String = "",
    val confirmarClave : String = "",

    val errorClaveActual : String? = null,
    val errorNuevaClave : String? = null,
    val errorConfirmarClave : String? = null,
    val errorGeneral : String? = null,

    val cumpleLargoPassword : Boolean = false,
    val cumpleMayusculaPassword : Boolean = false,
    val cumpleSimboloPassword : Boolean = false,

    val passwordVisible : Boolean = false

)
