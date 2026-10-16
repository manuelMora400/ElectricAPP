package com.example.electricapp.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.R
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonCerrarSesion
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.components.TarjetaOpciones
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VistaJefeScreens(
    onClick : () -> Unit
){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPading ->

        Column(
             modifier =  Modifier.fillMaxSize()
                        .padding(innerPading)
                        .verticalScroll(rememberScrollState())
        ) {
            EncabezadoVistaJefe(
                titulo = stringResource(R.string.titulo_Empresa),
                subTitulo = stringResource(R.string.subtitulo_empresa),
                icono = R.drawable.logo_inicio_sesion
            )
            Column(
                modifier = Modifier.padding(Dimens.espacioPantalla),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Bienvenida(
                    texto = stringResource(R.string.titulo_rol),
                    descripcion = stringResource(R.string.subtitulo)
                )
            Column(
                modifier = Modifier.padding(Dimens.espacioPantalla),
                verticalArrangement = Arrangement.spacedBy(Dimens.espacioTitulo)
            ) {
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_gestionar_usuarios),
                    subtitulo = stringResource(R.string.subtitulo_gestionar_usuarios),
                    icono = R.drawable.ic_gestion_usuarios,
                    modifier = Modifier.padding(),
                    onClick = onClick
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_historial_equipos),
                    subtitulo = stringResource(R.string.subtitulo_historial_equipos),
                    icono = R.drawable.ic_historial_equipo,
                    modifier = Modifier.padding(),
                    onClick = onClick
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_revisar_protocolos),
                    subtitulo = stringResource(R.string.subtitulo_revisar_protocolos),
                    icono = R.drawable.ic_revisar_protocolos,
                    modifier = Modifier.padding(),
                    onClick = onClick
                )

                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_instrumentos_calibracion),
                    subtitulo = stringResource(R.string.subtitulo_instrumentos_calibracion),
                    icono = R.drawable.ic_calibracion_instrumento,
                    modifier = Modifier.padding(),
                    onClick = onClick
                )

                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_anular_protocolos),
                    subtitulo = stringResource(R.string.subtitulo_anular_protocolos),
                    icono = R.drawable.ic_anular_protocolos,
                    modifier = Modifier.padding(),
                    onClick = onClick
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_registrar_clientes),
                    subtitulo = stringResource(R.string.subtitulo_registro_cliente),
                    icono = R.drawable.ic_registrar_client_equipo,
                    modifier = Modifier.padding(),
                    onClick = onClick
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_crear_protocolos),
                    subtitulo = stringResource(R.string.subtitulo_crear_protocolos),
                    icono = R.drawable.ic_crear_protocolos,
                    modifier = Modifier.padding(),
                    onClick = onClick
                )
                TarjetaOpciones(
                    titulo = stringResource(R.string.titulo_desactivar_personal),
                    subtitulo = stringResource(R.string.subtitulo_desactivar_personal),
                    icono = R.drawable.ic_desactivar_personal,
                    modifier = Modifier.padding(),
                    onClick = onClick
                )
               Row(horizontalArrangement = Arrangement.Center) {
                   BotonCerrarSesion(
                       texto = stringResource(R.string.btn_cerrar_sesion),
                       onClick = onClick
                   )
               }
            }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun VistaJefeView(){
    ElectricAppTheme(true) {
        VistaJefeScreens (
            onClick = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
fun VistaJefeView2(){
    ElectricAppTheme(false) {
        VistaJefeScreens (
            onClick = {}
        )
    }
}