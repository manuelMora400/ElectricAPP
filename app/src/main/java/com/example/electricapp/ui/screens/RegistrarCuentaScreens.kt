
package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioRegistros
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CampoCorreo
import com.example.electricapp.ui.components.CamposFormularios
import com.example.electricapp.ui.components.SeleccionPerfilRegistro
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
    onVolver: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.agregar_cuenta))
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            painter = painterResource(R.drawable.ic_volver),
                            contentDescription = stringResource(id = R.string.volver)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioPantalla)
        ) {
            item {
                TituloSeccion(texto = stringResource(R.string.datos_personales))
            }
            item {
                CamposFormularios(
                    valor = uiState.rut,
                    onValorCambia = onRutCambia,
                    etiquetaCampo = stringResource(R.string.campo_rut_registro),
                    ayudaUsuario = stringResource(R.string.ayuda_rut),
                    icono = R.drawable.ic_rut,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorRut,
                    tipoTeclado = KeyboardType.Text
                )
                CamposFormularios(
                    valor = uiState.nombre,
                    onValorCambia = onNombreCambia,
                    etiquetaCampo = stringResource(R.string.campo_nombre_completo_registro),
                    ayudaUsuario = stringResource(R.string.ayuda_nombre),
                    icono = R.drawable.ic_nombre_formulario,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorNombre,
                    tipoTeclado = KeyboardType.Text
                )
                CamposFormularios(
                    valor = uiState.telefono,
                    onValorCambia = onTelefonoCambia,
                    etiquetaCampo = stringResource(R.string.campo_telefono_registro),
                    ayudaUsuario = stringResource(R.string.ayuda_telefono),
                    icono = R.drawable.ic_telefono,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorTelefono,
                    tipoTeclado = KeyboardType.Text
                )
                CampoCorreo(
                    valor = uiState.correo,
                    onValorCambia = onCorreoCambia,
                    etiquetaCorreo = stringResource(R.string.campo_correo),
                    iconoCorreo = R.drawable.ic_correo,
                    modifier = Modifier.padding(Dimens.espacioMed),
                    error = uiState.errorCorreo,
                    tecladoTipo = KeyboardType.Email
                )
            }
            item {
                TituloSeccion(texto = stringResource(R.string.perfil_usuario))
            }
            item {
                SeleccionPerfilRegistro(
                    rol = uiState.rol,
                    texto = R.string.campo_perfil_registro,
                    onRolSeleccionado = onRolCambia,
                    modifier = Modifier.padding(Dimens.espacioMed)
                )

                if (uiState.Cliente) {

                    CamposFormularios(
                        valor = uiState.giro,
                        onValorCambia = onGiroCambia,
                        etiquetaCampo = stringResource(R.string.campo_giro_registro),
                        ayudaUsuario = stringResource(R.string.ayuda_giro) ,
                        icono = R.drawable.ic_cargo,
                        modifier = Modifier.padding(Dimens.espacioMed),
                        error = uiState.errorGiro,
                        tipoTeclado = KeyboardType.Text
                    )
                    CamposFormularios(
                        valor = uiState.contactoCargo,
                        onValorCambia = onContactoCambia,
                        etiquetaCampo = stringResource(R.string.campo_contacto_cargo),
                        ayudaUsuario = stringResource(R.string.ayuda_cargo),
                        icono = R.drawable.ic_contacto_cargo,
                        modifier = Modifier.padding(Dimens.espacioMed),
                        error = uiState.errorCotacto,
                        tipoTeclado = KeyboardType.Text
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.espacioMed),
                    modifier = Modifier.padding(Dimens.espacioMed)
                ) {
                    BotonInicio(
                        texto = stringResource(R.string.btn_registre_r),
                        onClick = onRegistrar,
                        modifier = Modifier.fillMaxWidth(1f)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.espacioMed),
                    modifier = Modifier.padding(Dimens.espacioMed)
                ) {
                    BotonCerrarSesion(
                        texto = stringResource(R.string.btn_limpiar),
                        onClick = onLimpiar,
                        icono = null
                    )
                }
            }
        }
    }
}

@Composable
private fun TituloSeccion(texto: String) {
    Text(
        text = texto.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(top = 4.dp)
    )
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
            onVolver = {}
        )
    }
}
