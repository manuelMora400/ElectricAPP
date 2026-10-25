package com.example.electricapp.model

import androidx.annotation.StringRes
import com.example.electricapp.R

enum class Estado(@StringRes val texto : Int) {
    APROBADO (R.string.Consulta_Aprobado),
    OBSERVACION (R.string.Consulta_Aprobado),
    RECHAZADO (R.string.Consulta_Aprobado),
    ACTUALIZANDO(R.string.Consulta_Remplazado)
}
data class ProtocoloItem(
    val fecha: String,
    val codigo: String,
    val estado: Estado,
    val detalleReemplazo: String? = null
)