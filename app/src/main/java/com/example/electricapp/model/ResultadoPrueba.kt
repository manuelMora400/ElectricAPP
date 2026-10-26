package com.example.electricapp.model

import androidx.annotation.StringRes
import com.example.electricapp.R

enum class ResultadoPrueba(@StringRes val texto : Int){
    APROBADO(R.string.resultado_aprobado),
    OBSERVADO(R.string.resultado_observado),
    RECHAZADO(R.string.resultado_rechazado)
}
