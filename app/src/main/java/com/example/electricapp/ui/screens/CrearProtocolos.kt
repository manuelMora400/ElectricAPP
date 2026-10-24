package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioProtocolosState
import com.example.electricapp.model.ResultadoPrueba
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CamposFormularios
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.components.SeleccionLista
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Blanco
import com.example.electricapp.ui.theme.CuadroAprobado
import com.example.electricapp.ui.theme.CuadroObservacion
import com.example.electricapp.ui.theme.CuadroRechazado
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Exito
import com.example.electricapp.ui.theme.ExitoFondo
import com.example.electricapp.ui.theme.TextoOscuro
import com.example.electricapp.viewModel.FormularioProtocolosViewModel

@Composable
fun screensCrearProtocolos(

    viewModel : FormularioProtocolosViewModel = viewModel(),
    onRegistrado : () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CrearProtocolosScreens(
        uiState = uiState,
        equipos = viewModel.equipos,
        devanados = viewModel.devanados,
        instrumentos = viewModel.instrumentos.map { it.nombre },
        onEquipoCambia = viewModel::onEquipoCambia,
        onTemperaturaCambia = viewModel::onTemperaturaCambia,
        onHumedadCambia = viewModel::onHumedadCambia,
        onDevanadoCambia = viewModel::onDevanadoCambia,
        onTensionCambia = viewModel::onTensionCambia,
        onLecturaUnMinutoCambia = viewModel::onLecturaUnMinutoCambia,
        onLecturaDiezMinutosCambia = viewModel::onLecturaDiezMinutosCambia,
        onInstrumentoCambia = viewModel::onInstrumentoCambia,
        onLimpiar = viewModel::onLimpiar,
        onRegistrar = {
            val registrado = viewModel.onRegistrar()
            if(registrado){
                onRegistrado()
            }
        }
    )
}

