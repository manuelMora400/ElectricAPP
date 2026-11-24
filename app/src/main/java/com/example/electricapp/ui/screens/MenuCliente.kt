package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.R
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.LogoPerfil
import com.example.electricapp.ui.components.TarjetaOpciones
import com.example.electricapp.ui.components.TarjetaSaludo
import com.example.electricapp.ui.components.TituloSeccion
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.PrimarioClaro
import com.example.electricapp.ui.theme.PrimarioOscuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuClienteScreens(
    nombreCliente: String = "Marcela",
    inicialesCliente: String = "MF",
    empresaCliente: String = "Envasadora del Pacífico",
    onClick: () -> Unit,
    onPerfil: () -> Unit = {},
    onCerrarSesion: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Induztek · Clientes",
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    LogoPerfil(
                        iniciales = inicialesCliente,
                        onClick = onPerfil,
                        modifier = Modifier.padding(end = Dimens.rowEspacio),
                        tamano = Dimens.logoBarra,
                        colorFondo = PrimarioClaro,
                        colorTexto = PrimarioOscuro
                    )
                },
                colors = EstilosBarra.colores(marino = true)
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(Dimens.espacioPantalla),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioGrilla)
        ) {
            TarjetaSaludo(
                titulo = "Hola, $nombreCliente",
                subtitulo = stringResource(R.string.subtitulo),
                rol = empresaCliente
            )

            TituloSeccion(stringResource(R.string.seccion_operacion))

            TarjetaOpciones(
                titulo = stringResource(R.string.titulo_historial_equipos),
                subtitulo = stringResource(R.string.subtitulo_historial_equipos),
                icono = R.drawable.ic_historial_equipo,
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            )

            TarjetaOpciones(
                titulo = stringResource(R.string.perfil_titulo),
                subtitulo = "Revisa los datos de tu cuenta y empresa",
                icono = R.drawable.ic_nombre,
                onClick = onPerfil,
                modifier = Modifier.fillMaxWidth()
            )

            BotonCerrarSesion(
                texto = stringResource(R.string.btn_cerrar_sesion),
                onClick = onCerrarSesion
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuClienteView() {
    ElectricAppTheme(true) {
        MenuClienteScreens(
            onClick = {},
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun MenuClienteView2() {
    ElectricAppTheme(false) {
        MenuClienteScreens(
            onClick = {},
        ) {}
    }
}