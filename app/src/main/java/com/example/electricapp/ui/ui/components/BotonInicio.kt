package com.example.electricapp.ui.ui.components

import androidx.compose.material3.Button
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.ui.ui.styles.EstilosBotones
import com.example.electricapp.ui.ui.styles.estiloAltoBoton
import com.example.electricapp.ui.ui.theme.Blanco
import com.example.electricapp.ui.ui.theme.TextoOscuro

@Composable
fun BotonInicio(

    texto : String,
    onClick : () -> Unit,
    modifier: Modifier = Modifier,
    siHabilitado : Boolean = true,
    @DrawableRes icono : Int? = null
){

    Button(

        onClick = onClick,
        enabled = siHabilitado,
        modifier = Modifier.estiloAltoBoton(),
        shape = EstilosBotones.formaBoton,
        colors = EstilosBotones.ColorPrincipal(),
        elevation = EstilosBotones.elevacion(),
        contentPadding = EstilosBotones.rellenar

    ){
        if(icono != null){
            Icon(
                painter = painterResource(id = icono),
                contentDescription = null,
                modifier = modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier.size(ButtonDefaults.IconSpacing))
        }
        Text(text = texto, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Preview (showBackground = true)
@Composable
private fun viewBoton(){
    Column {
        BotonInicio("Inicie Sesion", {}, modifier = Modifier.fillMaxWidth())
        BotonInicio("Desabiliao", {}, siHabilitado = false, modifier = Modifier.fillMaxWidth())
    }
}