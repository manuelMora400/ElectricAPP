package com.example.electricapp.ui.styles

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.electricapp.ui.theme.Blanco
import com.example.electricapp.ui.theme.Primario
import com.example.electricapp.ui.theme.PrimarioOscuro

object EstilosBarra {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun colores(marino: Boolean = false): TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = if (marino) PrimarioOscuro else Primario,
        titleContentColor = Blanco,
        navigationIconContentColor = Blanco,
        actionIconContentColor = Blanco
    )
}
