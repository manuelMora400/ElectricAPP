package com.example.electricapp.ui.components


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens

import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun EncabezadoVistaJefe(
    titulo : String,
    subTitulo : String,
    @DrawableRes icono : Int,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = EstilosTarjetas.colores()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Dimens.rowEspacio),
            horizontalArrangement = Arrangement.spacedBy(Dimens.espacioLogoJefe),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icono),
                contentDescription = null,
                modifier = Modifier
                            .align(Alignment.Top)
                            .size(48.dp)
                            .shadow(Dimens.elevation, shape = CircleShape)
                            .clip(CircleShape)
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = subTitulo,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EncabezadoView(){
    ElectricAppTheme(darkTheme = true) {
        EncabezadoVistaJefe(
            "INDUZTEK",
            "Dedicada a ingenieria electrica",
            R.drawable.logo_inicio_sesion
        )
    }
}
@Preview(showBackground = true)
@Composable
fun EncabezadoView2(){
    ElectricAppTheme(darkTheme = false) {
        EncabezadoVistaJefe(
            "INDUZTEK",
            "Dedicada a ingenieria electrica",
            R.drawable.logo_inicio_sesion
        )
    }
}

