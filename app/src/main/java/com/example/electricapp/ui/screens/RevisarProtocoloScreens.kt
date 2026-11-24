package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.ResultadoPrueba
import com.example.electricapp.model.RevisarProtocoloState
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.BotonSecundario
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.viewModel.RevisarProtocoloViewModel

@Composable
fun RevisarProtocoloScreens(
    viewModel: RevisarProtocoloViewModel = viewModel(),
    idProtocolo: Int,
    onVolver: () -> Unit,
    onObservar: () -> Unit,
    onFormalizado: () -> Unit
) {
    val uiState by viewModel.uistate.collectAsStateWithLifecycle()

    LaunchedEffect(idProtocolo) { viewModel.cargar(idProtocolo) }

    LaunchedEffect(uiState.formalizado) {
        if (uiState.formalizado) {
            onFormalizado()
        }
    }

    RevisarProtocoloContenido(
        uiState = uiState,
        onVolver = onVolver,
        onObservar = onObservar,
        onPedirFormalizar = viewModel::onPedirFormalizar,
        onConfirmarFormalizar = viewModel::onConfirmarFormalizar,
        onCancelar = viewModel::onCancelar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevisarProtocoloContenido(
    uiState: RevisarProtocoloState,
    onVolver: () -> Unit,
    onObservar: () -> Unit,
    onPedirFormalizar: () -> Unit,
    onConfirmarFormalizar: () -> Unit,
    onCancelar: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.ing_titulo_revisar),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            painter = painterResource(R.drawable.ic_volver),
                            contentDescription = stringResource(id = R.string.volver)
                        )
                    }
                },
                colors = EstilosBarra.colores()
            )
        }
    ) { innerPadding ->

        val errorGeneral = uiState.errorGeneral

        val protocolo = uiState.protocolo
        if (protocolo == null) {
            if (errorGeneral != null) {
                Text(
                    text = errorGeneral,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(innerPadding).padding(16.dp)
                )
            }
            return@Scaffold
        }

        val resultado = ResultadoPrueba.entries.find { it.name == protocolo.resultado }

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioGrilla)
        ) {
            item {
                Bienvenida(
                    texto = "${protocolo.tag} · ${protocolo.instalacion}",
                    descripcion = stringResource(
                        R.string.protocolo_datos_descripcion,
                        protocolo.fechaPrueba,
                        protocolo.tecnico
                    )
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = EstilosTarjetas.coloresSecundario(),
                    border = EstilosTarjetas.bordeado(),
                    elevation = EstilosTarjetas.elevacion()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.espacioMed),
                        verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
                    ) {
                        DatoProtocolo(stringResource(R.string.protocolo_campo_devanado), protocolo.devanado)
                        DatoProtocolo(stringResource(R.string.protocolo_campo_tension), protocolo.tensionPrueba)
                        DatoProtocolo(stringResource(R.string.protocolo_campo_lectura_uno), protocolo.medicion1Min)
                        DatoProtocolo(stringResource(R.string.protocolo_campo_lectura_diez), protocolo.medicion10Min)
                        DatoProtocolo(
                            stringResource(R.string.protocolo_titulo_resultado),
                            if (resultado != null) stringResource(resultado.texto) else protocolo.resultado
                        )
                    }
                }
            }

            if (errorGeneral != null) {
                item {
                    Text(
                        text = errorGeneral,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            item {
                BotonInicio(
                    texto = stringResource(R.string.ing_btn_formalizar),
                    onClick = onPedirFormalizar,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                BotonSecundario(
                    texto = stringResource(R.string.ing_btn_observar),
                    onClick = onObservar,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (uiState.mostrarConfirmacion) {
            AlertDialog(
                onDismissRequest = onCancelar,
                title = {
                    Text(
                        text = stringResource(R.string.ing_confirmar_titulo),
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.ing_confirmar_texto, protocolo.tag),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                confirmButton = {
                    TextButton(onClick = onConfirmarFormalizar) {
                        Text(text = stringResource(R.string.ing_btn_formalizar))
                    }
                },
                dismissButton = {
                    TextButton(onClick = onCancelar) {
                        Text(text = stringResource(R.string.btn_cancelar))
                    }
                }
            )
        }
    }
}

@Composable
private fun DatoProtocolo(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
