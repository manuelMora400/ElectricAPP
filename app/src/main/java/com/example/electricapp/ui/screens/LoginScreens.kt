package com.example.electricapp.ui.screens

import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.electricapp.R
import com.example.electricapp.model.FormularioLoginState
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CampoCorreo
import com.example.electricapp.ui.components.CampoPassword
import com.example.electricapp.ui.theme.Dimens
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonEnviarSms
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.viewModel.FormularioLoginViewModel


// Se elimino la card que mantenia los campos formularios
//  y se actualizo el componente vista jefe  para adaptarse
// por ultimo se agrega una animacion que "Hunde el boton"

@Composable
fun ScreensLogin(
    viewModel : FormularioLoginViewModel = viewModel(),
    onLogeado : (Rol) -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreens(
        uiState = uiState,
        onCorreoCambia = viewModel::onCorreoCambia,
        onPasswordCambia = viewModel::onPasswordCambia,
        onIniciar = {
            val rol = viewModel.onIniciar()
            if (rol != null){
                onLogeado(rol)
            }
        },
        onVisibilidad = viewModel::onVisibilidad,
        onLimpiar = viewModel::onLimpiarRecuperacion,
        onGenerarClave = viewModel::onEnviarClaveTemporal,
        onIdRecuperacioCambia = viewModel::onIdentificadiorClave
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreens(
    uiState : FormularioLoginState,
    onCorreoCambia : (String) -> Unit,
    onPasswordCambia : (String) -> Unit,
    onVisibilidad : () -> Unit,
    onIniciar : () -> Unit,
    onIdRecuperacioCambia : (String) -> Unit,
    onGenerarClave : () -> Unit,
    onLimpiar : () -> Unit
    ){
    var mostrarRecuperacion by remember { mutableStateOf(false) }

    Scaffold (
        containerColor = MaterialTheme.colorScheme.background
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            EncabezadoVistaJefe(
                titulo = stringResource(R.string.titulo_Empresa),
                subTitulo = stringResource(R.string.subtitulo_empresa),
                icono = R.drawable.logo_inicio_sesion
            )

            Column(
                modifier = Modifier.fillMaxWidth().padding(Dimens.espacioPantalla),
                verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
            ) {
                Bienvenida(
                    texto = stringResource(R.string.titulo_sesion),
                    descripcion = stringResource(R.string.bienvenida_inicio)
                )
                CampoCorreo(
                    valor = uiState.correo,
                    onValorCambia = onCorreoCambia,
                    etiquetaCorreo = stringResource(R.string.campo_correo),
                    iconoCorreo = R.drawable.ic_correo,
                    error = uiState.errorCorreo
                )
                CampoPassword(
                    valor = uiState.passwordHash,
                    onContrasenaCambia = onPasswordCambia,
                    etiquetaContrasena = stringResource(R.string.campo_password),
                    visible = uiState.passwordVisible,
                    onVisibilidad ,
                    error = uiState.errorPassword
                )

                //Esto nos permite hacer un mensaje error Credemciales "Como efecto"
                var errorCredenciales by remember {mutableStateOf("")}

                if(uiState.errorGeneral != null){
                    errorCredenciales = uiState.errorGeneral
                }

                AnimatedVisibility(
                    visible = uiState.errorGeneral != null,
                    enter = expandVertically()  + fadeIn(),
                    exit = shrinkVertically  () + fadeOut()
                ) {
                    Text(
                        text = errorCredenciales,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
                BotonInicio(
                    texto = stringResource(R.string.btn_iniciar_sesion),
                    onClick = onIniciar,
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                )
            TextButton(
                onClick = {
                    mostrarRecuperacion = true
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.btn_olvido_password),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            if (mostrarRecuperacion) {
                AlertDialog(
                    onDismissRequest = {
                        mostrarRecuperacion = false
                        onLimpiar()
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_candado),
                            contentDescription = null
                        )
                    },
                    title = {
                        Text(text = stringResource(R.string.btn_olvido_password))
                    },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(Dimens.espacioChico)) {
                            if (uiState.telefonoRecuperacion == null) {
                                Text(text = stringResource(R.string.no_existe_datos))
                                OutlinedTextField(
                                    value = uiState.idRecuperacion,
                                    onValueChange = onIdRecuperacioCambia,
                                    label = { Text(text = stringResource(R.string.rut_o_correo)) },
                                    isError = uiState.errorRecuperacion != null,
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                if (uiState.errorRecuperacion != null) {
                                    Text(
                                        text = uiState.errorRecuperacion,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            } else {
                                Text(
                                    text = stringResource(R.string.recuperacion_exito)
                                )
                            }
                        }
                    },
                    confirmButton = {
                        if (uiState.telefonoRecuperacion == null) {
                            TextButton(onClick = onGenerarClave) {
                                Text(text = stringResource(R.string.btn_continuar))
                            }
                        } else if (uiState.claveTemporal != null) {
                            BotonEnviarSms(
                                telefono = uiState.telefonoRecuperacion,
                                clave = uiState.claveTemporal,
                                onEnviado = {
                                    mostrarRecuperacion = false
                                    onLimpiar()
                                }
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                mostrarRecuperacion = false
                                onLimpiar()
                            }
                        ) {
                            Text(text = stringResource(R.string.btn_cancelar))
                        }
                    }
                )
            }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun viewLoginScreens(){
    ElectricAppTheme(true) {
        LoginScreens(
          uiState = FormularioLoginState(),
            onCorreoCambia = {} ,
            onPasswordCambia = {},
            onVisibilidad = {},
            onLimpiar = {},
            onGenerarClave = {},
            onIdRecuperacioCambia = {},
            onIniciar = {}
        )
    }
}
