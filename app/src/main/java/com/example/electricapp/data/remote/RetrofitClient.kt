package com.example.electricapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val URL_USUARIOS = "https://lc71zsll-8080.brs.devtunnels.ms/"

    private fun crear(urlBase : String) : Retrofit =
        Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val usuarioApi: UsuarioApi  by lazy { crear(URL_USUARIOS).create(UsuarioApi::class.java)}
}