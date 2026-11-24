package com.example.electricapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioInstrumentosStates
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.BotonSecundario
import com.example.electricapp.ui.components.CamposFormularios
import com.example.electricapp.ui.components.InstrumentosCards
import com.example.electricapp.ui.components.TituloSeccion
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.styles.estiloAltoBoton
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Exito
import com.example.electricapp.ui.theme.ExitoFondo
import com.example.electricapp.viewModel.FormularioInstrumentosViewModel

@Composable
fun screensRegistrosInstrumentos(
    viewModel: FormularioInstrumentosViewModel = viewModel(),
    onAbrirFormulario: () -> Unit,
    onVolver: () -> Unit,
    soloLectura: Boolean = false
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val instrumentos by viewModel.instrumentosBD.collectAsStateWithLifecycle()

    InstrumentosScreens(
        uiState = uiState,
        instrumentos = instrumentos,
        soloLectura = soloLectura,
        onAgregar = {
            viewModel.onLimpiar()
            onAbrirFormulario()
        },
        onSeleccionar = { instrumento ->
            viewModel.onSeleccionarInstrumento(instrumento)
            onAbrirFormulario()
        },
        onVolver = onVolver
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstrumentosScreens(
    uiState: FormularioInstrumentosStates,
    instrumentos: List<FormularioInstrumentosStates>,
    soloLectura: Boolean,
    onAgregar: () -> Unit,
    onSeleccionar: (FormularioInstrumentosStates) -> Unit,
    onVolver: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            if (!soloLectura) {
                FloatingActionButton(
                    onClick = onAgregar,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_agregar),
                        contentDescription = stringResource(R.string.agregar_instrumento)
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.titulo_Instrumentos_calibracion),
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
                        text = stringResource(R.string.visualizar_instrumentos),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(
                            if (soloLectura) R.string.ayuda_lista_instrumentos_lectura
                            else R.string.ayuda_lista_instrumentos
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (uiState.mensajeExito) {
                item {
                    mensajeExitoso(
                        mensaje = stringResource(
                            if (uiState.modoRenovar) R.string.mensaje_renovado_exito_instrumentos
                            else R.string.mensaje_registrado_exito_instrumentos
                        )
                    )
                }
            }

            items(instrumentos, key = { it.id }) { instrumento ->
                InstrumentosCards(
                    instrumento = instrumento,
                    modifier = if (soloLectura) Modifier
                    else Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSeleccionar(instrumento) }
                )
            }
        }
    }
}

