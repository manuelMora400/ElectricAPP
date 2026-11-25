package com.example.electricapp.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InstrumentoApi {

    @GET("api/instrumentos")
    suspend fun listarInstrumentos() : List<InstrumentoResponse>

    @GET("api/instrumentos/cantidad")
    suspend fun contador() : ContadorResponse

    @POST("api/instrumentos")
    suspend fun crearInstrumentos(@Body datos : CrearInstrumentoRequest) : InstrumentoResponse

    @POST("api/instrumentos/serie/{numeroSerie}/calibraciones")
    suspend fun renovarCalibracion(@Path("numeroSerie") numeroSerie : String, @Body datos : RenovarCalibracionRequest): InstrumentoResponse
}