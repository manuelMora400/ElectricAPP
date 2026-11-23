package com.example.electricapp

import com.example.electricapp.model.ContadoresPanelIngeniero
import com.example.electricapp.model.ProtocoloRevision
import com.example.electricapp.model.RevisarProtocoloState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IngenieroTest {

    private lateinit var estado : RevisarProtocoloState
    private lateinit var protocolo : ProtocoloRevision

    @Before
    fun preparar() {
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
    fun contadoresPanel_inicianEnCero() {
        val contadores = ContadoresPanelIngeniero()

        assertEquals(0, contadores.porRevisar)
        assertEquals(0, contadores.enObservacion)
        assertEquals(0, contadores.formalizados)
    }

    @Test
    fun contadoresPanel_guardanLosValores() {
        val contadores = ContadoresPanelIngeniero(porRevisar = 2, enObservacion = 1, formalizados = 3)

        assertEquals(2, contadores.porRevisar)
        assertEquals(1, contadores.enObservacion)
        assertEquals(3, contadores.formalizados)
    }
}