@Composable
fun CrearProtocolosScreens(

    uiState : FormularioProtocolosState,
    equipos : List<String>,
    devanados : List<String>,
    instrumentos : List<String>,
    onEquipoCambia : (String) -> Unit,
    onTemperaturaCambia : (String) -> Unit,
    onHumedadCambia : (String) -> Unit,
    onDevanadoCambia : (String) -> Unit,
    onTensionCambia : (String) -> Unit,
    onLecturaUnMinutoCambia : (String) -> Unit,
    onLecturaDiezMinutosCambia : (String) -> Unit,
    onInstrumentoCambia : (String) -> Unit,
    onLimpiar : () -> Unit,
    onRegistrar : () -> Unit
){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(innerPadding)
        ) {

            item {EncabezadoVistaJefe(
                titulo = stringResource(R.string.titulo_Empresa),
                subTitulo = stringResource(R.string.subtitulo_empresa),
                icono = R.drawable.logo_inicio_sesion
                )
            }

            item {Bienvenida(
                texto = stringResource(R.string.protocolo_titulo),
                descripcion = stringResource(R.string.protocolo_subtitulo)
                )
            }


            item { Card(
                modifier = Modifier.fillMaxWidth().padding(Dimens.espacioPantalla),
                colors = EstilosTarjetas.coloresSecundario(),
                border = EstilosTarjetas.bordeado(),
                elevation = EstilosTarjetas.elevacion()
            ) {
                Bienvenida(
                    texto = stringResource(R.string.protocolo_titulo_datos),
                    descripcion = stringResource(R.string.protocolo_datos_descripcion, uiState.fechaHora, uiState.tecnico)
                )

                SeleccionLista(
                    opcion = uiState.equipo,
                    opciones = equipos,
                    texto = R.string.protocolo_campo_equipo,
                    onOpcionSeleccionada = onEquipoCambia,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorEquipo
                )

                CamposFormularios(
                    valor = uiState.temperatura,
                    onValorCambia = onTemperaturaCambia,
                    etiquetaCampo = stringResource(R.string.protocolo_campo_temperatura),
                    icono = R.drawable.ic_calibracion_instrumento,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorTemperatura,
                    tipoTeclado = KeyboardType.Decimal
                )

                CamposFormularios(
                    valor = uiState.humedad,
                    onValorCambia = onHumedadCambia,
                    etiquetaCampo = stringResource(R.string.protocolo_campo_humedad),
                    icono = R.drawable.ic_calibracion_instrumento,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorHumedad,
                    tipoTeclado = KeyboardType.Decimal
                )
            }
            }


            item { Card(
                modifier = Modifier.fillMaxWidth().padding(Dimens.espacioPantalla),
                colors = EstilosTarjetas.coloresSecundario(),
                border = EstilosTarjetas.bordeado(),
                elevation = EstilosTarjetas.elevacion()
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
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorDevanado
                )

                CamposFormularios(
                    valor = uiState.tensionPrueba,
                    onValorCambia = onTensionCambia,
                    etiquetaCampo = stringResource(R.string.protocolo_campo_tension),
                    icono = R.drawable.ic_calibracion_instrumento,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorTensionPrueba,
                    tipoTeclado = KeyboardType.Decimal
                )

                CamposFormularios(
                    valor = uiState.lecturaUnMinuto,
                    onValorCambia = onLecturaUnMinutoCambia,
                    etiquetaCampo = stringResource(R.string.protocolo_campo_lectura_uno),
                    icono = R.drawable.ic_numero_serie,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorLecturaUnMinuto,
                    tipoTeclado = KeyboardType.Decimal
                )

                CamposFormularios(
                    valor = uiState.lecturaDiezMinutos,
                    onValorCambia = onLecturaDiezMinutosCambia,
                    etiquetaCampo = stringResource(R.string.protocolo_campo_lectura_diez),
                    icono = R.drawable.ic_numero_serie,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorLecturaDiezMinutos,
                    tipoTeclado = KeyboardType.Decimal
                )
            }
            }

            item { Card(
                modifier = Modifier.fillMaxWidth().padding(Dimens.espacioPantalla),
                colors = EstilosTarjetas.coloresSecundario(),
                border = EstilosTarjetas.bordeado(),
                elevation = EstilosTarjetas.elevacion()
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
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorInstrumento
                )
            }
            }

            if(uiState.indicePolarizacion != null && uiState.resultado != null){
                item { Card(
                    modifier = Modifier.fillMaxWidth().padding(Dimens.espacioPantalla),
                    colors = EstilosTarjetas.coloresSecundario(),
                    border = EstilosTarjetas.bordeado(),
                    elevation = EstilosTarjetas.elevacion()
                ) {
                    Bienvenida(
                        texto = stringResource(R.string.protocolo_titulo_resultado),
                        descripcion = stringResource(R.string.protocolo_indice, "%.2f".format(uiState.indicePolarizacion))
                    )

                    EtiquetaResultado(uiState.resultado)
                }
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.espacioMed),
                    modifier = Modifier.padding(Dimens.espacioPantalla)
                ) {
                    BotonInicio(
                        texto = stringResource(R.string.btn_registrar_protocolo),
                        onClick = onRegistrar,
                        modifier = Modifier.fillMaxWidth(1f)
                    )
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.espacioMed),
                    modifier = Modifier.padding(Dimens.espacioPantalla)
                ) {
                    BotonCerrarSesion(
                        texto = stringResource(R.string.btn_limpiar),
                        onClick = onLimpiar,
                        icono = null
                    )
                }
            }

            if(uiState.mensajeExito){
                item {
                    MensajeProtocoloRegistrado()
                }
            }
        }
    }
}


@Composable
private fun EtiquetaResultado(resultado : ResultadoPrueba){

    val colorFondo =
        if(resultado == ResultadoPrueba.APROBADO){
            CuadroAprobado
        }else if(resultado == ResultadoPrueba.OBSERVADO){
            CuadroObservacion
        }else
            CuadroRechazado

    val colorTexto =
        if(resultado == ResultadoPrueba.RECHAZADO){
            Blanco
        }else
            TextoOscuro

    Surface(
        shape = EstilosTarjetas.forma,
        color = colorFondo,
        modifier = Modifier.padding(Dimens.espacioMed)
    ) {
        Text(
            text = stringResource(resultado.texto),
            style = MaterialTheme.typography.titleMedium,
            color = colorTexto,
            modifier = Modifier.padding(Dimens.espacioMed)
        )
    }
}

@Composable
private fun MensajeProtocoloRegistrado(){

    Card(
        modifier = Modifier.fillMaxSize(1f),
        colors = CardDefaults.cardColors(containerColor = ExitoFondo, contentColor = Exito)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_exito),
            contentDescription = null,
            tint = Exito,
            modifier = Modifier.size(Dimens.tamanoIcono)
        )
        Text(
            text = stringResource(R.string.protocolo_registrado_exito),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(Dimens.espacioPantalla)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CrearProtocolosView(){
    ElectricAppTheme(true) {
        CrearProtocolosScreens(
            uiState = FormularioProtocolosState(
                fechaHora = "23/10/2026 19:30",
                tecnico = "Matias Godoy Herrera",
                indicePolarizacion = 1.8,
                resultado = ResultadoPrueba.OBSERVADO
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
fun CrearProtocolosView2(){
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
