package com.example.electricapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.ContadoresPanelJefe
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.TarjetaOpciones
import com.example.electricapp.ui.components.tarjetaContadores
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Primario
import com.example.electricapp.viewModel.JefeTecnicoViewModel

// ---------- Con ViewModel (la que llama la navegación) ----------

@Composable
fun VistaJefeScreens(
    viewModel: JefeTecnicoViewModel = viewModel(),
    onHistorial: () -> Unit,
    onRevisarProtocolos: () -> Unit,
    onAnularProtocolos: () -> Unit,
    onRegistrarClientes: () -> Unit,
    onCrearProtocolos: () -> Unit,
    onGestionUsuarios: () -> Unit,
    onInstrumentos: () -> Unit,
    onDesactivarCuentas: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val estado by viewModel.uistate.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.cargarResumenes() }

    VistaJefeContenido(
        estado = estado,
        onHistorial = onHistorial,
        onRevisarProtocolos = onRevisarProtocolos,
        onAnularProtocolos = onAnularProtocolos,
        onRegistrarClientes = onRegistrarClientes,
        onCrearProtocolos = onCrearProtocolos,
        onGestionUsuarios = onGestionUsuarios,
        onInstrumentos = onInstrumentos,
        onDesactivarCuentas = onDesactivarCuentas,
        onCerrarSesion = onCerrarSesion
    )
}

// ---------- Con estado (la que usan los previews) ----------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaJefeContenido(
    estado: ContadoresPanelJefe,
    onHistorial: () -> Unit,
    onRevisarProtocolos: () -> Unit,
    onAnularProtocolos: () -> Unit,
    onRegistrarClientes: () -> Unit,
    onCrearProtocolos: () -> Unit,
    onGestionUsuarios: () -> Unit,
    onInstrumentos: () -> Unit,
    onDesactivarCuentas: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.topbar_jefe)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primario,
                    titleContentColor = Color.White
                )
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

            Bienvenida(
                texto = stringResource(R.string.titulo_rol),
                descripcion = stringResource(R.string.subtitulo)
            )

            // Indicadores: el número va en "valor", la etiqueta es texto simple
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_cuentas_jefe),
                    subtitulo = stringResource(R.string.jefe_cuentas_sub, estado.cuentasActivas),
                    icono = R.drawable.ic_gestion_usuarios,
                    colorDeIcono = Color(0xFF6A3FA0),
                    onClick = onGestionUsuarios,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_instrumentos_jefe),
                    subtitulo = stringResource(R.string.jefe_instrumentos_sub, estado.instrumentosRegistrados),
                    icono = R.drawable.ic_calibracion_instrumento,
                    colorDeIcono = Color(0xFFC2185B),
                    onClick = onInstrumentos,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_registrar_clientes),
                    subtitulo = stringResource(R.string.subtitulo_registro_cliente),
                    icono = R.drawable.ic_registrar_client_equipo,
                    colorDeIcono = Color(0xFF455A9E),
                    onClick = onRegistrarClientes,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_desactivar_personal),
                    subtitulo = stringResource(R.string.subtitulo_desactivar_personal),
                    icono = R.drawable.ic_desactivar_personal,
                    colorDeIcono = Color(0xFFE65100),
                    onClick = onDesactivarCuentas,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }

            TituloSeccion(stringResource(R.string.seccion_operacion))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_historial_equipos),
                    subtitulo = stringResource(R.string.subtitulo_historial_equipos),
                    icono = R.drawable.ic_historial_equipo,
                    colorDeIcono = Color(0xFF7E57C2),
                    onClick = onHistorial,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_anular_protocolos),
                    subtitulo = stringResource(R.string.subtitulo_anular_protocolos),
                    icono = R.drawable.ic_anular_protocolos,
                    colorDeIcono = Color(0xFFD32F2F),
                    onClick = onAnularProtocolos,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_revisar_protocolos),
                    subtitulo = stringResource(R.string.subtitulo_revisar_protocolos),
                    icono = R.drawable.ic_revisar_protocolos,
                    colorDeIcono = Color(0xFF00796B),
                    onClick = onRevisarProtocolos,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_crear_protocolos),
                    subtitulo = stringResource(R.string.subtitulo_crear_protocolos),
                    icono = R.drawable.ic_crear_protocolos,
                    colorDeIcono = Color(0xFF1565C0),
                    onClick = onCrearProtocolos,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
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
fun VistaJefeView() {
    ElectricAppTheme(true) {
        VistaJefeContenido(
            estado = ContadoresPanelJefe(cuentasActivas = 7, instrumentosRegistrados = 3),
            onHistorial = {}, onRevisarProtocolos = {}, onAnularProtocolos = {},
            onRegistrarClientes = {}, onCrearProtocolos = {}, onGestionUsuarios = {},
            onInstrumentos = {}, onDesactivarCuentas = {}, onCerrarSesion = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VistaJefeView2() {
    ElectricAppTheme(false) {
        VistaJefeContenido(
            estado = ContadoresPanelJefe(cuentasActivas = 7, instrumentosRegistrados = 3),
            onHistorial = {}, onRevisarProtocolos = {}, onAnularProtocolos = {},
            onRegistrarClientes = {}, onCrearProtocolos = {}, onGestionUsuarios = {},
            onInstrumentos = {}, onDesactivarCuentas = {}, onCerrarSesion = {}
        )
    }
}