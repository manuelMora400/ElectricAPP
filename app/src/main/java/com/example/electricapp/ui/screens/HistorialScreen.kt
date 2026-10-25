package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.HistorialState
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CampoInstalacion
import com.example.electricapp.ui.components.CampoTag
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.components.ProtocoloCards
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.viewModel.HistorialViewModel

@Composable
fun buscarProtocolo(
    viewModel: HistorialViewModel = viewModel(),
    Buscado: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HistorialScreen(
        uiState = uiState,
        onTagBuscar = viewModel::onTagBuscar,
        onInstalacion = viewModel::onInstalacion,
        realizarBusqueda = {
            if (viewModel.Busqueda()) {
                Buscado()
            }
        }
    )
}

@Composable
fun HistorialScreen(
    uiState: HistorialState,
    onTagBuscar: (String) -> Unit,
    onInstalacion: (String) -> Unit,
    realizarBusqueda: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            EncabezadoVistaJefe(
                titulo = stringResource(R.string.titulo_historial),
                subTitulo = stringResource(R.string.titulo_bienvenida),
                icono = R.drawable.logo_inicio_sesion
            )

            Column(
                modifier = Modifier.padding(Dimens.espacioPantalla),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CampoTag(
                    valor = uiState.tagBuscar,
                    onTagBuscar = onTagBuscar,
                    etiquetaTag = stringResource(R.string.campo_tag),
                    iconoTag = R.drawable.ic_nombre,
                    error = uiState.tagError
                )

                Spacer(modifier = Modifier.height(8.dp))

                CampoInstalacion(
                    valor = uiState.instalacion,
                    onInstalacion = onInstalacion,
                    etiquetaInstalacion = stringResource(R.string.campo_Instalacion),
                    iconoInst = R.drawable.ic_busqueda,
                    error = uiState.intalacionError
                )

                Spacer(modifier = Modifier.height(12.dp))

                BotonInicio(
                    texto = "Buscar",
                    onClick = realizarBusqueda,
                    modifier = Modifier.fillMaxWidth()
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(uiState.listaProtocolo) { protocoloItem ->
                        ProtocoloCards(
                            protocolo = protocoloItem
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProtocoloView() {
    ElectricAppTheme(true) {
        HistorialScreen(
            uiState = HistorialState(),
            onTagBuscar = {},
            onInstalacion = {},
            realizarBusqueda={},
        )

    }
}