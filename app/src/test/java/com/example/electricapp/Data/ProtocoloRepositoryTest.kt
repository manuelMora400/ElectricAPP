package com.example.electricapp.Data

import com.example.electricapp.data.ProtocoloRepository
import com.example.electricapp.model.Estado
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.junit.Assert.assertThrows

class ProtocoloRepositoryTest {
    private lateinit var repositorio: ProtocoloRepository
    private lateinit var apiFalsa: FakeProtocoloApi

    @Before
    fun preparar() {
        apiFalsa = FakeProtocoloApi()
        repositorio = ProtocoloRepository(api = apiFalsa)
    }

    @Test
    fun buscarHistorial_protocoloFormalizadoAprobado_esperadoConvertirEnHistorial() = runBlocking {
        val lista = repositorio.buscarHistorial("TR-01", "Planta Lampa")

        val protocolo1 = lista.first { it.idProtocolo == 1L }
        assertEquals("TR-01", protocolo1.tagBuscar)
        assertEquals("Planta Lampa", protocolo1.instalacion)
        assertEquals("20/11/2026 10:30", protocolo1.fechaPrueba)
        assertEquals(Estado.APROBADO, protocolo1.estado)
        assertTrue(protocolo1.formalizado)
    }

    @Test
    fun buscarHistorial_dosPruebasDelMismoProtocolo_esperadoUnSoloProtocolo() = runBlocking {
        val lista = repositorio.buscarHistorial("TR-01", "Planta Lampa")

        assertEquals(2, lista.size)
        assertEquals(listOf(1L, 2L), lista.map { it.idProtocolo })
    }

    @Test
    fun buscarHistorial_textosEnBlanco_esperadoEnviarNullALaApi() = runBlocking {
        val lista = repositorio.buscarHistorial("", "   ")

        assertNull(apiFalsa.ultimoTag)
        assertNull(apiFalsa.ultimaInstalacion)
        assertEquals(3, lista.size)
    }

    @Test
    fun buscarHistorial_protocoloDa404_esperadoOmitirloSinFallar() = runBlocking {
        apiFalsa.idsInexistentes.add(2L)

        val lista = repositorio.buscarHistorial("TR-01", "Planta Lampa")

        assertEquals(1, lista.size)
        assertEquals(1L, lista[0].idProtocolo)
    }

    @Test
    fun buscarHistorial_sinConexion_esperadoMensajeAmigable() {
        apiFalsa.sinConexion = true

        val error = assertThrows(Exception::class.java) {
            runBlocking { repositorio.buscarHistorial("TR-01", "Planta Lampa") }
        }

        assertEquals("No se pudo conectar con el servidor", error.message)
    }
}