package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioProtocolosState
import com.example.electricapp.model.ResultadoPrueba
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonCamara
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CamposFormularios
import com.example.electricapp.ui.components.SeleccionLista
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.styles.estiloAltoBoton
import com.example.electricapp.ui.theme.Blanco
import com.example.electricapp.ui.theme.CuadroAprobado
import com.example.electricapp.ui.theme.CuadroObservacion
import com.example.electricapp.ui.theme.CuadroRechazado
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Exito
import com.example.electricapp.ui.theme.ExitoFondo
import com.example.electricapp.ui.theme.Primario
import com.example.electricapp.ui.theme.TextoOscuro
import com.example.electricapp.viewModel.FormularioProtocolosViewModel

@Composable
fun screensCrearProtocolos(
    viewModel: FormularioProtocolosViewModel = viewModel(),
    idTecnico: Long,
    nombreTecnico: String,
    onVolver: () -> Unit = {},
    onRegistrado: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val equipos by viewModel.equipos.collectAsStateWithLifecycle()
    val devanados by viewModel.devanados.collectAsStateWithLifecycle()
    val instrumentos by viewModel.nombresInstrumentos.collectAsStateWithLifecycle()

    LaunchedEffect(idTecnico) {
        viewModel.onTecnicoSesion(idTecnico, nombreTecnico)
    }

    CrearProtocolosScreens(
        uiState = uiState,
        equipos = equipos,
        devanados = devanados,
        instrumentos = instrumentos,
        onVolver = onVolver,
        onEquipoCambia = viewModel::onEquipoCambia,
        onTemperaturaCambia = viewModel::onTemperaturaCambia,
        onHumedadCambia = viewModel::onHumedadCambia,
        onDevanadoCambia = viewModel::onDevanadoCambia,
        onTensionCambia = viewModel::onTensionCambia,
        onLecturaUnMinutoCambia = viewModel::onLecturaUnMinutoCambia,
        onLecturaDiezMinutosCambia = viewModel::onLecturaDiezMinutosCambia,
        onInstrumentoCambia = viewModel::onInstrumentoCambia,
        onLimpiar = viewModel::onLimpiar,
        onRegistrar = { viewModel.onRegistrar(onExito = onRegistrado) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearProtocolosScreens(
    uiState: FormularioProtocolosState,
    equipos: List<String>,
    devanados: List<String>,
    instrumentos: List<String>,
    onVolver: () -> Unit = {},
    onEquipoCambia: (String) -> Unit,
    onTemperaturaCambia: (String) -> Unit,
    onHumedadCambia: (String) -> Unit,
    onDevanadoCambia: (String) -> Unit,
    onTensionCambia: (String) -> Unit,
    onLecturaUnMinutoCambia: (String) -> Unit,
    onLecturaDiezMinutosCambia: (String) -> Unit,
    onInstrumentoCambia: (String) -> Unit,
    onLimpiar: () -> Unit,
    onRegistrar: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.protocolo_titulo),
                        fontWeight = FontWeight.Bold
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
                    containerColor = Primario,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(Dimens.espacioPantalla),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
        ) {

            item {
                Bienvenida(
                    texto = stringResource(R.string.protocolo_titulo),
                    descripcion = stringResource(R.string.protocolo_subtitulo)
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
                        Bienvenida(
                            texto = stringResource(R.string.protocolo_titulo_datos),
                            descripcion = stringResource(
                                R.string.protocolo_datos_descripcion,
                                uiState.fechaHora,
                                uiState.tecnico
                            )
                        )

                        SeleccionLista(
                            opcion = uiState.equipo,
                            opciones = equipos,
                            texto = R.string.protocolo_campo_equipo,
                            onOpcionSeleccionada = onEquipoCambia,
                            error = uiState.errorEquipo
                        )

                        CamposFormularios(
                            valor = uiState.temperatura,
                            onValorCambia = onTemperaturaCambia,
                            etiquetaCampo = stringResource(R.string.protocolo_campo_temperatura),
                            ayudaUsuario = "Ej: 20.5 (-20 a 60 °C)",
                            icono = R.drawable.ic_calibracion_instrumento,
                            error = uiState.errorTemperatura,
                            tipoTeclado = KeyboardType.Decimal
                        )

                        CamposFormularios(
                            valor = uiState.humedad,
                            onValorCambia = onHumedadCambia,
                            etiquetaCampo = stringResource(R.string.protocolo_campo_humedad),
                            ayudaUsuario = "Ej: 55 (0 a 100 %)",
                            icono = R.drawable.ic_calibracion_instrumento,
                            error = uiState.errorHumedad,
                            tipoTeclado = KeyboardType.Decimal
                        )
                    }
                }
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
                        Bienvenida(
                            texto = stringResource(R.string.protocolo_titulo_medicion),
                            descripcion = stringResource(R.string.protocolo_descripcion_medicion)
                        )

                        SeleccionLista(
                            opcion = uiState.devanado,
                            opciones = devanados,
                            texto = R.string.protocolo_campo_devanado,
                            onOpcionSeleccionada = onDevanadoCambia,
                            error = uiState.errorDevanado
                        )

                        CamposFormularios(
                            valor = uiState.tensionPrueba,
                            onValorCambia = onTensionCambia,
                            etiquetaCampo = stringResource(R.string.protocolo_campo_tension),
                            ayudaUsuario = "Ej: 5",
                            icono = R.drawable.ic_calibracion_instrumento,
                            error = uiState.errorTensionPrueba,
                            tipoTeclado = KeyboardType.Decimal
                        )

                        CamposFormularios(
                            valor = uiState.lecturaUnMinuto,
                            onValorCambia = onLecturaUnMinutoCambia,
                            etiquetaCampo = stringResource(R.string.protocolo_campo_lectura_uno),
                            ayudaUsuario = "Ej: 5000",
                            icono = R.drawable.ic_numero_serie,
                            error = uiState.errorLecturaUnMinuto,
                            tipoTeclado = KeyboardType.Decimal
                        )

                        CamposFormularios(
                            valor = uiState.lecturaDiezMinutos,
                            onValorCambia = onLecturaDiezMinutosCambia,
                            etiquetaCampo = stringResource(R.string.protocolo_campo_lectura_diez),
                            ayudaUsuario = "Ej: 12000",
                            icono = R.drawable.ic_numero_serie,
                            error = uiState.errorLecturaDiezMinutos,
                            tipoTeclado = KeyboardType.Decimal
                        )
                    }
                }
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
                        Bienvenida(
                            texto = stringResource(R.string.protocolo_titulo_instrumento),
                            descripcion = stringResource(R.string.protocolo_descripcion_instrumento)
                        )

                        SeleccionLista(
                            opcion = uiState.instrumento,
                            opciones = instrumentos,
                            texto = R.string.protocolo_campo_instrumento,
                            onOpcionSeleccionada = onInstrumentoCambia,
                            error = uiState.errorInstrumento
                        )
                    }
                }
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
                    ){
                        Bienvenida(
                            texto = stringResource(R.string.foto_evidencia_terreno),
                            descripcion = stringResource(R.string.capturar_foto)
                        )

                        BotonCamara(
                            texto = if (uiState.fotoEvidencia == null) "Tomar foto" else "Cambiar foto"
                        )
                    }
                }
            }

            if (uiState.indicePolarizacion != null && uiState.resultado != null) {
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
                            Bienvenida(
                                texto = stringResource(R.string.protocolo_titulo_resultado),
                                descripcion = stringResource(
                                    R.string.protocolo_indice,
                                    "%.2f".format(uiState.indicePolarizacion)
                                )
                            )

                            EtiquetaResultado(
                                resultado = uiState.resultado,
                                indice = uiState.indicePolarizacion
                            )
                        }
                    }
                }
            }
            if (uiState.errorGeneral != null) {
                item {
                    Text(
                        text = uiState.errorGeneral,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
                ) {
                    BotonInicio(
                        texto = stringResource(R.string.btn_registrar_protocolo),
                        onClick = onRegistrar,
                        modifier = Modifier.estiloAltoBoton()
                    )

                    BotonCerrarSesion(
                        texto = stringResource(R.string.btn_limpiar),
                        onClick = onLimpiar,
                        icono = null
                    )
                }
            }

            if (uiState.mensajeExito) {
                item {
                    MensajeProtocoloRegistrado()
                }
            }
        }
    }
}

