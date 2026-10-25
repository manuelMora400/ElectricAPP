package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.model.Estado
import com.example.electricapp.model.HistorialState
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun ProtocoloCards(
    protocolo: HistorialState,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(1f).padding(Dimens.espacioMed)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(17.dp)
            ) {
                Text(
                    text = protocolo.fechaPrueba,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Tag: ${protocolo.tagBuscar}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Instalación: ${protocolo.instalacion}",
                    style = MaterialTheme.typography.titleMedium
                )
                etiqueta(
                    text =
                        when (protocolo.estado) {
                            Estado.APROBADO -> (R.string.Consulta_Aprobado)
                            Estado.OBSERVACION -> (R.string.Consulta_Observacion)
                            else -> (R.string.Consulta_Rechazado)
                        },
                    state = protocolo.estado

                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ProtocoloCardsView(){
    ElectricAppTheme(true) {
        ProtocoloCards(
            protocolo = HistorialState(

                "TR-10",
                "Andinas S.A",
            ),
        )
    }
}