package com.example.electricapp.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EquipoApi {

    @GET("api/equipos")
    suspend fun listarEquipos(): List<EquipoResponse>

    @POST("api/equipos")
    suspend fun crearEquipo(@Body datos: CrearEquipoRequest): EquipoResponse
}
