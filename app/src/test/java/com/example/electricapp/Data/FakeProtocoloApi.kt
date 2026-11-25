package com.example.electricapp.Data

import com.example.electricapp.data.remote.ProtocoloApi
import com.example.electricapp.data.remote.AislacionResponse
import com.example.electricapp.data.remote.CambiarEstadoProtocoloRequest
import com.example.electricapp.data.remote.CrearAislacionRequest
import com.example.electricapp.data.remote.CrearProtocoloRequest
import com.example.electricapp.data.remote.ProtocoloResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class FakeProtocoloApi: ProtocoloApi {
    private val protocolos = listOf(
        ProtocoloResponse(
            idProtocolo = 1L, fechaPrueba = "2026-11-20T10:30:00",
            temperatura = 22.5, humedad = 55.0, latitud = -33.4489, longitud = -70.6693,
            estadoProtocolo = "FORMALIZADO", resultadoPrueba = "APROBADO"
        ),
        ProtocoloResponse(
            idProtocolo = 2L, fechaPrueba = "2026-11-21T09:05:00",
            temperatura = 20.0, humedad = 60.0, latitud = -33.30, longitud = -70.80,
            estadoProtocolo = "PENDIENTE", resultadoPrueba = "RECHAZADO"
        ),
        ProtocoloResponse(
            idProtocolo = 3L, fechaPrueba = "2026-11-22T15:45:00",
            temperatura = 19.0, humedad = 48.0, latitud = null, longitud = null,
            estadoProtocolo = "PENDIENTE", resultadoPrueba = null
        ),
        ProtocoloResponse(
            idProtocolo = 4L, fechaPrueba = "2026-11-23T08:00:00",
            temperatura = 21.0, humedad = 50.0, latitud = null, longitud = null,
            estadoProtocolo = "PENDIENTE", resultadoPrueba = "APROBADO"
        )
    )

    private val aislaciones = listOf(
        AislacionResponse(1L, "TR-01", "Planta Lampa", "AT_BT", 5.0, 1200.0, 2400.0, 1L),
        AislacionResponse(2L, "TR-01", "Planta Lampa", "BT_T", 2.5, 1500.5, 3000.0, 1L),
        AislacionResponse(3L, "TR-01", "Planta Lampa", "AT_T", 5.0, 900.0, 1800.0, 2L),
        AislacionResponse(4L, "TR-02", "Planta Lampa", "AT_BT", 5.0, 800.0, 1600.0, null),
        AislacionResponse(5L, "TR-A", "Planta Elevadora Sur", "BT_T", 2.5, 1000.0, 2000.0, 3L)
    )

    var sinConexion = false

    val idsInexistentes = mutableSetOf<Long>()

    var ultimoTag: String? = "sin llamar"
    var ultimaInstalacion: String? = "sin llamar"

    var llamadasObtenerProtocolo = 0

    private fun noEncontrado() =
        HttpException(Response.error<Any>(404, "".toResponseBody(null)))

    override suspend fun filtrarAislaciones(tag: String?, instalacion: String?): List<AislacionResponse> {
        if (sinConexion) throw IOException("Servidor apagado")
        ultimoTag = tag
        ultimaInstalacion = instalacion
        return aislaciones.filter {
            (tag == null || it.tag.orEmpty().contains(tag, ignoreCase = true)) &&
                    (instalacion == null || it.instalacion.orEmpty().contains(instalacion, ignoreCase = true))
        }
    }

    override suspend fun aislacionesDeProtocolo(idProtocolo: Long): List<AislacionResponse> {
        if (sinConexion) throw IOException("Servidor apagado")
        return aislaciones.filter { it.idProtocolo == idProtocolo }
    }

    override suspend fun obtenerProtocolo(id: Long): ProtocoloResponse {
        if (sinConexion) throw IOException("Servidor apagado")
        llamadasObtenerProtocolo++
        if (id in idsInexistentes) throw noEncontrado()
        return protocolos.firstOrNull { it.idProtocolo == id } ?: throw noEncontrado()
    }

    override suspend fun cambiarEstado(id: Long, request: CambiarEstadoProtocoloRequest): ProtocoloResponse = TODO()
    override suspend fun listarProtocolos(): List<ProtocoloResponse> = TODO()
    override suspend fun protocolosDelTecnico(idTecnico: Long): List<ProtocoloResponse> = TODO()
    override suspend fun crearProtocolo(datos: CrearProtocoloRequest): ProtocoloResponse = TODO()
    override suspend fun crearAislacion(datos: CrearAislacionRequest): AislacionResponse = TODO()
    override suspend fun listarDevanados(): List<String> = TODO()
}