@Composable
private fun EtiquetaResultado(
    resultado: ResultadoPrueba,
    indice: Double
) {
    val colorFondo = when (resultado) {
        ResultadoPrueba.APROBADO -> CuadroAprobado
        ResultadoPrueba.OBSERVADO -> CuadroObservacion
        else -> CuadroRechazado
    }

    val colorTexto = if (resultado == ResultadoPrueba.RECHAZADO) Blanco else TextoOscuro

    Surface(
        shape = EstilosTarjetas.forma,
        color = colorFondo,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.espacioMed, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "IP ${"%.2f".format(indice)} · ",
                style = MaterialTheme.typography.titleMedium,
                color = colorTexto
            )
            Text(
                text = stringResource(resultado.texto),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colorTexto
            )
        }
    }
}

@Composable
private fun MensajeProtocoloRegistrado() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ExitoFondo, contentColor = Exito)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.espacioMed),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_exito),
                contentDescription = null,
                tint = Exito,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.protocolo_registrado_exito),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CrearProtocolosView() {
    ElectricAppTheme(true) {
        CrearProtocolosScreens(
            uiState = FormularioProtocolosState(
                fechaHora = "23/10/2026 19:30",
                tecnico = "Matias Godoy Herrera",
                indicePolarizacion = 2.40,
                resultado = ResultadoPrueba.APROBADO
            ),
            equipos = listOf("TR-01 - Planta Lampa"),
            devanados = listOf("AT-BT", "AT-T", "BT-T"),
            instrumentos = listOf("Megohmetro 5 kV"),
            onEquipoCambia = {},
            onTemperaturaCambia = {},
            onHumedadCambia = {},
            onDevanadoCambia = {},
            onTensionCambia = {},
            onLecturaUnMinutoCambia = {},
            onLecturaDiezMinutosCambia = {},
            onInstrumentoCambia = {},
            onLimpiar = {},
            onRegistrar = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CrearProtocolosView2() {
    ElectricAppTheme(false) {
        CrearProtocolosScreens(
            uiState = FormularioProtocolosState(
                fechaHora = "23/10/2026 19:30",
                tecnico = "Matias Godoy Herrera"
            ),
            equipos = listOf("TR-01 - Planta Lampa"),
            devanados = listOf("AT-BT", "AT-T", "BT-T"),
            instrumentos = listOf("Megohmetro 5 kV"),
            onEquipoCambia = {},
            onTemperaturaCambia = {},
            onHumedadCambia = {},
            onDevanadoCambia = {},
            onTensionCambia = {},
            onLecturaUnMinutoCambia = {},
            onLecturaDiezMinutosCambia = {},
            onInstrumentoCambia = {},
            onLimpiar = {},
            onRegistrar = {}
        )
    }
}