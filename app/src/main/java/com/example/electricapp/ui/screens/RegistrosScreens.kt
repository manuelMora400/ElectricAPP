package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.electricapp.model.FormularioRegistros
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CampoCorreo
import com.example.electricapp.ui.components.CamposFormularios
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.components.SeleccionPerfilRegistro
import com.example.electricapp.ui.components.UsuariosCards
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Exito
import com.example.electricapp.ui.theme.ExitoFondo
import com.example.electricapp.viewModel.FormularioRegistrosViewModel


@Composable
fun screensRegistro(

     viewModel : FormularioRegistrosViewModel = viewModel(),
     onRegistroExitoso : () -> Unit
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cuentas by viewModel.cuentasBD.collectAsStateWithLifecycle()

    RegistrosScreens (
        uiState = uiState,
        cuentas = cuentas,
        onEstadoCambia = viewModel::onEstadoCambia,
        onRutCambia = viewModel::onRutCambia,
        onNombreCambia = viewModel::onNombreCambia,
        onTelefonoCambia = viewModel::onTelefonoCambia,
        onCorreoCambia = viewModel::onCorreoCambia,
        onPasswordCambia = viewModel::onPasswordCambia,
        onRolCambia = viewModel::onRolCambia,
        onGiroCambia = viewModel::onGiroCambia,
        onContactoCambia = viewModel::onContactoCambia,
        onLimpiar = viewModel::onLimpiar,
        onRegistrar = {
            val registrado = viewModel.onRegistrar()
            if(registrado){
                onRegistroExitoso()
            }
        }
    )
}

@Composable
fun RegistrosScreens(

    uiState : FormularioRegistros,
    cuentas : List<FormularioRegistros>,
    onRutCambia : (String) -> Unit,
    onNombreCambia : (String) -> Unit,
    onTelefonoCambia : (String) -> Unit,
    onCorreoCambia : (String) -> Unit,
    onPasswordCambia : (String) -> Unit,
    onRolCambia : (Rol) -> Unit,
    onGiroCambia : (String) -> Unit,
    onContactoCambia : (String) -> Unit,
    onLimpiar : () -> Unit,
    onRegistrar : () -> Unit,
    onEstadoCambia :(String, Boolean) -> Unit

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

            item {  Bienvenida(
                texto = stringResource(R.string.titulo_gestionar_cuenta),
                descripcion = stringResource(R.string.subtitulo_gestionar_cuenta)
                )
            }
            item {  Card(

                modifier = Modifier.fillMaxWidth().padding(Dimens.espacioPantalla),
                colors = EstilosTarjetas.coloresSecundario(),
                border = EstilosTarjetas.bordeado(),
                elevation = EstilosTarjetas.elevacion()

            ) {
                Bienvenida(
                    texto = stringResource(R.string.titulo_formulario_registro),
                    descripcion = stringResource(R.string.ayuda_formulario_registro)
                )
                    CamposFormularios(
                        valor = uiState.rut,
                        onValorCambia = onRutCambia,
                        etiquetaCampo = stringResource(R.string.campo_rut_registro),
                        icono = R.drawable.ic_rut,
                        modifier = Modifier.padding(Dimens.espacioMed),
                        error = uiState.errorRut,
                        tipoTeclado = KeyboardType.Text
                    )

                    CamposFormularios(
                        valor = uiState.nombre,
                        onValorCambia = onNombreCambia,
                        etiquetaCampo = stringResource(R.string.campo_nombre_completo_registro),
                        icono = R.drawable.ic_nombre_formulario,
                        modifier = Modifier.padding(Dimens.espacioMed),
                        error = uiState.errorNombre,
                        tipoTeclado = KeyboardType.Text
                    )

                    CamposFormularios(
                        valor = uiState.telefono,
                        onValorCambia = onTelefonoCambia,
                        etiquetaCampo = stringResource(R.string.campo_telefono_registro),
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

                    CamposFormularios(
                        valor = uiState.password,
                        onValorCambia = onPasswordCambia,
                        etiquetaCampo = stringResource(R.string.campo_password),
                        icono = R.drawable.ic_candado,
                        modifier = Modifier.padding(Dimens.espacioMed),
                        error = uiState.errorPassword,
                        tipoTeclado = KeyboardType.Password
                    )

                    SeleccionPerfilRegistro(
                        rol = uiState.rol,
                        texto = R.string.campo_perfil_registro,
                        onRolSeleccionado = onRolCambia,
                        modifier = Modifier.padding(Dimens.espacioMed)
                    )

                if(uiState.Cliente){

                    CamposFormularios(
                        valor = uiState.giro,
                        onValorCambia = onGiroCambia,
                        etiquetaCampo = stringResource(R.string.campo_giro_registro),
                        icono = R.drawable.ic_cargo,
                        modifier = Modifier.padding(Dimens.espacioMed),
                        error = uiState.errorGiro,
                        tipoTeclado = KeyboardType.Text
                    )

                    CamposFormularios(
                        valor = uiState.contactoCargo,
                        onValorCambia = onContactoCambia,
                        etiquetaCampo = stringResource(R.string.campo_contacto_cargo),
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
                if(uiState.registradoExito){
                    item {
                        MensajeExitoso(uiState.nombre)
                    }
                }

            item {Bienvenida(
                texto = stringResource(R.string.titulo_administrar_cuentas),
                descripcion = stringResource(R.string.subtitulo_administrar_cuentas),
            )
            }
                items(cuentas, key = {it.correo}){
                        usuarios ->
                    UsuariosCards(
                        usuario = usuarios,
                        onEstadoCambia = { nuevoEstado -> onEstadoCambia(usuarios.correo, nuevoEstado)})
                }
            }

        }
    }


@Composable
private fun MensajeExitoso(nombre : String){

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
            text = stringResource(R.string.exito_registrado),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(Dimens.espacioPantalla)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun RegistrosScreensView(){

    ElectricAppTheme(false) {
        RegistrosScreens (
            uiState = FormularioRegistros(),
            cuentas = emptyList(),
            onRutCambia = {},
            onNombreCambia = {},
            onTelefonoCambia = {},
            onCorreoCambia = {},
            onPasswordCambia = {},
            onRolCambia = {},
            onGiroCambia = {},
            onContactoCambia = {},
            onLimpiar = {},
            onRegistrar = {},
            onEstadoCambia = {_, _ ->}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrosScreensView2(){

    ElectricAppTheme(false) {
        RegistrosScreens (
            uiState = FormularioRegistros(),
            cuentas = emptyList(),
            onRutCambia = {},
            onNombreCambia = {},
            onTelefonoCambia = {},
            onCorreoCambia = {},
            onPasswordCambia = {},
            onRolCambia = {},
            onGiroCambia = {},
            onContactoCambia = {},
            onLimpiar = {},
            onRegistrar = {},
            onEstadoCambia = {_, _ ->}
        )
    }
}
