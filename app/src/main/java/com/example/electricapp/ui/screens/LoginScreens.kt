package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.electricapp.ui.components.ImagenEncabezadoLogin
import com.example.electricapp.ui.theme.Dimens
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Exito
import com.example.electricapp.ui.theme.ExitoFondo
import com.example.electricapp.viewModel.FormularioLoginViewModel

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
        onOlvido = {}
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
    onOlvido : () -> Unit
){

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
            }
                BotonInicio(
                    texto = stringResource(R.string.btn_iniciar_sesion),
                    onClick = onIniciar,
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                )
            if(uiState.logeadoExitoso){
                logeadoExito(uiState.mensajeExitoso)
            }

            TextButton(
                onClick = onOlvido,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.btn_olvido_password),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Composable
 fun logeadoExito(nombre : String){

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = EstilosTarjetas.elevacion(),
        border = EstilosTarjetas.bordeado(),
        shape = EstilosTarjetas.forma,
        colors = CardDefaults.cardColors(containerColor = ExitoFondo, contentColor = Exito)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_exito),
            contentDescription = null,
            tint = Exito,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.logeado_exito),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(Dimens.espacioPantalla)
        )
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
            onIniciar = {},
            onOlvido = {}
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
            onIniciar = {},
            onOlvido = {}
        )
    }
}