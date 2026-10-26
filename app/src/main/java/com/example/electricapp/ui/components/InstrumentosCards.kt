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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.R
import com.example.electricapp.model.FormularioInstrumentosStates
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun InstrumentosCards(
    instrumento : FormularioInstrumentosStates,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(1f).padding(Dimens.espacioMed)
    ) {
        Column(
            modifier = Modifier.padding(Dimens.espacioMed),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioChico)
        ) {
            Text(
                text = instrumento.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${instrumento.marca} ${instrumento.modelo}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.instrumento_serie, instrumento.numSerie),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.instrumento_vence, instrumento.fechaVencimiento),
                style = MaterialTheme.typography.bodyMedium
            )

            Etiqueta(
                texto = if(instrumento.vencido) R.string.instrumento_vencido else R.string.instrumento_vigente,
                activo = !instrumento.vencido
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InstrumentosCardsView(){
    ElectricAppTheme(true) {
        InstrumentosCards(
            instrumento = FormularioInstrumentosStates(
                nombre = "Megóhmetro 10 kV",
                marca = "Megger",
                modelo = "MIT1025",
                numSerie = "MIT-0071233",
                fechaVencimiento = "10/10/2026",
                vencido = true
            )
        )
    }
}
