package com.example.electricapp.ui.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.ui.styles.EstilosBotones
import com.example.electricapp.ui.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.ui.theme.Dimens
import com.example.electricapp.ui.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.ui.theme.TextoOscuro

@Composable
fun TarjetaOpciones(

    titulo : String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
){

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = EstilosTarjetas.forma,
        colors = EstilosTarjetas.colores(),
        elevation = EstilosTarjetas.elevacion()
    ) {
        Column(
            modifier = Modifier.padding(Dimens.espacioMed),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleLarge,
                color = TextoOscuro,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Button(
                onClick = onClick,
                modifier = modifier.fillMaxWidth(),
                shape = EstilosBotones.formaBoton,
                colors = EstilosBotones.ColorBotonGestiones(),
                elevation = EstilosBotones.elevacion()
            ) {
                Text(stringResource(R.string.btn_gestion_usuarios))
            }
        }
            Button(
                onClick = onClick,
                modifier = modifier.fillMaxWidth(),
                shape = EstilosBotones.formaBoton,
                colors = EstilosBotones.ColorBotonGestiones(),
                elevation = EstilosBotones.elevacion()
            ) {
                Text(stringResource(R.string.btn_historial_equipo))
            }

           Button(
                onClick = onClick,
                modifier = modifier.fillMaxWidth(),
                shape = EstilosBotones.formaBoton,
                colors = EstilosBotones.ColorBotonGestiones(),
                elevation = EstilosBotones.elevacion()
        ) {
            Text(stringResource(R.string.btn_registrar_instrumentos))
        }
        }
    }



@Preview(showBackground = true)
@Composable
fun viewCardOpciones(){

    ElectricAppTheme {
        Column {
            TarjetaOpciones("Gestione con precaucion", Modifier.padding(20.dp), onClick = {})
        }

    }
}