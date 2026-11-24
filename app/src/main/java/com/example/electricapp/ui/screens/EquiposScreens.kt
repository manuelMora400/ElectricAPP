package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioEquipos
import com.example.electricapp.ui.components.LogoPerfil
import com.example.electricapp.ui.components.etiquetaRol
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.viewModel.EquiposViewModel


@Composable
fun screensEquipos(
    viewModel: EquiposViewModel = viewModel(),
    onAgregarEquipo: () -> Unit,
    onVolver: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val equipos by viewModel.equipos.collectAsStateWithLifecycle()
    val cargando by viewModel.cargando.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.cargarDatos()
    }

    EquiposScreens(
        uiState = uiState,
        equipos = equipos,
        cargando = cargando,
        onAgregarEquipo = onAgregarEquipo,
        onVolver = onVolver
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquiposScreens(
    uiState: FormularioEquipos,
    equipos: List<FormularioEquipos>,
    cargando: Boolean,
    onAgregarEquipo: () -> Unit,
    onVolver: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregarEquipo,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_agregar),
                    contentDescription = stringResource(R.string.agregar_equipo)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.titulo_equipos_sistema),
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
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioGrilla)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.espacioChico)) {
                    Text(
                        text = stringResource(R.string.titulo_lista_equipos),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(R.string.ayuda_lista_equipos),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (uiState.errorGeneral != null) {
                item {
                    Text(
                        text = uiState.errorGeneral,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (equipos.isEmpty() && !cargando && uiState.errorGeneral == null) {
                item {
                    Text(
                        text = stringResource(R.string.equipos_vacio),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            items(equipos, key = { it.id }) { equipo ->
                EquipoItem(equipo = equipo)
            }
        }
    }
}

@Composable
private fun EquipoItem(equipo: FormularioEquipos) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = EstilosTarjetas.colores(),
        border = EstilosTarjetas.bordeado()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogoPerfil(
                iniciales = equipo.tag.take(2).uppercase(),
                onClick = {}
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = equipo.tag,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = stringResource(R.string.equipo_tipo_instalacion, equipo.tipo, equipo.instalacion),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
                etiquetaRol(
                    texto = equipo.cliente,
                    colorFondo = MaterialTheme.colorScheme.primaryContainer,
                    colorTexto = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

private val equiposEjemplo = listOf(
    FormularioEquipos(id = 1, tag = "TR-01", tipo = "Transformador", instalacion = "Planta Lampa", cliente = "Marcela Fuentes Rojas"),
    FormularioEquipos(id = 2, tag = "TR-SS-01", tipo = "Transformador", instalacion = "Faena Til Til", cliente = "Patricio Navarro Soto")
)

@Preview(showBackground = true)
@Composable
fun EquiposScreensView() {
    ElectricAppTheme(false) {
        EquiposScreens(
            uiState = FormularioEquipos(),
            equipos = equiposEjemplo,
            cargando = false,
            onAgregarEquipo = {},
            onVolver = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EquiposScreensOscuroView() {
    ElectricAppTheme(true) {
        EquiposScreens(
            uiState = FormularioEquipos(),
            equipos = equiposEjemplo,
            cargando = false,
            onAgregarEquipo = {},
            onVolver = {}
        )
    }
}
