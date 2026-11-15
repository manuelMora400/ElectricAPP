
package com.example.electricapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioRegistros
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.components.BotonEnviarSms
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.BotonSecundario
import com.example.electricapp.ui.components.CamposFormularios
import com.example.electricapp.ui.components.SeleccionPerfilRegistro
import com.example.electricapp.ui.components.TituloSeccion
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.styles.estiloAltoBoton
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.viewModel.FormularioRegistrosViewModel

@Composable
fun screensRegistrarCuentas(
    viewModel: FormularioRegistrosViewModel = viewModel(),
    onRegistroExitoso : () -> Unit,
    onVolver: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    RegistrarCuentaScreens(
        uiState = uiState,
        onRutCambia = viewModel::onRutCambia,
        onNombreCambia = viewModel::onNombreCambia,
        onTelefonoCambia = viewModel::onTelefonoCambia,
        onCorreoCambia = viewModel::onCorreoCambia,
        onRolCambia = viewModel::onRolCambia,
        onGiroCambia = viewModel::onGiroCambia,
        onContactoCambia = viewModel::onContactoCambia,
        onLimpiar = viewModel::onLimpiar,
        onRegistrar = {
            val registradoExito = viewModel.onRegistrar()
            if(registradoExito){
                onRegistroExitoso()
            }
        },
        onCerrar = {
            viewModel.onLimpiar()
            onVolver()
        },
        onVolver = onVolver
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarCuentaScreens(
    uiState: FormularioRegistros,
    onRutCambia: (String) -> Unit,
    onNombreCambia: (String) -> Unit,
    onTelefonoCambia: (String) -> Unit,
    onCorreoCambia: (String) -> Unit,
    onRolCambia: (Rol) -> Unit,
    onGiroCambia: (String) -> Unit,
    onContactoCambia: (String) -> Unit,
    onLimpiar: () -> Unit,
    onRegistrar: () -> Unit,
    onCerrar : () -> Unit,
    onVolver: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.agregar_cuenta).trim(),
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
                        text = stringResource(R.string.titulo_formulario_registro),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(R.string.ayuda_formulario_registro),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                TituloSeccion(texto = stringResource(R.string.datos_personales))
            }
            item {
                SeccionFormulario {
                    CamposFormularios(
                        valor = uiState.rut,
                        onValorCambia = onRutCambia,
                        etiquetaCampo = stringResource(R.string.campo_rut_registro),
                        ayudaUsuario = stringResource(R.string.ayuda_rut).trim(),
                        icono = R.drawable.ic_rut,
                        error = uiState.errorRut,
                        tipoTeclado = KeyboardType.Text
                    )
                    CamposFormularios(
                        valor = uiState.nombre,
                        onValorCambia = onNombreCambia,
                        etiquetaCampo = stringResource(R.string.campo_nombre_completo_registro),
                        ayudaUsuario = stringResource(R.string.ayuda_nombre).trim(),
                        icono = R.drawable.ic_nombre_formulario,
                        error = uiState.errorNombre,
                        tipoTeclado = KeyboardType.Text
                    )
                    CamposFormularios(
                        valor = uiState.telefono,
                        onValorCambia = onTelefonoCambia,
                        etiquetaCampo = stringResource(R.string.campo_telefono_registro),
                        ayudaUsuario = stringResource(R.string.ayuda_telefono).trim(),
                        icono = R.drawable.ic_telefono,
                        error = uiState.errorTelefono,
                        tipoTeclado = KeyboardType.Phone //Teclado para telefono (numerico) para el +
                    )

                    CamposFormularios(
                        valor = uiState.correo,
                        onValorCambia = onCorreoCambia,
                        etiquetaCampo = stringResource(R.string.campo_correo),
                        ayudaUsuario = stringResource(R.string.ayuda_correo).trim(),
                        icono = R.drawable.ic_correo,
                        error = uiState.errorCorreo,
                        tipoTeclado = KeyboardType.Email
                    )
                }
            }

            item {
                TituloSeccion(texto = stringResource(R.string.perfil_usuario))
            }
            item {
                SeccionFormulario {
                    SeleccionPerfilRegistro(
                        rol = uiState.rol,
                        texto = R.string.campo_perfil_registro,
                        onRolSeleccionado = onRolCambia,
                        modifier = Modifier.padding(Dimens.espacioMed),
                        icono = R.drawable.ic_gestion_usuarios
                    )

                    // Se añade Animacion
                    // antes era if(uistate.cliente) -> abria un menu aparte abajito
                    // ahora es lo mismo, solo que se le pasa a una animacion
                    AnimatedVisibility(
                        visible = uiState.Cliente,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Column {
                            CamposFormularios(
                                valor = uiState.giro,
                                onValorCambia = onGiroCambia,
                                etiquetaCampo = stringResource(R.string.campo_giro_registro),
                                ayudaUsuario = stringResource(R.string.ayuda_giro).trim(),
                                icono = R.drawable.ic_cargo,
                                error = uiState.errorGiro,
                                tipoTeclado = KeyboardType.Text
                            )
                            CamposFormularios(
                                valor = uiState.contactoCargo,
                                onValorCambia = onContactoCambia,
                                etiquetaCampo = stringResource(R.string.campo_contacto_cargo),
                                ayudaUsuario = stringResource(R.string.ayuda_cargo).trim(),
                                icono = R.drawable.ic_contacto_cargo,
                                error = uiState.errorCotacto,
                                tipoTeclado = KeyboardType.Text
                            )
                        }
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(top = Dimens.espacioMed),
                    verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
                ) {
                    BotonInicio(
                        texto = stringResource(R.string.btn_registre_r),
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

        if (uiState.claveTemporal != null) {
            AlertDialog(
                onDismissRequest = {},
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_candado),
                        contentDescription = null
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.dialogo_titulo_cuenta_creada),
                        fontWeight = FontWeight.Bold
                    )
                },

                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Dimens.espacioChico)
                    ) {
                        Text(
                            text = stringResource(R.string.dialogo_titulo_clave),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stringResource(R.string.dialogo_descripcion_clave, uiState.telefono),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = stringResource(R.string.dialogo_aviso_cerrar),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },

                confirmButton = {
                    BotonEnviarSms(
                        telefono = uiState.telefono,
                        clave = uiState.claveTemporal,
                        onEnviado = onCerrar
                    )
                },
                dismissButton = {
                    TextButton(onClick = onCerrar) {
                        Text(text = stringResource(R.string.btn_cerrar))
                    }
                }
            )
        }
    }
}

// componente unico de aqui, se usa para alinear todo lo que tenga dentro de la card
// y no se deforme
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
@Preview(showBackground = true)
@Composable
fun RegistrarCuentasView() {
    ElectricAppTheme(true) {
        RegistrarCuentaScreens(
            uiState = FormularioRegistros(),
            onRutCambia = {},
            onNombreCambia = {},
            onTelefonoCambia = {},
            onCorreoCambia = {},
            onRolCambia = {},
            onGiroCambia = {},
            onContactoCambia = {},
            onLimpiar = {},
            onRegistrar = {},
            onCerrar = {},
            onVolver = {}
        )
    }
}



