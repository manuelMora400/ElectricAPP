package com.example.electricapp.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioLoginState
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CampoPassword
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Exito
import com.example.electricapp.viewModel.FormularioLoginViewModel

@Composable
fun screensCrearContrasena(
    viewModel : FormularioLoginViewModel = viewModel(),
    onClaveGuardada : () -> Unit
){
    val uistate by viewModel.uiState.collectAsStateWithLifecycle()
    CrearContrasenaScreens(
        uistate = uistate,
        onNuevaClave = viewModel::onNuevaClave,
        onConfirmarClave = viewModel::onConfirmarClave,
        onGuardarClave = { if(viewModel.onGuardarClaveNueva()) onClaveGuardada()} ,
        onVisibilidad = viewModel::onVisibilidad
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearContrasenaScreens(

    uistate : FormularioLoginState,
    onNuevaClave :  (String) -> Unit,
    onConfirmarClave : (String) -> Unit,
    onGuardarClave : () -> Unit,
    onVisibilidad : () -> Unit

) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cambiar_clave).trim(),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = EstilosBarra.colores()
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(Dimens.espacioMed),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)

        ) {
            Text(
                text = stringResource(R.string.titulo_cambia_clave, uistate.nombre),
                style = MaterialTheme.typography.titleMedium
            )

            CampoPassword(
                valor = uistate.nuevaClave,
                onContrasenaCambia = onNuevaClave,
                etiquetaContrasena = stringResource(R.string.campo_password),
                visible = uistate.passwordVisible,
                onAlternarVisibilidad = onVisibilidad,
                error = uistate.errorNuevaClave
            )

            ReglaContrasenas(
                cumplePassword = uistate.cumpleLargoPassword,
                texto = stringResource(R.string.cumple_largo)
            )
            ReglaContrasenas(
                cumplePassword = uistate.cumpleSiboloPassword,
                texto = stringResource(R.string.cumple_punto)
            )
            ReglaContrasenas(
                cumplePassword = uistate.cumpleMayusculaPassword,
                texto = stringResource(R.string.cumple_mayuscula)
            )

            CampoPassword(
                valor = uistate.confirmarClave,
                onContrasenaCambia = onConfirmarClave,
                etiquetaContrasena = stringResource(R.string.confirmar_password),
                visible = uistate.passwordVisible,
                onAlternarVisibilidad = onVisibilidad,
                error = uistate.errorConfirmarClave
            )

            BotonInicio(
                texto = stringResource(R.string.btn_confirmar_clave),
                onClick = onGuardarClave,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
private fun ReglaContrasenas(cumplePassword : Boolean, texto : String){


    val colores by animateColorAsState(
        targetValue = if(cumplePassword) Exito else MaterialTheme.colorScheme.error,
        label = "Regla contraseñas"
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text= if(cumplePassword) "ok" else "X",
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

@Preview(showBackground = true)
@Composable
fun crearContrasenaView(){
    ElectricAppTheme(true) {
        CrearContrasenaScreens(
          uistate = FormularioLoginState(),
            onNuevaClave = {},
            onGuardarClave = {},
            onConfirmarClave = {},
            onVisibilidad = {}
        )
    }
}