package com.example.electricapp.model

import androidx.annotation.StringRes
import com.example.electricapp.R

enum class Estado(@StringRes val texto : Int) {
    APROBADO (R.string.Consulta_Aprobado),
    OBSERVACION (R.string.Consulta_Observacion),
    RECHAZADO (R.string.Consulta_Rechazado)
}