@Composable
fun screensFormularioInstrumento(
    viewModel: FormularioInstrumentosViewModel = viewModel(),
    onVolver: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val instrumentos by viewModel.instrumentosBD.collectAsStateWithLifecycle()

    FormularioInstrumentoScreens(
        uiState = uiState,
        instrumentoActual = instrumentos.find { it.numSerie == uiState.numSerie },
        onNombreCambia = viewModel::onNombreCambia,
        onMarcaCambia = viewModel::onMarcaCambia,
        onModeloCambia = viewModel::onModeloCambia,
        onNumSerieCambia = viewModel::onNumSerieCambia,
        onFechaCalibracionCambia = viewModel::onFechaCalifracionCambia,
        onFechaVencimientoCambia = viewModel::onFechaVencimientoCambia,
        onRegistrar = { viewModel.onRegistrar(onExito = onVolver) },
        onRenovar = { viewModel.onActualizar(onExito = onVolver) },
        onLimpiar = viewModel::onLimpiar,
        onVolver = onVolver
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioInstrumentoScreens(
    uiState: FormularioInstrumentosStates,
    instrumentoActual: FormularioInstrumentosStates?,
    onNombreCambia: (String) -> Unit,
    onMarcaCambia: (String) -> Unit,
    onModeloCambia: (String) -> Unit,
    onNumSerieCambia: (String) -> Unit,
    onFechaCalibracionCambia: (String) -> Unit,
    onFechaVencimientoCambia: (String) -> Unit,
    onRegistrar: () -> Unit,
    onRenovar: () -> Unit,
    onLimpiar: () -> Unit,
    onVolver: () -> Unit
) {
    val titulo = if (uiState.modoRenovar) R.string.titulo_renovar_instrumento else R.string.titulo_registrar_instrumento
    val ayuda = if (uiState.modoRenovar) R.string.ayuda_renovar_instrumento else R.string.ayuda_registrar_instrumento

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(titulo),
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
                Text(
                    text = stringResource(ayuda),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (uiState.modoRenovar) {
                item {
                    TituloSeccion(texto = stringResource(R.string.seccion_instrumento_renovar))
                }
                if (instrumentoActual != null) {
                    item {
                        InstrumentosCards(instrumento = instrumentoActual)
                    }
                }
            } else {
                item {
                    TituloSeccion(texto = stringResource(R.string.seccion_datos_instrumento))
                }
                item {
                    SeccionFormularioInstrumento {
                        CamposFormularios(
                            valor = uiState.nombre,
                            onValorCambia = onNombreCambia,
                            etiquetaCampo = stringResource(R.string.campo_nombre_instrumentos),
                            ayudaUsuario = stringResource(R.string.ej_nombre_instrumento),
                            icono = R.drawable.ic_calibracion_instrumento,
                            error = uiState.errorNombre,
                            tipoTeclado = KeyboardType.Text
                        )
                        CamposFormularios(
                            valor = uiState.marca,
                            onValorCambia = onMarcaCambia,
                            etiquetaCampo = stringResource(R.string.campo_marca_instrumentos),
                            ayudaUsuario = stringResource(R.string.ej_marca_instrumento),
                            icono = R.drawable.ic_marca_instrumentos,
                            error = uiState.errorMarca,
                            tipoTeclado = KeyboardType.Text
                        )
                        CamposFormularios(
                            valor = uiState.modelo,
                            onValorCambia = onModeloCambia,
                            etiquetaCampo = stringResource(R.string.campo_modelo_Instrumentos),
                            ayudaUsuario = stringResource(R.string.ej_modelo_instrumento),
                            icono = R.drawable.ic_modelo_instrumento,
                            error = uiState.errorModelo,
                            tipoTeclado = KeyboardType.Text
                        )
                        CamposFormularios(
                            valor = uiState.numSerie,
                            onValorCambia = onNumSerieCambia,
                            etiquetaCampo = stringResource(R.string.campo_numero_serie_instrumentos),
                            ayudaUsuario = stringResource(R.string.ej_serie_instrumento),
                            icono = R.drawable.ic_numero_serie,
                            error = uiState.errorNumeroSerie,
                            tipoTeclado = KeyboardType.Text
                        )
                    }
                }
            }

            item {
                TituloSeccion(
                    texto = stringResource(
                        if (uiState.modoRenovar) R.string.seccion_nueva_calibracion
                        else R.string.titulo_instruccion_Instrumentos
                    )
                )
            }
            item {
                SeccionFormularioInstrumento {
                    CamposFormularios(
                        valor = uiState.fechaCalibracion,
                        onValorCambia = onFechaCalibracionCambia,
                        etiquetaCampo = stringResource(R.string.campo_fecha_calibracion_instrumento),
                        ayudaUsuario = stringResource(R.string.ej_fecha_instrumento),
                        icono = R.drawable.ic_fechas,
                        error = uiState.errorFechaCalibracion,
                        tipoTeclado = KeyboardType.Text
                    )
                    CamposFormularios(
                        valor = uiState.fechaVencimiento,
                        onValorCambia = onFechaVencimientoCambia,
                        etiquetaCampo = stringResource(R.string.campo_fecha_vencimiento_instumento),
                        ayudaUsuario = stringResource(R.string.ej_fecha_instrumento),
                        icono = R.drawable.ic_fechas,
                        error = uiState.errorFechaVencimiento,
                        tipoTeclado = KeyboardType.Text
                    )
                }
            }

            if (uiState.modoRenovar && uiState.errorNumeroSerie != null) {
                item {
                    Text(
                        text = uiState.errorNumeroSerie,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(top = Dimens.espacioMed),
                    verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
                ) {
                    if (uiState.modoRenovar) {
                        BotonInicio(
                            texto = stringResource(R.string.btn_renovar_instrumento),
                            onClick = onRenovar,
                            modifier = Modifier.estiloAltoBoton()
                        )
                        BotonSecundario(
                            texto = stringResource(R.string.volver),
                            onClick = onVolver
                        )
                    } else {
                        BotonInicio(
                            texto = stringResource(R.string.btn_registrar_instrumento),
                            onClick = onRegistrar,
                            modifier = Modifier.estiloAltoBoton()
                        )
                        BotonSecundario(
                            texto = stringResource(R.string.btn_limpiar),
                            onClick = onLimpiar
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SeccionFormularioInstrumento(contenido: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = EstilosTarjetas.formaPanel,
        colors = EstilosTarjetas.coloresPanel(),
        border = EstilosTarjetas.bordeSuave(),
        elevation = EstilosTarjetas.sinElevacion()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(Dimens.padSeccionFormulario),
            content = contenido
        )
    }
}

@Composable
fun mensajeExitoso(mensaje : String){

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ExitoFondo, contentColor = Exito),
        border = EstilosTarjetas.bordeado(),
        elevation = EstilosTarjetas.elevacion(),
        shape = EstilosTarjetas.forma
    ) {
        Row(
            modifier = Modifier.padding(Dimens.espacioMed),
            horizontalArrangement = Arrangement.spacedBy(Dimens.espacioMed),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_exito),
                contentDescription = null,
                tint = Exito,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
            Text(
                text = mensaje,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private val instrumentosEjemplo = listOf(
    FormularioInstrumentosStates(
        id = 1, nombre = "Megóhmetro 5 kV", marca = "Megger", modelo = "MIT525",
        numSerie = "MIT-0045821", fechaVencimiento = "15/03/2027"
    ),
    FormularioInstrumentosStates(
        id = 2, nombre = "Megóhmetro 10 kV", marca = "Megger", modelo = "MIT1025",
        numSerie = "MIT-0071233", fechaVencimiento = "10/10/2026", vencido = true
    )
)

@Preview(showBackground = true)
@Composable
fun InstrumentosJefeView() {
    ElectricAppTheme(false) {
        InstrumentosScreens(
            uiState = FormularioInstrumentosStates(mensajeExito = true),
            instrumentos = instrumentosEjemplo,
            soloLectura = false,
            onAgregar = {},
            onSeleccionar = {},
            onVolver = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InstrumentosTecnicoOscuroView() {
    ElectricAppTheme(true) {
        InstrumentosScreens(
            uiState = FormularioInstrumentosStates(),
            instrumentos = instrumentosEjemplo,
            soloLectura = true,
            onAgregar = {},
            onSeleccionar = {},
            onVolver = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioRegistrarInstrumentoView() {
    ElectricAppTheme(false) {
        FormularioInstrumentoScreens(
            uiState = FormularioInstrumentosStates(),
            instrumentoActual = null,
            onNombreCambia = {},
            onMarcaCambia = {},
            onModeloCambia = {},
            onNumSerieCambia = {},
            onFechaCalibracionCambia = {},
            onFechaVencimientoCambia = {},
            onRegistrar = {},
            onRenovar = {},
            onLimpiar = {},
            onVolver = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioRenovarInstrumentoOscuroView() {
    ElectricAppTheme(true) {
        FormularioInstrumentoScreens(
            uiState = FormularioInstrumentosStates(numSerie = "MIT-0071233", modoRenovar = true),
            instrumentoActual = instrumentosEjemplo[1],
            onNombreCambia = {},
            onMarcaCambia = {},
            onModeloCambia = {},
            onNumSerieCambia = {},
            onFechaCalibracionCambia = {},
            onFechaVencimientoCambia = {},
            onRegistrar = {},
            onRenovar = {},
            onLimpiar = {},
            onVolver = {}
        )
    }
}
