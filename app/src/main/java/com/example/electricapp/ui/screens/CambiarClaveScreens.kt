package com.example.electricapp.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.CambiarClaveState
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CampoPassword
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.Exito
import com.example.electricapp.viewModel.CambiarClaveViewModel

@Composable
fun CambiarClaveScreens(
    viewModel: CambiarClaveViewModel = viewModel(),
    idUsuario: Long,
    onVolver: () -> Unit,
    onClaveGuardada: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CambiarClaveContenido(
        uiState = uiState,
        onClaveActual = viewModel::onClaveActual,
        onNuevaClave = viewModel::onNuevaClave,
        onConfirmarClave = viewModel::onConfirmarClave,
        onVisibilidad = viewModel::onVisibilidad,
        onVolver = onVolver,
        onGuardarClave = {viewModel.onGuardarClaveNueva(idUsuario, onClaveGuardada)}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CambiarClaveContenido(
    uiState: CambiarClaveState,
    onClaveActual: (String) -> Unit,
    onNuevaClave: (String) -> Unit,
    onConfirmarClave: (String) -> Unit,
    onVisibilidad: () -> Unit,
    onVolver: () -> Unit,
    onGuardarClave: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.btn_cambiar_clave),
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(Dimens.espacioMed),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
        ) {

            CampoPassword(
                valor = uiState.claveActual,
                onContrasenaCambia = onClaveActual,
                etiquetaContrasena = stringResource(R.string.perfil_clave_actual),
                visible = uiState.passwordVisible,
                onAlternarVisibilidad = onVisibilidad,
                error = uiState.errorClaveActual
            )

            CampoPassword(
                valor = uiState.nuevaClave,
                onContrasenaCambia = onNuevaClave,
                etiquetaContrasena = stringResource(R.string.perfil_clave_nueva),
                visible = uiState.passwordVisible,
                onAlternarVisibilidad = onVisibilidad,
                error = uiState.errorNuevaClave
            )

            ReglaClave(
                cumplePassword = uiState.cumpleLargoPassword,
                texto = stringResource(R.string.cumple_largo).trim()
            )
            ReglaClave(
                cumplePassword = uiState.cumpleSimboloPassword,
                texto = stringResource(R.string.cumple_punto).trim()
            )
            ReglaClave(
                cumplePassword = uiState.cumpleMayusculaPassword,
                texto = stringResource(R.string.cumple_mayuscula).trim()
            )

            CampoPassword(
                valor = uiState.confirmarClave,
                onContrasenaCambia = onConfirmarClave,
                etiquetaContrasena = stringResource(R.string.confirmar_password).trim(),
                visible = uiState.passwordVisible,
                onAlternarVisibilidad = onVisibilidad,
                error = uiState.errorConfirmarClave
            )

            val errorGeneral = uiState.errorGeneral
            if (errorGeneral != null) {
                Text(
                    text = errorGeneral,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }

            BotonInicio(
                texto = stringResource(R.string.perfil_btn_actualizar_clave),
                onClick = onGuardarClave,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ReglaClave(cumplePassword : Boolean, texto : String){

    val colores by animateColorAsState(
        targetValue = if(cumplePassword) Exito else MaterialTheme.colorScheme.error,
        label = "Regla contraseñas"
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = if(cumplePassword) "ok" else "X",
            color = colores,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = texto,
            color = colores,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
