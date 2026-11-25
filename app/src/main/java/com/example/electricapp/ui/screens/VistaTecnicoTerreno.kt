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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.model.ContadoresVistaTecnico
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.LogoPerfil
import com.example.electricapp.ui.components.TarjetaOpciones
import com.example.electricapp.ui.components.TarjetaSaludo
import com.example.electricapp.ui.components.TituloSeccion
import com.example.electricapp.ui.components.tarjetaContadores
import com.example.electricapp.ui.styles.EstilosBarra
import com.example.electricapp.ui.styles.estiloAltoBoton
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.PrimarioClaro
import com.example.electricapp.ui.theme.PrimarioOscuro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaTecnicoScrees(
    estado : ContadoresVistaTecnico,
    nombreTecnico : String,
    onCrearProtocolos : () -> Unit,
    onMisProtocolos   : () -> Unit,
    onHistorialDeEquipo : () -> Unit,
    onInstrumentos : () -> Unit,
    onPerfil : () -> Unit,
    onCerrarSesion : () -> Unit
){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.banner_tecnico),
                        fontWeight = FontWeight.Bold)
                },
                actions = {
                    LogoPerfil(
                        iniciales = stringResource(R.string.logo_iniciales_tecnico),
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
                titulo = stringResource(R.string.titlo_Tecnico, nombreTecnico),
                subtitulo = stringResource(R.string.subtitulo),
                rol = stringResource(R.string.rol_tecnico)
            )

            Row (
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                tarjetaContadores(
                    valor = estado.misProtocolos,
                    etiqueta = stringResource(R.string.tecnico_contador_mis_protocolos),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                tarjetaContadores(
                    valor = estado.enObservacion,
                    etiqueta = stringResource(R.string.Consulta_Observacion),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                tarjetaContadores(
                    valor = estado.porFormalizar,
                    etiqueta = stringResource(R.string.tecnico_contador_por_formalizar),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }
            BotonInicio(
                texto = stringResource(R.string.btn_crear_protocolo),
                onClick = onCrearProtocolos,
                modifier = Modifier.estiloAltoBoton()
            )

            TituloSeccion(texto = stringResource(R.string.ing_seccion_accesos))

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.tecnico_mis_protocolos),
                    subtitulo = stringResource(R.string.tecnico_ver_estados),
                    icono = R.drawable.ic_revisar_protocolos,
                    onClick = onMisProtocolos,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )

                TarjetaOpciones(
                    titulo = stringResource(R.string.perfil_titulo),
                    subtitulo = stringResource(R.string.subtitulo_perfil),
                    icono = R.drawable.ic_gestion_usuarios,
                    onClick = onPerfil,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.tecnico_titulo_historial_equipo),
                    subtitulo = stringResource(R.string.tecnico_subtitulo_historial_equipo),
                    icono = R.drawable.ic_busqueda,
                    onClick = onHistorialDeEquipo,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.instrumentos_tecnico),
                    subtitulo = stringResource(R.string.subtitulo_tecnico_instrumento),
                    icono = R.drawable.ic_calibracion_instrumento,
                    onClick = onInstrumentos,
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
fun viewVistaTecnico(){

    ElectricAppTheme(true) {
        VistaTecnicoScrees(
            estado = ContadoresVistaTecnico(4,2,1),
            nombreTecnico = "Manue Mora",
            onCrearProtocolos = {},
            onMisProtocolos = {},
            onHistorialDeEquipo = {},
            onInstrumentos = {},
            onPerfil = {},
            onCerrarSesion = {}
        )
    }
}