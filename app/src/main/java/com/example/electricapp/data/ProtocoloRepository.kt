package com.example.electricapp.data.remote

import com.example.electricapp.data.remote.AislacionResponse
import com.example.electricapp.data.remote.ProtocoloApi
import com.example.electricapp.data.remote.ProtocoloResponse
import com.example.electricapp.data.remote.RetrofitClient
import com.example.electricapp.model.DetallesState
import com.example.electricapp.model.Estado
import com.example.electricapp.model.HistorialState
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class ProtocoloRepository (
    private val api: ProtocoloApi = RetrofitClient.protocoloApi
){
    suspend fun buscarHistorial(tag: String, instalacion: String): List<HistorialState> = llamar {

        val aislaciones = api.filtrarAislaciones(
            tag = tag.ifBlank { null },
            instalacion = instalacion.ifBlank { null }
        )
        coroutineScope {
            porProtocolo.map { aislacion ->
                async {
                    val protocolo = try {
                        api.obtenerProtocolo(aislacion.idProtocolo!!)
                    } catch (e: HttpException) {
                        if (e.code() == 404) null else throw e
                    }
                    protocolo?.let { aHistorial(aislacion, it) }
                }
            }.awaitAll().filterNotNull()
        }
    }
}