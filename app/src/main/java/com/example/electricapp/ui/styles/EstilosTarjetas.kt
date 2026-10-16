package com.example.electricapp.ui.styles

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.electricapp.ui.theme.ColorCard
import com.example.electricapp.ui.theme.Dimens

object EstilosTarjetas{

    val forma = RoundedCornerShape(Dimens.radioTarjeta)

    @Composable
    fun colores() : CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    )
    @Composable
    fun elevacion() : CardElevation = CardDefaults.cardElevation(
        defaultElevation = Dimens.elevacionTarjeta
    )
}