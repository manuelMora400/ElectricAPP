package com.example.electricapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.styles.EstilosBotones
import com.example.electricapp.ui.theme.ElectricAppTheme


@Composable
fun BotonCerrarSesion(

    texto : String,
    onClick : () -> Unit,
    @DrawableRes icono : Int? = null,
    modifier: Modifier = Modifier
){

    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = true,
        shape = EstilosBotones.formaBoton,
        colors = EstilosBotones.ColorPrincipal(),
        elevation = EstilosBotones.elevacion(),
        contentPadding = EstilosBotones.rellenar
    ) {

        if(icono != null){
            Icon(
                painter = painterResource(R.drawable.ic_cerrar_sesion),
                contentDescription = null,
                modifier = modifier.size(ButtonDefaults.IconSize)
            )
        }
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBotonCerrarSesion(){

    ElectricAppTheme(darkTheme = true) {
        BotonCerrarSesion(
            "Cerrar Sesion",
            onClick = {},
            R.drawable.ic_cerrar_sesion,
            Modifier.padding(20.dp)
        )
    }
}