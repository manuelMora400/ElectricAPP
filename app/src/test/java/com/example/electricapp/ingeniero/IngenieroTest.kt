package com.example.electricapp.ingeniero

import com.example.electricapp.model.ContadoresPanelIngeniero
import com.example.electricapp.model.ProtocoloRevision
import com.example.electricapp.model.ResultadoPrueba
import com.example.electricapp.model.RevisarProtocoloState
import com.example.electricapp.viewModel.RevisarProtocoloViewModel
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class IngenieroTest {

    private lateinit var estado : RevisarProtocoloState
    private lateinit var protocolo : ProtocoloRevision
    private lateinit var viewModel : RevisarProtocoloViewModel

    @Before
    fun preparar() {
        viewModel = RevisarProtocoloViewModel()
        estado = RevisarProtocoloState()
        protocolo = ProtocoloRevision(
            idProtocolo = 4,
            tag = "TR-02",
            instalacion = "Planta Lampa",
            resultado = "APROBADO",
            estado = "REGISTRADO_TERRENO"
        )
    }

    @Test
    fun contadoresPanel_sinDatos_esperadoEnCero() {
        val contadores = ContadoresPanelIngeniero()

        assertEquals(0, contadores.porRevisar)
        assertEquals(0, contadores.enObservacion)
        assertEquals(0, contadores.formalizados)
    }

    @Test
    fun contadoresPanel_conValores_esperadoGuardarValores() {
        val contadores = ContadoresPanelIngeniero(porRevisar = 2, enObservacion = 1, formalizados = 3)

        assertEquals(2, contadores.porRevisar)
        assertEquals(1, contadores.enObservacion)
        assertEquals(3, contadores.formalizados)
    }

    @Test
    fun revisarProtocolo_alIniciar_esperadoSinProtocolo() {

        val estado = viewModel.uistate.value

        assertNull(estado.protocolo)
    }

    @Test
    fun onPedirFormalizar_abrirDialogo_esperadoMostrarConfirmacion() {

        viewModel.onPedirFormalizar()
        val estado = viewModel.uistate.value

        assertTrue(estado.mostrarConfirmacion)
    }

    @Test
    fun cargarProtocolo_guardaElProtocolo() {
        val resultado = estado.copy(protocolo = protocolo)

        assertEquals(4, resultado.protocolo?.idProtocolo)
        assertEquals("TR-02", resultado.protocolo?.tag)
    }

    @Test
    fun confirmarFormalizar_quedaFormalizado() {
        val conDialogo = estado.copy(mostrarConfirmacion = true)

        val resultado = conDialogo.copy(mostrarConfirmacion = false, formalizado = true)

        assertFalse(resultado.mostrarConfirmacion)
        assertTrue(resultado.formalizado)
    }

    @Test
    fun resultadoAprobado_seTraduceConElEnum() {
        val resultado = ResultadoPrueba.entries.find { it.name == protocolo.resultado }

        assertEquals(ResultadoPrueba.APROBADO, resultado)
    }

}
