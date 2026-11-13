package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun etiquetaRol(
    texto : String,
    colorFondo : Color,
    colorTexto : Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = colorFondo
    ) {
        Text(
            text =  texto,
            style = MaterialTheme.typography.bodyMedium,
            color = colorTexto,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Preview
@Composable
fun viewEtiquetaRol(){
    ElectricAppTheme(false) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)

        ) {
            etiquetaRol(
                texto = "Tecnico",
                colorFondo = MaterialTheme.colorScheme.primary,
                colorTexto = MaterialTheme.colorScheme.background
            )
            etiquetaRol(
                texto = "Desactivada",
                colorFondo = MaterialTheme.colorScheme.errorContainer,
                colorTexto = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}