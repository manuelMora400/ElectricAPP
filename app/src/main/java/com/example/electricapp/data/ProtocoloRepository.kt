package com.example.electricapp.data

import com.example.electricapp.data.remote.AislacionResponse
import com.example.electricapp.data.remote.CambiarEstadoProtocoloRequest
import com.example.electricapp.data.remote.CrearAislacionRequest
import com.example.electricapp.data.remote.CrearProtocoloRequest
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

class ProtocoloRepository(
    private val api: ProtocoloApi = RetrofitClient.protocoloApi
) {
    suspend fun buscarHistorial(tag: String, instalacion: String): List<HistorialState> = llamar {
        val aislaciones = api.filtrarAislaciones(
            tag = tag.ifBlank { null },
            instalacion = instalacion.ifBlank { null }
        )

        val porProtocolo = aislaciones
            .filter { it.idProtocolo != null }
            .distinctBy { it.idProtocolo }
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

    suspend fun obtenerDetalle(idProtocolo: Long): DetallesState = llamar {
        val (protocolo, aislaciones) = coroutineScope {
            val cabecera = async { api.obtenerProtocolo(idProtocolo) }
            val pruebas = async { api.aislacionesDeProtocolo(idProtocolo) }
            cabecera.await() to pruebas.await()
        }

        if (aislaciones.isEmpty()) {
            throw Exception("El protocolo $idProtocolo no tiene pruebas de aislación registradas")
        }

        val todos = aislaciones.map { aDetalle(it, protocolo) }
        todos.first().copy(listaDetalles = todos)
    }

    private fun aHistorial(a: AislacionResponse, p: ProtocoloResponse) = HistorialState(
        idProtocolo = p.idProtocolo,
        tagBuscar = a.tag.orEmpty(),
        instalacion = a.instalacion.orEmpty(),
        fechaPrueba = formatearFecha(p.fechaPrueba),
        estado = when (p.resultadoPrueba) {
            "APROBADO" -> Estado.APROBADO
            "RECHAZADO" -> Estado.RECHAZADO
            else -> Estado.OBSERVACION
        },
        formalizado = esFormalizado(p.estadoProtocolo)
    )

    private fun aDetalle(a: AislacionResponse, p: ProtocoloResponse): DetallesState {
        val formalizado = esFormalizado(p.estadoProtocolo)
        return DetallesState(
            idProtocolo = p.idProtocolo,
            Tag = a.tag.orEmpty(),
            Instalacion = a.instalacion.orEmpty(),
            medicion1Min = formatearNumero(a.lectura1Min),
            medicion10Min = formatearNumero(a.lectura10Min),
            devanado = a.enumDevanado.orEmpty().replace('_', '-'),
            tensionPrueba = formatearNumero(a.tension),
            resultado = when (p.resultadoPrueba) {
                "APROBADO" -> "Aprobado"
                "RECHAZADO" -> "Rechazado"
                "OBSERVACION" -> "Observado"
                else -> ""
            },
            estado = if (formalizado) "Formalizado" else "Pendiente de formalización",
            nombreIngeniero = if (formalizado) "No disponible" else null,
            fechaPrueba = formatearFecha(p.fechaPrueba),
            latitud = p.latitud,
            longitud = p.longitud
        )
    }

    private fun esFormalizado(estadoServidor: String?) =
        estadoServidor == "FORMALIZADO"

    private fun formatearFecha(iso: String?): String {
        if (iso.isNullOrBlank()) return ""
        val m = Regex("""(\d{4})-(\d{2})-(\d{2})[T ](\d{2}):(\d{2})""").find(iso) ?: return iso
        val (anio, mes, dia, hora, min) = m.destructured
        return "$dia/$mes/$anio $hora:$min"
    }

    private fun formatearNumero(valor: Double?): String = when {
        valor == null -> "0"
        valor % 1.0 == 0.0 -> valor.toLong().toString()
        else -> valor.toString()
    }

    private suspend fun <T> llamar(peticion: suspend () -> T): T = try {
        peticion()
    } catch (e: HttpException) {
        throw Exception(mensajeDelServidor(e))
    } catch (e: IOException) {
        throw Exception("No se pudo conectar con el servidor")
    }

    private fun mensajeDelServidor(e: HttpException): String {
        if (e.code() == 404) return "No se encontró el protocolo solicitado"
        val cuerpo = e.response()?.errorBody()?.string()
        val mensaje = try {
            JSONObject(cuerpo ?: "").optString("error")
        } catch (ex: Exception) {
            ""
        }
        return mensaje.ifBlank { "Error del servidor (${e.code()})" }
    }

    suspend fun formalizar(idProtocolo: Long): ProtocoloResponse = llamar {
        api.cambiarEstado(idProtocolo, CambiarEstadoProtocoloRequest("FORMALIZADO"))
    }

    suspend fun listarProtocolos(): List<ProtocoloResponse> = llamar {
        api.listarProtocolos()
    }

    suspend fun protocolosDelTecnico(idTecnico: Long): List<ProtocoloResponse> = llamar {
        api.protocolosDelTecnico(idTecnico)
    }

    suspend fun registrarProtocolo(datos: CrearProtocoloRequest): ProtocoloResponse = llamar {
        api.crearProtocolo(datos)
    }

    suspend fun registrarAislacion(datos: CrearAislacionRequest): AislacionResponse = llamar {
        api.crearAislacion(datos)
    }

    suspend fun listarDevanados(): List<String> = llamar {
        api.listarDevanados()
    }
}