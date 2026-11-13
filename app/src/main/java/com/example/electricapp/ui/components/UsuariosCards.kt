package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.model.FormularioRegistros
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun UsuariosCards(
    usuario: FormularioRegistros,
    onEstadoCambia : (Boolean) -> Unit,
    modifier: Modifier = Modifier
){

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = EstilosTarjetas.colores(),
        border = EstilosTarjetas.bordeado()

    ) {

        //esto saca las iniciales por ejemplo manuel mora MM o javier gongora JG
        val iniciales = usuario.nombre.split(" ").take(2).mapNotNull { it.firstOrNull() }
                        .joinToString ("").uppercase()

        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment =  Alignment.CenterVertically
        ) {

            LogoPerfil(
                iniciales = iniciales,
                onClick = {}
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ){
                Text(
                    text = usuario.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.padding(Dimens.espacioChico)
                )
                Text(
                    text = usuario.correo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    modifier = Modifier.padding(Dimens.espacioChico)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = modifier.padding(top = 4.dp)
                ) {

                    val texto =
                        when(usuario.rol){
                            Rol.JEFE_TECNICO -> stringResource( R.string.rol_jefe_tecnico)
                            Rol.TECNICO -> stringResource(R.string.rol_tecnico)
                            Rol.CLIENTE -> stringResource(R.string.rol_cliente)
                            else -> stringResource(R.string.rol_ingeniero)
                        }

                    etiquetaRol(
                        texto = texto,
                        colorFondo = MaterialTheme.colorScheme.secondaryContainer,
                        colorTexto = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    if(!usuario.activo){
                        etiquetaRol(
                            texto = texto,
                            colorFondo = MaterialTheme.colorScheme.errorContainer,
                            colorTexto = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
            Switch(
                checked = usuario.activo,
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
            usuario = FormularioRegistros(

                "Manuel Mora",
                "Manuel@gmail.com",
                rol = Rol.CLIENTE
            ),
            onEstadoCambia = {}
        )
    }
}