package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme


// Sirve igual para los paneles del Técnico, Ingeniero y Cliente
@Composable
fun TarjetaSaludo(
    titulo : String,
    subtitulo : String,
    rol : String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = EstilosTarjetas.formaPanel,
        colors = EstilosTarjetas.coloresPanel(),
        border = EstilosTarjetas.bordeSuave(),
        elevation = EstilosTarjetas.sinElevacion()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(Dimens.padTarjetaPanel),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioChico)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            etiquetaRol(
                texto = rol,
                colorFondo = MaterialTheme.colorScheme.primaryContainer,
                colorTexto = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(top = Dimens.espacioMed / 2)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaSaludoView(){
    ElectricAppTheme(false) {
        TarjetaSaludo("Bienvenido, Jefe Tecnico", "Menú principal, seleccione una opcion:", "Jefe tecnico")
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaSaludoViewOscuro(){
    ElectricAppTheme(true) {
        TarjetaSaludo("Bienvenido, Jefe Tecnico", "Menu principal, seleccione una opcion:", "Jefe tecnico")
    }
}
