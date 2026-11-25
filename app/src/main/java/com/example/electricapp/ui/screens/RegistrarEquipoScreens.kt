package com.example.electricapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioEquipos
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.BotonSecundario
import com.example.electricapp.ui.components.CamposFormularios
import com.example.electricapp.ui.components.SeleccionLista
import com.example.electricapp.ui.components.TituloSeccion
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.styles.estiloAltoBoton
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.viewModel.EquiposViewModel

@Composable
fun screensRegistrarEquipo(
    viewModel: EquiposViewModel = viewModel(),
    onVolver: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val clientes by viewModel.clientes.collectAsStateWithLifecycle()
    val cargando by viewModel.cargando.collectAsStateWithLifecycle()
    val contexto = LocalContext.current
    val mensajeExito = stringResource(R.string.equipo_registrado_exito)

    RegistrarEquipoScreens(
        uiState = uiState,
        clientes = clientes.map { it.nombre },
        cargando = cargando,
        onTagCambia = viewModel::onTagCambia,
        onTipoCambia = viewModel::onTipoCambia,
        onInstalacionCambia = viewModel::onInstalacionCambia,
        onClienteCambia = viewModel::onClienteCambia,
        onLimpiar = viewModel::onLimpiar,
        onRegistrar = {
            viewModel.onRegistrar(
                onExito = {
                    Toast.makeText(contexto, mensajeExito, Toast.LENGTH_SHORT).show()
                    onVolver()
                }
            )
        },
        onVolver = onVolver
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarEquipoScreens(
    uiState: FormularioEquipos,
    clientes: List<String>,
    cargando: Boolean,
    onTagCambia: (String) -> Unit,
    onTipoCambia: (String) -> Unit,
    onInstalacionCambia: (String) -> Unit,
    onClienteCambia: (String) -> Unit,
    onLimpiar: () -> Unit,
    onRegistrar: () -> Unit,
    onVolver: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.agregar_equipo),
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
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.espacioChico)) {
                    Text(
                        text = stringResource(R.string.titulo_formulario_equipo),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(R.string.ayuda_formulario_equipo),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                TituloSeccion(texto = stringResource(R.string.seccion_datos_equipo))
            }
            item {
                SeccionFormularioEquipo {
                    CamposFormularios(
                        valor = uiState.tag,
                        onValorCambia = onTagCambia,
                        etiquetaCampo = stringResource(R.string.campo_tag_equipo),
                        ayudaUsuario = stringResource(R.string.ayuda_tag_equipo),
                        icono = R.drawable.ic_numero_serie,
                        error = uiState.errorTag,
                        tipoTeclado = KeyboardType.Text
                    )
                    CamposFormularios(
                        valor = uiState.tipo,
                        onValorCambia = onTipoCambia,
                        etiquetaCampo = stringResource(R.string.campo_tipo_equipo),
                        ayudaUsuario = stringResource(R.string.ayuda_tipo_equipo),
                        icono = R.drawable.ic_modelo_instrumento,
                        error = uiState.errorTipo,
                        tipoTeclado = KeyboardType.Text
                    )
                    CamposFormularios(
                        valor = uiState.instalacion,
                        onValorCambia = onInstalacionCambia,
                        etiquetaCampo = stringResource(R.string.campo_instalacion_equipo),
                        ayudaUsuario = stringResource(R.string.ayuda_instalacion_equipo),
                        icono = R.drawable.ic_historial_equipo,
                        error = uiState.errorInstalacion,
                        tipoTeclado = KeyboardType.Text
                    )
                }
            }

            item {
                TituloSeccion(texto = stringResource(R.string.seccion_cliente_equipo))
            }
            item {
                SeccionFormularioEquipo {
                    SeleccionLista(
                        opcion = uiState.cliente,
                        opciones = clientes,
                        texto = R.string.campo_cliente_equipo,
                        onOpcionSeleccionada = onClienteCambia,
                        modifier = Modifier.padding(Dimens.espacioMed),
                        error = uiState.errorCliente
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

            item {
                Column(
                    modifier = Modifier.padding(top = Dimens.espacioMed),
                    verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
                ) {
                    BotonInicio(
                        texto = stringResource(R.string.btn_registrar_equipo),
                        onClick = onRegistrar,
                        modifier = Modifier.estiloAltoBoton(),
                        siHabilitado = !cargando
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

@Composable
private fun SeccionFormularioEquipo(contenido: @Composable ColumnScope.() -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun RegistrarEquipoView() {
    ElectricAppTheme(false) {
        RegistrarEquipoScreens(
            uiState = FormularioEquipos(),
            clientes = listOf("Marcela Fuentes Rojas"),
            cargando = false,
            onTagCambia = {},
            onTipoCambia = {},
            onInstalacionCambia = {},
            onClienteCambia = {},
            onLimpiar = {},
            onRegistrar = {},
            onVolver = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrarEquipoOscuroView() {
    ElectricAppTheme(true) {
        RegistrarEquipoScreens(
            uiState = FormularioEquipos(errorTag = "El TAG es obligatorio"),
            clientes = listOf("Marcela Fuentes Rojas"),
            cargando = false,
            onTagCambia = {},
            onTipoCambia = {},
            onInstalacionCambia = {},
            onClienteCambia = {},
            onLimpiar = {},
            onRegistrar = {},
            onVolver = {}
        )
    }
}
