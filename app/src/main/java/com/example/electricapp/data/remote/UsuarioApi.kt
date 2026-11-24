package com.example.electricapp.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioApi {

    @POST("api/usuarios")
    suspend fun crearUsuarios(@Body datos : CrearUsuariosRequest): UsuarioResponse

    @POST("api/usuarios/login")
    suspend fun login(@Body datos : LoginRequest) : LoginResponse

    @GET("api/usuarios/obtenerPerfil/{id}")
    suspend fun obtenerPefil(@Path("id") id : Long) : UsuarioResponse

    @GET("api/usuarios/listar")
    suspend fun listarUsuarios() : List<UsuarioResponse>

    @POST("api/usuarios/cambiarEstado/{id}")
    suspend fun cambiaEstado(@Path("id") id : Long): UsuarioResponse

    @PUT("api/usuarios/cambiarClave/{id}")
    suspend fun cambiarClave(@Path("id") id : Long, @Body datos : CambiarClaveRequest)

    @POST("api/usuarios/recuperar-clave")
    suspend fun recuperarClave(@Body datos : RecuperarClaveRequest) : UsuarioResponse

    @GET("api/usuarios/activos/cantidad")
    suspend fun cantidadCuentasActivas()  : ContadorResponse
}