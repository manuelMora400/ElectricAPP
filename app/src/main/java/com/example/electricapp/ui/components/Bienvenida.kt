package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun Bienvenida(
    texto : String,
    descripcion : String
){
    Column(
        modifier = Modifier.fillMaxWidth().padding(Dimens.espacioTitulo)
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = descripcion,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
@Preview(showBackground = true)
@Composable
fun BienvenidaView(){

    ElectricAppTheme(false) {
        Bienvenida(
            "Bienvenido Jefe Tecnico",
            "Menu principal - Seleccione una opcion"
        )
    }
}
@Preview(showBackground = true)
@Composable
fun BienvenidaView2(){

    ElectricAppTheme(true) {
        Bienvenida(
            "Bienvenido Jefe Tecnico",
            "Menu principal - Seleccione una opcion"
        )
    }
}