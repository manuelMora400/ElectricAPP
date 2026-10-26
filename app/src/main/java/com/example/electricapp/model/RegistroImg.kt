package com.example.electricapp.model

data class RegistroImg(
    val nombre: String,
    val id : Long = 0,
    val Documento: ByteArray? = null
)