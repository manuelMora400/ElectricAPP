package com.example.electricapp.generador

import java.security.SecureRandom

object GeneradorClave {

    private val generarRandom  = SecureRandom()
    private val letrasMayusculas = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val alfabetoMixto = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    private val numeros = "0123456789"

    fun generarClave() : String{

        val mayuscula = letrasMayusculas[generarRandom.nextInt(letrasMayusculas.length)]

        val mixto = (1..6).map{
            alfabetoMixto[generarRandom.nextInt(alfabetoMixto.length)]
        }.joinToString ("")

        val puntoContedor = "."

        val numeros = (1..2).map {
            numeros[generarRandom.nextInt(numeros.length)]
        }.joinToString ("")

        return mayuscula.toString() + mixto + puntoContedor + numeros
    }
}