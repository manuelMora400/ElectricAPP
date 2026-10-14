package com.example.electricapp.ui.components

import androidx.compose.foundation.Image
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.theme.Blanco
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme


@Composable
fun ImagenEncabezadoLogin(
    @DrawableRes imagen : Int,
    titulo : String,
    modifier: Modifier = Modifier
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.altoImagen)
    ){
        Image(
            painter = painterResource(id = imagen),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(text = titulo, style = MaterialTheme.typography.titleLarge, color = Blanco)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImagenEncabezadoPreview(){
    ElectricAppTheme {
        ImagenEncabezadoLogin(
            imagen = R.drawable.imagen_encabezado_login,
            titulo = "Inicio Sesion",
            modifier = Modifier.size(400.dp)
        )
    }
}