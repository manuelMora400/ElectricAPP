package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.components.TarjetaOpciones
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaTecnicoScrees(

    onCrearProtocolos : () -> Unit,
    onMisProtocolos   : () -> Unit,
    onRegistrarEquipo : () -> Unit,
    onHistorialDeEquipo : () -> Unit,
    onCerrarSesion : () -> Unit
){
    Scaffold(
        contentColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.banner_tecnico))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                        .verticalScroll(rememberScrollState()).padding(10.dp)
        ) {
            EncabezadoVistaJefe(
                titulo = stringResource(R.string.titulo_Empresa),
                subTitulo = stringResource(R.string.subtitulo_empresa),
                icono = R.drawable.logo_inicio_sesion
            )

            Column(
                modifier = Modifier.padding(Dimens.espacioPantalla),
                verticalArrangement = Arrangement.spacedBy(Dimens.espacioTitulo)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.tecnico_crear_protoclo),
                    subtitulo = stringResource(R.string.tecnico_subtitlo_crear_protocolo),
                    icono = R.drawable.ic_crear_protocolos,
                    onClick = onCrearProtocolos
                )

                TarjetaOpciones(
                    titulo = stringResource(R.string.tecnico_registrar_equipo),
                    subtitulo = stringResource(R.string.tecnico_subtitlo_crear_protocolo),
                    icono = R.drawable.ic_historial_equipo,
                    onClick = onRegistrarEquipo
                )

                TarjetaOpciones(
                    titulo = stringResource(R.string.tecnico_titulo_historial_equipo),
                    subtitulo = stringResource(R.string.tecnico_subtitulo_historial_equipo),
                    icono = R.drawable.ic_busqueda,
                    onClick = onHistorialDeEquipo
                )

                TarjetaOpciones(
                    titulo = stringResource(R.string.tecnico_titulo_mis_protocolos),
                    subtitulo = stringResource(R.string.tecnico_subtitulo_mis_protocolos),
                    icono = R.drawable.ic_revisar_protocolos,
                    onClick = onMisProtocolos
                )
            }
            BotonCerrarSesion(
                texto = stringResource(R.string.btn_cerrar_sesion),
                onClick = onCerrarSesion,
                icono = R.drawable.ic_cerrar_sesion
            )
           }
        }
    }

@Preview(showBackground = true)
@Composable
fun viewVistaTecnico(){

    ElectricAppTheme(true) {
        VistaTecnicoScrees(
            onCrearProtocolos = {},
            onMisProtocolos = {},
            onRegistrarEquipo = {},
            onHistorialDeEquipo = {},
            onCerrarSesion = {}
        )
    }
}