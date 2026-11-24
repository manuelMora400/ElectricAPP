package com.example.electricapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProtocoloApi {

    @GET("api/aislacion/filtro")
    suspend fun filtrarAislaciones(
        @Query("tag") tag: String?,
        @Query("instalacion") instalacion: String?
    ): List<AislacionResponse>

    @GET("api/aislacion/protocolo/{idProtocolo}")
    suspend fun aislacionesDeProtocolo(@Path("idProtocolo") idProtocolo: Long): List<AislacionResponse>

    @GET("api/protocolos/{id}")
    suspend fun obtenerProtocolo(@Path("id") id: Long): ProtocoloResponse
}