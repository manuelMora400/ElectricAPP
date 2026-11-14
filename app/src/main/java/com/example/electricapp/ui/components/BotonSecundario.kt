package com.example.electricapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.ui.styles.EstilosBotones
import com.example.electricapp.ui.styles.estiloAltoBoton
import com.example.electricapp.ui.theme.ElectricAppTheme

// Boton secundario para botones menos importante (como limpiar) etc
@Composable
fun BotonSecundario(
    texto : String,
    onClick : () -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.estiloAltoBoton(),
        shape = EstilosBotones.formaBoton,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = texto, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun BotonSecundarioView(){
    ElectricAppTheme(false) {
        Column(Modifier.padding(16.dp)) {
            BotonSecundario("Limpiar", {})
        }
    }
}
