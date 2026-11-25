package com.example.electricapp.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @PUT("api/protocolos/{id}/estado")
    suspend fun cambiarEstado(
        @Path("id") id: Long,
        @Body request: CambiarEstadoProtocoloRequest
    ): ProtocoloResponse

    @GET("api/protocolos")
    suspend fun listarProtocolos(): List<ProtocoloResponse>

    @GET("api/protocolos/tecnico/{idTecnico}")
    suspend fun protocolosDelTecnico(@Path("idTecnico") idTecnico: Long): List<ProtocoloResponse>

    @POST("api/protocolos")
    suspend fun crearProtocolo(@Body datos: CrearProtocoloRequest): ProtocoloResponse

    @POST("api/aislacion")
    suspend fun crearAislacion(@Body datos: CrearAislacionRequest): AislacionResponse

    @GET("api/aislacion/devanados")
    suspend fun listarDevanados(): List<String>
}
