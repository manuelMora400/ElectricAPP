package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme


@Composable
fun Etiqueta(
    texto : Int,
    activo : Boolean,

){
    val colorFondo =
        if(activo){
             Color.Green
        }else
             Color.Red

    Surface(
        shape = EstilosTarjetas.forma,
        color = colorFondo,
        modifier = Modifier.padding(Dimens.espacioChico)
    ) {
        Text(
            text = stringResource(texto),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
@Preview(showBackground = true)
@Composable
fun EtiquetaView(){
    ElectricAppTheme(false) {
        Etiqueta(
            R.string.desactivar_cuenta,
            false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EtiquetaView2(){
    ElectricAppTheme(false) {
        Etiqueta(
            R.string.activar_cuenta,
            true
        )
    }
}