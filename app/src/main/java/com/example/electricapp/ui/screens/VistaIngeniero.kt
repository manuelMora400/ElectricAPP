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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.model.ContadoresPanelIngeniero
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.TarjetaOpciones
import com.example.electricapp.ui.components.tarjetaContadores
import com.example.electricapp.ui.styles.estiloAltoBoton
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Primario
import com.example.electricapp.R
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaIngenieroContenido(
    estado: ContadoresPanelIngeniero,
    nombreIngeniero: String,
    onRevisarProtocolos: () -> Unit,
    onHistorial: () -> Unit,
    onInstrumentos: () -> Unit,
    onPerfil: () -> Unit,
    onFormalizados: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.ing_topbar)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primario,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(Dimens.espacioPantalla),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Bienvenida(
                texto = stringResource(R.string.titulo_bienvenida, nombreIngeniero),
                descripcion = stringResource(R.string.rol_ingeniero)
            )

            Row (
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                tarjetaContadores(
                    valor = estado.porRevisar,
                    etiqueta = stringResource(R.string.ing_por_revisar),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                tarjetaContadores(
                    valor = estado.enObservacion,
                    etiqueta = stringResource(R.string.Consulta_Observacion),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                tarjetaContadores(
                    valor = estado.formalizados,
                    etiqueta = stringResource(R.string.ing_formalizados),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }

            BotonInicio(
                texto = stringResource(R.string.ing_btn_revisar, estado.porRevisar),
                onClick = onRevisarProtocolos,
                modifier = Modifier.estiloAltoBoton()
            )

            Text(
                text = stringResource(R.string.ing_seccion_accesos).uppercase(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_historial_equipos),
                    subtitulo = stringResource(R.string.subtitulo_historial_equipos),
                    icono = R.drawable.ic_historial_equipo,
                    onClick = onHistorial,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_instrumentos_jefe),
                    subtitulo = stringResource(R.string.ing_instrumentos_sub),
                    icono = R.drawable.ic_calibracion_instrumento,
                    onClick = onInstrumentos,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.ing_perfil),
                    subtitulo = stringResource(R.string.ing_perfil_sub),
                    icono = R.drawable.ic_nombre,
                    onClick = onPerfil,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.ing_formalizados),
                    subtitulo = stringResource(R.string.ing_formalizados_sub),
                    icono = R.drawable.ic_exito,
                    onClick = onFormalizados,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }

            BotonCerrarSesion(
                texto = stringResource(R.string.btn_cerrar_sesion),
                onClick = onCerrarSesion,
                icono = R.drawable.ic_cerrar_sesion
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaIngenieroView() {
    ElectricAppTheme(false) {
        VistaIngenieroContenido(
            estado = ContadoresPanelIngeniero(porRevisar = 2, enObservacion = 1, formalizados = 2),
            nombreIngeniero = "Andrea",
            onRevisarProtocolos = {}, onHistorial = {}, onInstrumentos = {},
            onPerfil = {}, onFormalizados = {}, onCerrarSesion = {}
        )
    }
}