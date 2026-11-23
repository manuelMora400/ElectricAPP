package com.example.electricapp.model

data class RevisarProtocoloState(
    val protocolo : ProtocoloRevision? = null,
    val mostrarConfirmacion : Boolean = false,
    val formalizado : Boolean = false
)
