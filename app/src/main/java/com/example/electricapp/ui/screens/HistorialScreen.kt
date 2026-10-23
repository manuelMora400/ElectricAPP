package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioLoginState
import com.example.electricapp.model.HistorialState
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CampoInstalacion
import com.example.electricapp.ui.components.CampoTag
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.components.ImagenEncabezadoLogin
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.viewModel.HistorialViewModel

@Composable
fun buscarProtocolo(
    viewModel: HistorialViewModel = viewModel(),
    Buscado: ()-> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HistorialScreen(
        uiState = uiState,
        onTagBuscar = viewModel::onTagBuscar,
        onInstalacion = viewModel::onInstalacion,
        realizarBusqueda = {
            val buscando = viewModel.Busqueda()
            if (buscando){
                Buscado()
            }
        }
    )
}

@Composable
fun HistorialScreen(
    uiState : HistorialState,
    onTagBuscar: (String) -> Unit,
    onInstalacion: (String) -> Unit,
    realizarBusqueda:() -> Unit
){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ){

            EncabezadoVistaJefe(
                titulo = stringResource(R.string.titulo_historial),
                subTitulo = stringResource(R.string.titulo_bienvenida),
                icono = R.drawable.logo_inicio_sesion
            )
            Column(
                modifier = Modifier
                    .padding(Dimens.espacioPantalla),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                CampoTag(
                    valor = uiState.tagBuscar,
                    onTagBuscar = onTagBuscar,
                    etiquetaTag = stringResource(R.string.campo_tag),
                    iconoTag = R.drawable.ic_nombre,
                    error = uiState.tagError
                )
                CampoInstalacion(
                    valor = uiState.instalacion,
                    onInstalacion = onInstalacion,
                    etiquetaInstalacion = stringResource(R.string.campo_Instalacion),
                    iconoInst = R.drawable.ic_busqueda,
                    error = uiState.intalacionError
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.espacioMed)) {
                BotonInicio(
                    texto = stringResource(R.string.btn_historial),
                    onClick = realizarBusqueda,
                    modifier = Modifier.fillMaxSize()
                )
            }
            if (uiState.Cliente){

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TagScreensView(){

    ElectricAppTheme(false) {
        HistorialScreen(
            uiState = HistorialState(),
            onTagBuscar = {},
            onInstalacion = {},
            realizarBusqueda = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TagScreensVie(){

    ElectricAppTheme(true) {
        HistorialScreen(
            uiState = HistorialState(),
            onTagBuscar = {},
            onInstalacion = {},
            realizarBusqueda = {}
        )
    }
}