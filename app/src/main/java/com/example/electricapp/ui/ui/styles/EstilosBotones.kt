package com.example.electricapp.ui.ui.styles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.electricapp.ui.ui.theme.BotonNoHabilitado
import com.example.electricapp.ui.ui.theme.ColorPrimarioOscuro
import com.example.electricapp.ui.ui.theme.Dimens
import com.example.electricapp.ui.ui.theme.FondoClaro
import com.example.electricapp.ui.ui.theme.colorPrimario
import com.example.electricapp.ui.ui.theme.textoBotonInicio
import com.example.electricapp.ui.ui.theme.textoDesabilitado

object EstilosBotones{

    val formaBoton = RoundedCornerShape(Dimens.radioBoton)
    val rellenar = PaddingValues(horizontal = 20.dp, vertical = 15.dp)

    // Boton Inicio Sesion
    @Composable
    fun ColorPrincipal() : ButtonColors = buttonColors(

        containerColor = colorPrimario,
        contentColor = textoBotonInicio,
        disabledContainerColor = textoDesabilitado,
        disabledContentColor = FondoClaro

    )
    @Composable
    fun elevacion(): ButtonElevation = ButtonDefaults.buttonElevation(
        defaultElevation = Dimens.elevation,
        pressedElevation = 4.dp
    )
    @Composable
    fun bordeBoton() : BorderStroke = BorderStroke(Dimens.radioBoton, MaterialTheme.colorScheme.primary)

    @Composable
    fun ColoresSecundarios() : ButtonColors = buttonColors(
        contentColor = MaterialTheme.colorScheme.primary
    )
}
fun Modifier.estiloAltoBoton() : Modifier = this.height(Dimens.alturaBoton)