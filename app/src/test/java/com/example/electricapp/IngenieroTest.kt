package com.example.electricapp

import com.example.electricapp.model.ProtocoloRevision
import com.example.electricapp.model.RevisarProtocoloState
import org.junit.Before

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
}
