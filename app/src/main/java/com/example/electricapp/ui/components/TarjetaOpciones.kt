package com.example.electricapp.ui.components


import androidx.compose.material3.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme


@Composable
fun TarjetaOpciones(
    titulo : String,
    subtitulo : String,
    @DrawableRes icono : Int,
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
){
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = EstilosTarjetas.colores()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Dimens.espacioMed),
            horizontalArrangement = Arrangement.spacedBy(Dimens.espacioLogoJefe),
            verticalAlignment =  Alignment.CenterVertically,
        ) {

            Icon(
                painter = painterResource(icono),
                contentDescription = null
            )
            Column(
                modifier = Modifier.padding()
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TarjetaOpcionesView(){
    ElectricAppTheme(true) {
    Column(

    ) {
        TarjetaOpciones (
            "Gestionar usuarios",
            "Administrar cuentas y accesos",
            R.drawable.ic_gestion_usuarios,
            modifier = Modifier.padding(20.dp),
            onClick = {}
        )
        TarjetaOpciones(
            "Historial de equipos",
            "Consultar el historial de un equipo",
            R.drawable.ic_historial_equipo,
            modifier = Modifier.padding(20.dp),
            onClick = {}
        )
        TarjetaOpciones (
            "Revisar protocolos",
            "Revise protocolos",
            R.drawable.ic_revisar_protocolos,
            modifier = Modifier.padding(20.dp),
            onClick = {}
        )
        TarjetaOpciones(
            "Anular protocolos",
            "Anule protocolos",
            R.drawable.ic_anular_protocolos,
            modifier = Modifier.padding(20.dp),
            onClick = {}
        )

        TarjetaOpciones(
            "Registrar clientes",
            "Clientes, instalaciones y equipo",
            R.drawable.ic_registrar_client_equipo,
            modifier = Modifier.padding(20.dp),
            onClick = {}
        )
        TarjetaOpciones (
            "Crear protocolos ",
            "Crear y registrar protocolos",
            R.drawable.ic_crear_protocolos,
            modifier = Modifier.padding(20.dp),
            onClick = {}
        )
        TarjetaOpciones(
            "Desactivar personal",
            "Desactive personal",
            R.drawable.ic_desactivar_personal,
            modifier = Modifier.padding(20.dp),
            onClick = {}
        )
    }
    }
}
