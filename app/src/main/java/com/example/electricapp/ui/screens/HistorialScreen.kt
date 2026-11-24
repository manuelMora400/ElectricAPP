package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.HistorialState
import com.example.electricapp.model.Rol
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
    rol: Rol,
    zonaCliente: String = "",
    Buscado: () -> Unit,
    onVolver: () -> Unit = {},
    onProtocoloClick: (HistorialState) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(rol, zonaCliente) {
        viewModel.onRol(rol, zonaCliente)
    }

    HistorialScreen(
        uiState = uiState,
        onTagBuscar = viewModel::onTagBuscar,
        onInstalacion = viewModel::onInstalacion,
        realizarBusqueda = { viewModel.Busqueda(onExito = Buscado) },
        onVolver = onVolver,
        onProtocoloClick = onProtocoloClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    uiState: HistorialState,
    onTagBuscar: (String) -> Unit,
    onInstalacion: (String) -> Unit,
    realizarBusqueda: () -> Unit,
    onVolver: () -> Unit = {},
    onProtocoloClick: (HistorialState) -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (uiState.Cliente) {
                TopAppBar(
                    title = {
                        val tituloBarra = if (uiState.tagBuscar.isNotBlank() && uiState.zonaCliente.isNotBlank()) {
                            "${uiState.tagBuscar} · ${uiState.zonaCliente}"
                        } else {
                            uiState.zonaCliente.ifBlank { stringResource(R.string.titulo_historial) }
                        }
                        Text(
                            text = tituloBarra,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onVolver) {
                            Icon(
                                painter = painterResource(R.drawable.ic_volver),
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF283593)
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            if (!uiState.Cliente) {
                EncabezadoVistaJefe(
                    titulo = stringResource(R.string.titulo_historial),
                    subTitulo = stringResource(R.string.titulo_bienvenidos),
                    icono = R.drawable.logo_inicio_sesion
                )
            }

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

                if (!uiState.Cliente) {
                    Spacer(modifier = Modifier.height(8.dp))

                    CampoInstalacion(
                        valor = uiState.instalacion,
                        onInstalacion = onInstalacion,
                        etiquetaInstalacion = stringResource(R.string.campo_Instalacion),
                        iconoInst = R.drawable.ic_busqueda,
                        error = uiState.intalacionError
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                BotonInicio(
                    texto = stringResource(R.string.btn_historial),
                    onClick = realizarBusqueda,
                    siHabilitado = !uiState.cargando,
                    modifier = Modifier.fillMaxWidth()
                )

                if (uiState.cargando) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.listaProtocolo.forEach { protocoloItem ->
                        ProtocoloCards(
                            protocolo = protocoloItem,
                            esCliente = uiState.Cliente,
                            onClick = { onProtocoloClick(protocoloItem) }
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
            realizarBusqueda = {}
        )
    }
}