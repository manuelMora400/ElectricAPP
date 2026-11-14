package com.example.electricapp.model

data class DetallesState (
    val Tag: String = "",
    val Instalacion: String = "",
    val medicion1Min: String,
    val medicion10Min: String,
    val devanado: String,
    val tensionPrueba: String,
    val resultado: String,
    val estado: String,
    val nombreIngeniero: String?,
    val fechaPrueba: String
){

}