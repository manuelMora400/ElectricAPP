package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.generador.GeneradorPdfProtocolo
import com.example.electricapp.model.DetallesState
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonDescargar
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Blanco
import com.example.electricapp.ui.theme.CuadroAprobado
import com.example.electricapp.ui.theme.CuadroObservacion
import com.example.electricapp.ui.theme.CuadroRechazado
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Primario
import com.example.electricapp.ui.theme.TextoOscuro
import com.example.electricapp.ui.theme.TextoSuave
import com.example.electricapp.viewModel.DetalleViewModel

@Composable
fun DetallesScreen(
    viewModel: DetalleViewModel = viewModel(),
    onDescargar: () -> Unit,
    modifier: Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetallesContentScreen(
        uiState = uiState,
        onDescargar = onDescargar
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesContentScreen(
    uiState: DetallesState,
    onDescargar: () -> Unit
) {
    val lectura1 = uiState.medicion1Min.toDoubleOrNull() ?: 0.0
    val lectura10 = uiState.medicion10Min.toDoubleOrNull() ?: 0.0
    val indicePolarizacion = if (lectura1 > 0.0) lectura10 / lectura1 else 0.0
    val context = LocalContext.current
    val textoIngeniero: String = if (!uiState.nombreIngeniero.isNullOrBlank()) {
        uiState.nombreIngeniero
    } else {
        "Pendiente de validación"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (uiState.Tag.isNotBlank()) "Detalle · ${uiState.Tag}"
                        else "Detalle de Protocolo",
                        style = MaterialTheme.typography.titleLarge,
                        color = Blanco
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onDescargar) {
                        Icon(
                            painter = painterResource(R.drawable.ic_volver),
                            contentDescription = "Volver",
                            tint = Blanco
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primario,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.espacioMed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.espacioPantalla,
                            end = Dimens.espacioPantalla,
                            top = Dimens.espacioPantalla
                        )
                ) {
                    if (uiState.resultado.isNotBlank()) {
                        InsigniaEstado(
                            texto = uiState.resultado,
                            esResultado = true
                        )
                    }
                    if (uiState.estado.isNotBlank()) {
                        InsigniaEstado(
                            texto = uiState.estado,
                            esResultado = false
                        )
                    }
                }
            }

            item {
                BannerResultadoProtocolo(
                    indicePolarizacion = indicePolarizacion,
                    resultado = uiState.resultado
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.espacioPantalla, vertical = Dimens.espacioMed),
                    colors = EstilosTarjetas.coloresSecundario(),
                    border = EstilosTarjetas.bordeado(),
                    elevation = EstilosTarjetas.elevacion()
                ) {
                    Bienvenida(
                        texto = "${uiState.Tag} · ${uiState.Instalacion}",
                        descripcion = "Prueba del ${uiState.fechaPrueba}"
                    )
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.espacioPantalla, vertical = Dimens.espacioMed),
                    colors = EstilosTarjetas.coloresSecundario(),
                    border = EstilosTarjetas.bordeado(),
                    elevation = EstilosTarjetas.elevacion()
                ) {
                    Bienvenida(
                        texto = "Medición ${uiState.devanado} · ${uiState.tensionPrueba} kV",
                        descripcion = "1 min ${uiState.medicion1Min} MΩ · 10 min ${uiState.medicion10Min} MΩ"
                    )
                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.espacioPantalla, vertical = Dimens.espacioMed),
                    colors = EstilosTarjetas.coloresSecundario(),
                    border = EstilosTarjetas.bordeado(),
                    elevation = EstilosTarjetas.elevacion()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.espacioMed)
                    ) {
                        Surface(
                            shape = EstilosTarjetas.forma,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(Dimens.tamanoIcono)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_exito),
                                contentDescription = null,
                                tint = TextoSuave,
                                modifier = Modifier.padding(Dimens.espacioChico)
                            )
                        }

                        Spacer(modifier = Modifier.width(Dimens.espacioMed))

                        Column {
                            Text(
                                text = "Formalizado por",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = textoIngeniero,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            item {
                BotonDescargar(
                    detalle = uiState,
                    indicePolarizacion = indicePolarizacion,
                    modifier = Modifier.padding(horizontal = Dimens.espacioPantalla)
                )
            }
        }
    }

}

@Composable
private fun InsigniaEstado(
    texto: String,
    esResultado: Boolean
) {
    val colorFondo = if (esResultado) {
        obtenerColorResultado(texto)
    } else {
        MaterialTheme.colorScheme.primary
    }

    val colorTexto = if (!esResultado || texto.equals("RECHAZADO", ignoreCase = true)) {
        Blanco
    } else {
        TextoOscuro
    }

    Surface(
        shape = EstilosTarjetas.forma,
        color = colorFondo
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = colorTexto,
            modifier = Modifier.padding(
                horizontal = Dimens.espacioMed,
                vertical = Dimens.espacioChico
            )
        )
    }
}

@Composable
private fun BannerResultadoProtocolo(
    indicePolarizacion: Double,
    resultado: String
) {
    val colorFondo = obtenerColorResultado(resultado)
    val colorTexto = if (resultado.equals("RECHAZADO", ignoreCase = true)) Blanco else TextoOscuro

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.espacioPantalla),
        shape = EstilosTarjetas.forma,
        colors = CardDefaults.cardColors(
            containerColor = colorFondo,
            contentColor = colorTexto
        ),
        elevation = EstilosTarjetas.elevacion()
    ) {
        Text(
            text = "IP ${"%.2f".format(indicePolarizacion)} · ${resultado.ifBlank { "Sin resultado" }}",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(Dimens.espacioPantalla)
        )
    }
}

private fun obtenerColorResultado(resultado: String) = when {
    resultado.equals("APROBADO", ignoreCase = true) -> CuadroAprobado
    resultado.equals("OBSERVADO", ignoreCase = true) -> CuadroObservacion
    else -> CuadroRechazado
}

@Preview(showBackground = true)
@Composable
fun DetallesScreenPreview() {
    ElectricAppTheme(darkTheme = false) {
        DetallesContentScreen(
            uiState = DetallesState(
                Tag = "TR-01",
                Instalacion = "Planta Lampa",
                medicion1Min = "9800",
                medicion10Min = "21560",
                devanado = "AT-T",
                tensionPrueba = "5",
                resultado = "Aprobado",
                estado = "Formalizado",
                nombreIngeniero = "Andrea Contreras",
                fechaPrueba = "13/09/2025"
            ),
            onDescargar = {}
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DetalleScreenPreview() {
    ElectricAppTheme(darkTheme = true) {
        DetallesContentScreen(
            uiState = DetallesState(
                Tag = "TR-01",
                Instalacion = "Planta Lampa",
                medicion1Min = "9800",
                medicion10Min = "21560",
                devanado = "AT-T",
                tensionPrueba = "5",
                resultado = "Aprobado",
                estado = "Formalizado",
                nombreIngeniero = "Andrea Contreras",
                fechaPrueba = "13/09/2025"
            ),
            onDescargar = {}
        )
    }
}