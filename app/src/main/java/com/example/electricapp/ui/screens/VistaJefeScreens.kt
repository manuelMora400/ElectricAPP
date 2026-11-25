package com.example.electricapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.ContadoresPanelJefe
import com.example.electricapp.ui.components.LogoPerfil
import com.example.electricapp.ui.components.TarjetaOpciones
import com.example.electricapp.ui.components.TarjetaSaludo
import com.example.electricapp.ui.components.TituloSeccion
import com.example.electricapp.ui.components.tarjetaContadores
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.PrimarioClaro
import com.example.electricapp.ui.theme.PrimarioOscuro
import com.example.electricapp.viewModel.JefeTecnicoViewModel

@Composable
fun VistaJefeScreens(
    viewModel: JefeTecnicoViewModel = viewModel(),
    onHistorial: () -> Unit,
    onAnularProtocolos: () -> Unit,
    onCrearProtocolos: () -> Unit,
    onGestionUsuarios: () -> Unit,
    onInstrumentos: () -> Unit,
    onPerfil: () -> Unit,
    onEquipos: () -> Unit
) {
    val estado by viewModel.uistate.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.cargarResumenes() }

    VistaJefeContenido(
        estado = estado,
        onHistorial = onHistorial,
        onAnularProtocolos = onAnularProtocolos,
        onCrearProtocolos = onCrearProtocolos,
        onGestionUsuarios = onGestionUsuarios,
        onInstrumentos = onInstrumentos,
        onPerfil = onPerfil,
        onEquipos = onEquipos
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaJefeContenido(
    estado: ContadoresPanelJefe,
    onHistorial: () -> Unit,
    onAnularProtocolos: () -> Unit,
    onCrearProtocolos: () -> Unit,
    onGestionUsuarios: () -> Unit,
    onInstrumentos: () -> Unit,
    onPerfil : () -> Unit,
    onEquipos : () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.topbar_jefe),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                actions = {
                    LogoPerfil(
                        iniciales = stringResource(R.string.logo_iniciales_jefe),
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
                titulo = stringResource(R.string.titulo_rol),
                subtitulo = stringResource(R.string.subtitulo),
                rol = stringResource(R.string.rol_jefe_tecnico)
            )

            TituloSeccion(stringResource(R.string.jefe_cantidad_admin))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(Dimens.espacioGrilla)
            ) {
                tarjetaContadores(
                    valor = estado.cuentasActivas,
                    etiqueta = stringResource(R.string.jefe_cuentas),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                tarjetaContadores(
                    valor = estado.instrumentosRegistrados,
                    etiqueta = stringResource(R.string.jefe_instrumentos),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }

            TituloSeccion(stringResource(R.string.seccion_administracion))
            GrillaOpciones(
                listOf(
                    OpcionPanel(
                        titulo = stringResource(R.string.titulo_cuentas_jefe),
                        subtitulo = stringResource(R.string.jefe_cuentas_sub, estado.cuentasActivas),
                        icono = R.drawable.ic_gestion_usuarios,
                        onClick = onGestionUsuarios
                    ),
                    OpcionPanel(
                        titulo = stringResource(R.string.titulo_instrumentos_jefe),
                        subtitulo = stringResource(R.string.jefe_instrumentos_sub, estado.instrumentosRegistrados),
                        icono = R.drawable.ic_calibracion_instrumento,
                        onClick = onInstrumentos
                    ),
                    OpcionPanel(
                        titulo = stringResource(R.string.perfil_titulo),
                        subtitulo = stringResource(R.string.subtitulo_perfil),
                        icono = R.drawable.ic_nombre,
                        onClick = onPerfil
                    ),
                    OpcionPanel(
                        titulo = stringResource(R.string.titulo_equipos_jefe),
                        subtitulo = stringResource(R.string.subtitulo_equipos_jefe),
                        icono = R.drawable.ic_registrar_client_equipo,
                        onClick = onEquipos
                    )
                )
            )

            TituloSeccion(stringResource(R.string.seccion_operacion))
            GrillaOpciones(
                listOf(
                    OpcionPanel(
                        titulo = stringResource(R.string.titulo_historial_equipos),
                        subtitulo = stringResource(R.string.subtitulo_historial_equipos),
                        icono = R.drawable.ic_historial_equipo,
                        onClick = onHistorial
                    ),
                    OpcionPanel(
                        titulo = stringResource(R.string.titulo_anular_protocolos),
                        subtitulo = stringResource(R.string.subtitulo_anular_protocolos),
                        icono = R.drawable.ic_anular_protocolos,
                        onClick = onAnularProtocolos
                    ),
                    OpcionPanel(
                        titulo = stringResource(R.string.titulo_crear_protocolos),
                        subtitulo = stringResource(R.string.subtitulo_crear_protocolos),
                        icono = R.drawable.ic_crear_protocolos,
                        onClick = onCrearProtocolos
                    )
                )
            )
        }
    }
}

private data class OpcionPanel(
    val titulo: String,
    val subtitulo: String,
    @DrawableRes val icono: Int,
    val onClick: () -> Unit
)

@Composable
private fun GrillaOpciones(opciones: List<OpcionPanel>) {
    Column(verticalArrangement = Arrangement.spacedBy(Dimens.espacioGrilla)) {
        opciones.chunked(2).forEach { fila ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(Dimens.espacioGrilla)
            ) {
                fila.forEach { opcion ->
                    TarjetaOpciones(
                        titulo = opcion.titulo,
                        subtitulo = opcion.subtitulo,
                        icono = opcion.icono,
                        onClick = opcion.onClick,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
                if (fila.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaJefeView() {
    ElectricAppTheme(true) {
        VistaJefeContenido(
            estado = ContadoresPanelJefe(cuentasActivas = 7, instrumentosRegistrados = 3),
            onHistorial = {},
            onAnularProtocolos = {},
            onCrearProtocolos = {},
            onGestionUsuarios = {},
            onInstrumentos = {},
            onPerfil = {},
            onEquipos = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VistaJefeView2() {
    ElectricAppTheme(false) {
        VistaJefeContenido(
            estado = ContadoresPanelJefe(cuentasActivas = 7, instrumentosRegistrados = 3),
            onHistorial = {},
            onAnularProtocolos = {},
            onCrearProtocolos = {},
            onGestionUsuarios = {},
            onInstrumentos = {},
            onPerfil = {},
            onEquipos = {}
        )
    }
}
