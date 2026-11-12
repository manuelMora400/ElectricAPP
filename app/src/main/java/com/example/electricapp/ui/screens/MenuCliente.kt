package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.R
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.components.TarjetaOpciones
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun MenuClienteScreens(
    onClick : () -> Unit,
    onCerrarSesion : () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPading ->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPading)
                .verticalScroll(rememberScrollState())
        ) {
            EncabezadoVistaJefe(
                titulo = stringResource(R.string.titulo_Empresa),
                subTitulo = stringResource(R.string.subtitulo_empresa),
                icono = R.drawable.logo_inicio_sesion
            )
            Column(
                modifier = Modifier.padding(Dimens.espacioPantalla),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Bienvenida(
                    texto = stringResource(R.string.titulo_cliente),
                    descripcion = stringResource(R.string.subtitulo)
                )
                Column(
                    modifier = Modifier.padding(Dimens.espacioPantalla),
                    verticalArrangement = Arrangement.spacedBy(Dimens.espacioTitulo)
                ) {
                    TarjetaOpciones(
                        titulo = stringResource(R.string.titulo_historial_equipos),
                        subtitulo = stringResource(R.string.subtitulo_historial_equipos),
                        icono = R.drawable.ic_historial_equipo,
                        modifier = Modifier.padding(),
                        onClick = onClick
                    )
                    Row(horizontalArrangement = Arrangement.Center) {
                        BotonCerrarSesion(
                            texto = stringResource(R.string.btn_cerrar_sesion),
                            onClick = onCerrarSesion
                        )
                    }

                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MenuClienteView(){
    ElectricAppTheme(true) {
        MenuClienteScreens (
            onClick = {},
            onCerrarSesion = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
fun MenuClienteView2(){
    ElectricAppTheme(false) {
        MenuClienteScreens (
            onClick = {},
            onCerrarSesion = {}
        )
    }
}
