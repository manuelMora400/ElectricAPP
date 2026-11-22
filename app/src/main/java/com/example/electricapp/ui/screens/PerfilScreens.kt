package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.electricapp.model.PerfilUsuario
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.components.BotonSecundario
import com.example.electricapp.ui.components.LogoPerfil
import com.example.electricapp.ui.components.TituloSeccion
import com.example.electricapp.ui.components.etiquetaRol
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.viewModel.PerfilViewModel

@Composable
fun PerfilScreens(
    viewModel: PerfilViewModel = viewModel(),
    idUsuario: Long,
    onVolver: () -> Unit,
    onEditarDatos: () -> Unit,
    onCambiarClave: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val perfil by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(idUsuario) {
        viewModel.cargar(idUsuario)
    }

    PerfilContenido(
        perfil = perfil,
        onVolver = onVolver,
        onEditarDatos = onEditarDatos,
        onCambiarClave = onCambiarClave,
        onCerrarSesion = onCerrarSesion
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilContenido(
    perfil: PerfilUsuario,
    onVolver: () -> Unit,
    onEditarDatos: () -> Unit,
    onCambiarClave: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.perfil_titulo)) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            painter = painterResource(R.drawable.ic_volver),
                            contentDescription = stringResource(R.string.volver)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(Dimens.espacioPantalla),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LogoPerfil(
                    iniciales = perfil.iniciales,
                    onClick = {},
                    tamano = 80.dp,
                    colorFondo = MaterialTheme.colorScheme.primaryContainer
                )
                Text(
                    text = perfil.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                etiquetaRol(
                    texto = stringResource(perfil.rol.texto),
                    colorFondo = MaterialTheme.colorScheme.primaryContainer,
                    colorTexto = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            TituloSeccion(stringResource(R.string.perfil_seccion_datos))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = EstilosTarjetas.coloresSecundario(),
                border = EstilosTarjetas.bordeado()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(Dimens.espacioMed),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DatoPerfil(stringResource(R.string.campo_correo), perfil.correo)
                    DatoPerfil(stringResource(R.string.campo_rut_registro), perfil.rut)
                    DatoPerfil(stringResource(R.string.campo_telefono_registro), perfil.telefono)

                    if (perfil.rol == Rol.CLIENTE && perfil.cargo.isNotBlank()) {
                        DatoPerfil(stringResource(R.string.campo_contacto_cargo), perfil.cargo)
                    }
                }
            }

            BotonSecundario(
                texto = stringResource(R.string.btn_editar_datos),
                onClick = onEditarDatos
            )

            BotonSecundario(
                texto = stringResource(R.string.btn_cambiar_clave),
                onClick = onCambiarClave
            )

            TextButton(
                onClick = onCerrarSesion,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.btn_cerrar_sesion),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun DatoPerfil(etiqueta: String, valor: String) {
    Column {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
