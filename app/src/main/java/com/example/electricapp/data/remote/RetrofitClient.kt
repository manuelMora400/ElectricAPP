package com.example.electricapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val URL_GATEWAY = "https://lc71zsll-8081.brs.devtunnels.ms/"

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL_GATEWAY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val usuarioApi : UsuarioApi by lazy { retrofit.create(UsuarioApi::class.java) }
    val instrumentoApi : InstrumentoApi by lazy { retrofit.create(InstrumentoApi::class.java) }
    val equipoApi : EquipoApi by lazy { retrofit.create(EquipoApi::class.java) }
    val protocoloApi : ProtocoloApi by lazy { retrofit.create(ProtocoloApi::class.java) }
}
