package com.example.electricapp.data

import com.example.electricapp.data.remote.ContadorResponse
import com.example.electricapp.data.remote.CrearInstrumentoRequest
import com.example.electricapp.data.remote.InstrumentoApi
import com.example.electricapp.data.remote.InstrumentoResponse
import com.example.electricapp.data.remote.RetrofitClient
import com.example.electricapp.data.remote.VigenciaResponse
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class InstrumentoRepository (
    private val instrumentoApi : InstrumentoApi = RetrofitClient.instrumentoApi
){
    private suspend fun <T> llamar(peticion: suspend () -> T) : T = try{
        peticion()
    }catch (e : HttpException){
        throw Exception(mensajeDelServidor(e))
    }catch (e: IOException){
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

    suspend fun RegistrarInstrumento(datos : CrearInstrumentoRequest) : InstrumentoResponse = llamar {
        instrumentoApi.crearInstrumentos(datos)
    }

    suspend fun CargarInstrumentos() : List<InstrumentoResponse> = llamar {
        instrumentoApi.listarInstrumentos()
    }

    suspend fun contarInstrumentos() : ContadorResponse = llamar {
        instrumentoApi.contador()
    }

    suspend fun consultarVigencia(id : Long) : VigenciaResponse = llamar {
        instrumentoApi.vigencia(id)
    }

    /*
        falta actualizar (falta hacerlo en el backend)
    */
}


