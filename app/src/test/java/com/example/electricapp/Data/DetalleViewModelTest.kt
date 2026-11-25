package com.example.electricapp.Data

import com.example.electricapp.data.ProtocoloRepository
import com.example.electricapp.viewModel.DetalleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DetalleViewModelTest {
    private lateinit var apiFalsa: FakeProtocoloApi
    private lateinit var viewModel: DetalleViewModel

    @Before
    fun preparar() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        apiFalsa = FakeProtocoloApi()
        viewModel = DetalleViewModel(ProtocoloRepository(api = apiFalsa))
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    @Test
    fun estadoInicial_alCrearElViewModel_esperadoCargandoSinError() {
        val estado = viewModel.uiState.value

        assertTrue(estado.cargando)
        assertNull(estado.error)
    }

    @Test
    fun cargarDetalle_protocoloExistente_esperadoDatosYSinCargando() {
        viewModel.cargarDetalle(1L)

        val estado = viewModel.uiState.value
        assertFalse(estado.cargando)
        assertNull(estado.error)
        assertEquals(1L, estado.idProtocolo)
        assertEquals("TR-01", estado.Tag)
        assertEquals("Formalizado", estado.estado)
        assertEquals(2, estado.listaDetalles.size)
    }

    @Test
    fun cargarDetalle_protocoloInexistente_esperadoMensajeDeError() {
        viewModel.cargarDetalle(99L)

        val estado = viewModel.uiState.value
        assertFalse(estado.cargando)
        assertEquals("No se encontró el protocolo solicitado", estado.error)
        assertEquals(99L, estado.idProtocolo)
    }

    @Test
    fun cargarDetalle_mismoIdDosVeces_esperadoLlamarALaApiUnaSolaVez() {
        viewModel.cargarDetalle(1L)
        viewModel.cargarDetalle(1L)

        assertEquals(1, apiFalsa.llamadasObtenerProtocolo)
    }

    @Test
    fun cargarDetalle_sinConexion_esperadoMensajeAmigable() {
        apiFalsa.sinConexion = true

        viewModel.cargarDetalle(1L)

        val estado = viewModel.uiState.value
        assertEquals("No se pudo conectar con el servidor", estado.error)
        assertFalse(estado.cargando)
    }
}