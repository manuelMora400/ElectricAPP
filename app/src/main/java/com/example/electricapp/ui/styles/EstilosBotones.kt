package com.example.electricapp.ui.styles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.electricapp.ui.theme.BotonGestiones
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.FondoClaro
import com.example.electricapp.ui.theme.GrisDeshabilitado
import com.example.electricapp.ui.theme.TextoOscuro


object EstilosBotones{

    val formaBoton = RoundedCornerShape(Dimens.radioBoton)
    val rellenar = PaddingValues(horizontal = 10.dp, vertical = 5.dp)

    // Boton Inicio Sesion
    @Composable
    fun ColorPrincipal() : ButtonColors = buttonColors(

        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = TextoOscuro,
        disabledContainerColor = TextoOscuro,
        disabledContentColor = FondoClaro

    )

    @Composable
    fun ColorBotonGestiones() : ButtonColors = buttonColors(
        containerColor = BotonGestiones,
        contentColor = TextoOscuro,
        disabledContentColor = GrisDeshabilitado,
        disabledContainerColor = FondoClaro
    )
    @Composable
    fun elevacion(): ButtonElevation = ButtonDefaults.buttonElevation(
        defaultElevation = Dimens.elevation,
        pressedElevation = 9.dp
    )
    @Composable
    fun bordeBoton() : BorderStroke = BorderStroke(Dimens.radioBoton, MaterialTheme.colorScheme.primary)

    @Composable
    fun ColoresSecundarios() : ButtonColors = buttonColors(
        contentColor = MaterialTheme.colorScheme.primary
    )
}
fun Modifier.estiloAltoBoton() : Modifier = this.height(Dimens.alturaBoton).fillMaxWidth()