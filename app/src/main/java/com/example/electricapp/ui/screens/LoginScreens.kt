package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.electricapp.R
import com.example.electricapp.model.FormularioLoginState
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CampoCorreo
import com.example.electricapp.ui.components.CampoPassword
import com.example.electricapp.ui.components.ImagenEncabezadoLogin
import com.example.electricapp.ui.theme.Dimens
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.viewModel.FormularioLoginViewModel

@Composable
fun ScreensLogin(
    viewModel : FormularioLoginViewModel = viewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreens(
        uiState = uiState,
        onCorreoCambia = viewModel::onCorreoCambia,
        onPasswordCambia = viewModel::onPasswordCambia,
        onIniciar = viewModel::onIniciar,
        onVisibilidad = viewModel::onVisibilidad
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreens(
    uiState : FormularioLoginState,
    onCorreoCambia : (String) -> Unit,
    onPasswordCambia : (String) -> Unit,
    onVisibilidad : () -> Unit,
    onIniciar : () -> Unit
){
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ){
            ImagenEncabezadoLogin(
                imagen = R.drawable.imagen_encabezado_login,
                titulo = stringResource(R.string.titulo_sesion)
            )
            Column(

                modifier = Modifier
                    .padding(Dimens.espacioPantalla),
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
            }
            Row(horizontalArrangement = Arrangement.spacedBy(Dimens.espacioMed)) {
                BotonInicio(
                    texto = stringResource(R.string.btn_iniciar_sesion),
                    onClick = onIniciar,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreensView(){

    ElectricAppTheme(false) {
        LoginScreens (

            uiState = FormularioLoginState(),
            onCorreoCambia = {},
            onPasswordCambia = {},
            onVisibilidad = {},
            onIniciar = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreensVie(){

    ElectricAppTheme(true) {
        LoginScreens (
            uiState = FormularioLoginState(),
            onCorreoCambia = {},
            onPasswordCambia = {},
            onVisibilidad = {},
            onIniciar = {}
        )
    }
}