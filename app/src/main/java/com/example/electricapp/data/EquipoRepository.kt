package com.example.electricapp.data

import com.example.electricapp.data.remote.CrearEquipoRequest
import com.example.electricapp.data.remote.EquipoApi
import com.example.electricapp.data.remote.EquipoResponse
import com.example.electricapp.data.remote.RetrofitClient
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


class EquipoRepository(
    private val equipoApi: EquipoApi = RetrofitClient.equipoApi
) {

    suspend fun listarEquipos(): List<EquipoResponse> = llamar {
        equipoApi.listarEquipos()
    }

    suspend fun registrarEquipo(datos: CrearEquipoRequest): EquipoResponse = llamar {
        equipoApi.crearEquipo(datos)
    }

    private suspend fun <T> llamar(peticion: suspend () -> T): T = try {
        peticion()
    } catch (e: HttpException) {
        throw Exception(mensajeDelServidor(e))
    } catch (e: IOException) {
        throw Exception("No se pudo conectar con el servidor")
    }

    private fun mensajeDelServidor(e: HttpException): String {
        val cuerpo = e.response()?.errorBody()?.string()
        val mensaje = try {
            JSONObject(cuerpo ?: "").optString("message")
        } catch (ex: Exception) {
            ""
        }
        return mensaje.ifBlank { "Error del servidor (${e.code()})" }
    }
}
