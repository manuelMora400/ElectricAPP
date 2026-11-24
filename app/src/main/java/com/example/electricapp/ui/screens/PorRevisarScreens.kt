package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.electricapp.model.ProtocoloRevision
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.TarjetaOpciones
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.viewModel.PorRevisarViewModel

@Composable
fun PorRevisarScreens(
    viewModel: PorRevisarViewModel = viewModel(),
    onVolver: () -> Unit,
    onRevisar: (Int) -> Unit
) {
    val protocolos by viewModel.uistate.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.cargarProtocolos() }

    PorRevisarContenido(
        protocolos = protocolos,
        onVolver = onVolver,
        onRevisar = onRevisar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PorRevisarContenido(
    protocolos: List<ProtocoloRevision>,
    onVolver: () -> Unit,
    onRevisar: (Int) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.ing_por_revisar),
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

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioGrilla)
        ) {
            item {
                Bienvenida(
                    texto = stringResource(R.string.ing_titulo_por_revisar),
                    descripcion = stringResource(R.string.ing_orden_por_revisar)
                )
            }

            if (protocolos.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.ing_sin_protocolos),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            items(protocolos, key = { it.idProtocolo }) { protocolo ->
                TarjetaOpciones(
                    titulo = "${protocolo.tag} · ${protocolo.instalacion}",
                    subtitulo = stringResource(R.string.ing_tecnico_fecha, protocolo.tecnico, protocolo.fechaPrueba),
                    icono = R.drawable.ic_revisar_protocolos,
                    onClick = { onRevisar(protocolo.idProtocolo) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
