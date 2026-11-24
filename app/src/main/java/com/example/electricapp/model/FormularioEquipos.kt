package com.example.electricapp.model


data class FormularioEquipos(

    val id : Long = 0L,
    val tag : String = "",
    val tipo : String = "",
    val instalacion : String = "",
    val idCliente : Long = 0L,
    val cliente : String = "",
    val errorTag : String? = null,
    val errorTipo : String? = null,
    val errorInstalacion : String? = null,
    val errorCliente : String? = null,
    val errorGeneral : String? = null,
    val registradoExito : Boolean = false
)
