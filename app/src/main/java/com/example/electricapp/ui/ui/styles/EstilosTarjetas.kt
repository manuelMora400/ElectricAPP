package com.example.electricapp.ui.ui.styles

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import com.example.electricapp.ui.ui.theme.BotonGestiones
import com.example.electricapp.ui.ui.theme.ColorCard
import com.example.electricapp.ui.ui.theme.Dimens

object EstilosTarjetas{

    val forma = RoundedCornerShape(Dimens.radioTarjeta)

    @Composable
    fun colores() : CardColors = CardDefaults.cardColors(
        contentColor = ColorCard
    )
    @Composable
    fun elevacion() : CardElevation = CardDefaults.cardElevation(
        defaultElevation = Dimens.elevacionTarjeta
    )
}