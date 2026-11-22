package com.example.electricapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.EditarPerfilState
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CamposFormularios
import com.example.electricapp.ui.components.TituloSeccion
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.styles.EstilosCampos
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.styles.estiloAltoBoton
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.viewModel.EditarPerfilViewModel

@Composable
fun EditarPerfilScreens(
    viewModel: EditarPerfilViewModel = viewModel(),
    idUsuario: Long,
    rolSesion: Rol,
    onVolver: () -> Unit,
    onGuardado: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(idUsuario) {
        viewModel.cargar(idUsuario, rolSesion)
    }

    EditarPerfilContenido(
        uiState = uiState,
        onNombreCambia = viewModel::onNombreCambia,
        onCorreoCambia = viewModel::onCorreoCambia,
        onTelefonoCambia = viewModel::onTelefonoCambia,
        onVolver = onVolver,
        onGuardar = {
            if (viewModel.onGuardar()) {
                onGuardado(viewModel.uiState.value.nombre.trim())
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfilContenido(
    uiState: EditarPerfilState,
    onNombreCambia: (String) -> Unit,
    onCorreoCambia: (String) -> Unit,
    onTelefonoCambia: (String) -> Unit,
    onVolver: () -> Unit,
    onGuardar: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.btn_editar_datos),
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
                TituloSeccion(texto = stringResource(R.string.datos_personales))
            }
            item {
                SeccionFormulario {
                    CamposFormularios(
                        valor = uiState.nombre,
                        onValorCambia = onNombreCambia,
                        etiquetaCampo = stringResource(R.string.campo_nombre_completo_registro),
                        ayudaUsuario = "",
                        icono = R.drawable.ic_nombre_formulario,
                        error = uiState.errorNombre,
                        tipoTeclado = KeyboardType.Text
                    )
                    CamposFormularios(
                        valor = uiState.telefono,
                        onValorCambia = onTelefonoCambia,
                        etiquetaCampo = stringResource(R.string.campo_telefono_registro),
                        ayudaUsuario = "",
                        icono = R.drawable.ic_telefono,
                        error = uiState.errorTelefono,
                        tipoTeclado = KeyboardType.Phone
                    )

                    if (uiState.puedeEditarCorreo) {
                        CamposFormularios(
                            valor = uiState.correo,
                            onValorCambia = onCorreoCambia,
                            etiquetaCampo = stringResource(R.string.campo_correo),
                            ayudaUsuario = "",
                            icono = R.drawable.ic_correo,
                            error = uiState.errorCorreo,
                            tipoTeclado = KeyboardType.Email
                        )
                    } else {
                        CampoBloqueado(
                            valor = uiState.correo,
                            etiqueta = stringResource(R.string.campo_correo),
                            icono = R.drawable.ic_correo,
                            ayuda = stringResource(R.string.perfil_campo_bloqueado)
                        )
                    }

                    CampoBloqueado(
                        valor = uiState.rut,
                        etiqueta = stringResource(R.string.campo_rut_registro),
                        icono = R.drawable.ic_rut,
                        ayuda = null
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(top = Dimens.espacioMed),
                    verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
                ) {
                    val errorGeneral = uiState.errorGeneral
                    if (errorGeneral != null) {
                        Text(
                            text = errorGeneral,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    BotonInicio(
                        texto = stringResource(R.string.perfil_btn_guardar),
                        onClick = onGuardar,
                        modifier = Modifier.estiloAltoBoton()
                    )
                }
            }
        }
    }
}

@Composable
private fun SeccionFormulario(contenido: @Composable ColumnScope.() -> Unit) {
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
private fun CampoBloqueado(
    valor: String,
    etiqueta: String,
    @DrawableRes icono: Int,
    ayuda: String?
) {
    OutlinedTextField(
        value = valor,
        onValueChange = {},
        enabled = false,
        modifier = Modifier.fillMaxWidth().padding(Dimens.espacioMed),
        label = { Text(etiqueta) },
        leadingIcon = {
            Icon(
                painter = painterResource(icono),
                contentDescription = null,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_candado),
                contentDescription = null,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
        },
        supportingText = if (ayuda != null) { { Text(ayuda) } } else null,
        shape = EstilosCampos.forma
    )
}
