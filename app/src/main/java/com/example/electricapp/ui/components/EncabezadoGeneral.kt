package com.example.electricapp.ui.components


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Blanco
import com.example.electricapp.ui.theme.Dimens

import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Primario

@Composable
fun EncabezadoVistaJefe(
    titulo : String,
    subTitulo : String,
    @DrawableRes icono : Int,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxWidth().background(Primario).padding(Dimens.padEncabezado),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Box(
            Modifier.size(72.dp).clip(CircleShape).background(Color.White),
            contentAlignment = Alignment.Center
            ){
            Image(
                painter = painterResource(icono),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            color = Blanco
        )
        Text(
            text = subTitulo,
            style = MaterialTheme.typography.titleMedium,
            color = Blanco
        )
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

