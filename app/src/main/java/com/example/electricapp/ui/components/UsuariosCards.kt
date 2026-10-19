package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.model.Rol
import com.example.electricapp.model.UsuarioEjemplo
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun UsuariosCards(
    usuarioEjemplo: UsuarioEjemplo,
    onEstadoCambia : (Boolean) -> Unit,
    modifier: Modifier = Modifier
){

    Card(
        modifier = Modifier.fillMaxWidth(1f).padding(Dimens.espacioMed)

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment =  Alignment.CenterVertically
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(17.dp)
            ){
                Text(
                    text = usuarioEjemplo.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(Dimens.espacioChico)
                )
                Text(
                    text = usuarioEjemplo.correo,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(Dimens.espacioChico)
                )
                Etiqueta(
                    texto = if(usuarioEjemplo.activo) R.string.activar_cuenta else R.string.desactivar_cuenta,
                    activo = usuarioEjemplo.activo
                )
            }
            Switch(
                checked = usuarioEjemplo.activo,
                onCheckedChange = {nuevo -> onEstadoCambia(nuevo)}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsuariosCardsView(){
    ElectricAppTheme(true) {
        UsuariosCards(
            usuarioEjemplo = UsuarioEjemplo(
                1,
                "Manuel Mora",
                Rol.INGENIERO,
                "Manuel@gmail.com"
            ),
            onEstadoCambia = {}
        )
    }
}