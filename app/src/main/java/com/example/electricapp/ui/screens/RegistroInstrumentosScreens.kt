package com.example.electricapp.ui.screens


import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioInstrumentosStates
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.BotonInicio
import com.example.electricapp.ui.components.CamposFormularios
import com.example.electricapp.ui.components.EncabezadoVistaJefe
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.ui.theme.Exito
import com.example.electricapp.ui.theme.ExitoFondo
import com.example.electricapp.viewModel.FormularioInstrumentosViewModel

@Composable
fun screensRegistrosInstrumentos(

    viewModel: FormularioInstrumentosViewModel = viewModel(),
    onRegistro : () -> Unit,
    onActualizado: () -> Unit
){
    val Uistate by viewModel.uiState.collectAsStateWithLifecycle()

    RegistroInstrumentosScreens(
        uistate = Uistate,
        onNombreCambia = viewModel::onNombreCambia,
        onMarcaCambia = viewModel::onMarcaCambia,
        onModeloCambia = viewModel::onModeloCambia,
        onNumSerieCambia = viewModel::onNumSerieCambia,
        onFechaCalibracionCambia = viewModel::onFechaCalifracionCambia,
        onFechaVencimientoCambia = viewModel::onFechaVencimientoCambia,
        onRegistrar = {
            val registradoExito = viewModel.onRegistrar()
            if(registradoExito){
                onRegistro()
            }
        },
        onLimpiar = viewModel::onLimpiar,
        onActualizar = {
            val actualizado = viewModel.onActualizar()
            if(actualizado){
                onActualizado()
            }
        }
    )
}

@Composable
fun RegistroInstrumentosScreens(

    uistate : FormularioInstrumentosStates,
    onNombreCambia : (String) -> Unit,
    onMarcaCambia  : (String) -> Unit,
    onModeloCambia : (String) -> Unit,
    onNumSerieCambia : (String) -> Unit,
    onFechaCalibracionCambia : (String) -> Unit,
    onFechaVencimientoCambia : (String) -> Unit,
    onRegistrar : () -> Unit,
    onLimpiar : () -> Unit,
    onActualizar : () -> Unit
){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding)
                        .fillMaxWidth()
        ) {
            item {
                EncabezadoVistaJefe(
                    titulo = stringResource(R.string.titulo_Empresa),
                    subTitulo = stringResource(R.string.subtitulo_empresa),
                    icono = R.drawable.logo_inicio_sesion
                )
            }
            item {
                Bienvenida(
                    texto = stringResource(R.string.titulo_Instrumentos_calibracion),
                    descripcion = stringResource(R.string.subtitulo_Instrumentos_calibracion)
                )
            }

            item { Card(
                modifier = Modifier.fillMaxWidth().padding(Dimens.espacioPantalla),
                colors = EstilosTarjetas.coloresSecundario(),
                border = EstilosTarjetas.bordeado(),
                elevation = EstilosTarjetas.elevacion()
            ) {

                CamposFormularios(
                    valor = uistate.nombre,
                    onValorCambia = onNombreCambia,
                    etiquetaCampo = stringResource(R.string.campo_nombre_instrumentos),
                    icono = R.drawable.ic_calibracion_instrumento,
                    modifier = Modifier.padding(),
                    error = uistate.errorNombre,
                    tipoTeclado = KeyboardType.Text
                )

                CamposFormularios(
                    valor = uistate.marca,
                    onValorCambia = onMarcaCambia,
                    etiquetaCampo = stringResource(R.string.campo_marca_instrumentos),
                    icono = R.drawable.ic_marca_instrumentos,
                    modifier = Modifier.padding(),
                    error = uistate.errorMarca,
                    tipoTeclado = KeyboardType.Text
                )

                CamposFormularios(
                    valor = uistate.modelo,
                    onValorCambia = onModeloCambia,
                    etiquetaCampo = stringResource(R.string.campo_modelo_Instrumentos),
                    icono = R.drawable.ic_modelo_instrumento,
                    modifier = Modifier.padding(),
                    error = uistate.errorModelo,
                    tipoTeclado = KeyboardType.Text
                )

                CamposFormularios(
                    valor = uistate.numSerie,
                    onValorCambia = onNumSerieCambia,
                    etiquetaCampo = stringResource(R.string.campo_numero_serie_instrumentos),
                    icono = R.drawable.ic_numero_serie,
                    modifier = Modifier.padding(),
                    error = uistate.errorNumeroSerie,
                    tipoTeclado = KeyboardType.Text
                )

                Bienvenida(
                    texto = stringResource(R.string.titulo_instruccion_Instrumentos),
                    descripcion = stringResource(R.string.instrucciones_para_calibrar)
                )

                CamposFormularios(
                    valor = uistate.fechaCalibracion,
                    onValorCambia = onFechaCalibracionCambia,
                    etiquetaCampo = stringResource(R.string.campo_fecha_calibracion_instrumento),
                    icono = R.drawable.ic_fechas,
                    modifier = Modifier.padding(),
                    error = uistate.errorFechaCalibracion,
                    tipoTeclado = KeyboardType.Text
                )


                CamposFormularios(
                    valor = uistate.fechaVencimiento,
                    onValorCambia = onFechaVencimientoCambia,
                    etiquetaCampo = stringResource(R.string.campo_fecha_vencimiento_instumento),
                    icono = R.drawable.ic_fechas,
                    modifier = Modifier.padding(),
                    error = uistate.errorFechaVencimiento,
                    tipoTeclado = KeyboardType.Text
                )
                Row(
                    modifier = Modifier.padding(Dimens.espacioMed),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.espacioChico)
                ){
                    BotonInicio(
                        texto = stringResource(R.string.btn_registrar_instrumento),
                        onClick = onRegistrar
                    )
                }

                Row(
                    modifier = Modifier.padding(Dimens.espacioMed),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.espacioChico)
                ){
                    BotonInicio(
                        texto = stringResource(R.string.btn_limpiar),
                        onClick = onLimpiar
                    )
                }

                Row(
                    modifier = Modifier.padding(Dimens.espacioMed),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.espacioChico)
                ){
                    BotonInicio(
                        texto = stringResource(R.string.btn_actualizar_instrumento),
                        onClick = onActualizar,
                        modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary)
                    )
                }

            }
            }

            if(uistate.mensajeExito){
                item {
                    mensajeExitoso(uistate.mensajeExitososo)
                }
            }

            item {
                Bienvenida(
                    texto = stringResource(R.string.visualizar_instrumentos),
                    descripcion = stringResource(R.string.informacion_instrumentos)
                )
            }
        }
    }
}
@Composable
fun mensajeExitoso(mensaje : String){

    Card(
        modifier = Modifier.fillMaxSize(1f).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ExitoFondo, contentColor = Exito),
        border = EstilosTarjetas.bordeado(),
        elevation = EstilosTarjetas.elevacion(),
        shape = EstilosTarjetas.forma
    ) {

        Icon(
            painter = painterResource(R.drawable.ic_exito),
            contentDescription = null,
            tint = Exito,
            modifier = Modifier.size(Dimens.tamanoIcono)
        )

        Text(
            text = stringResource(R.string.mensaje_registrado_exito_instrumentos),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(Dimens.espacioPantalla)
        )

    }

}

@Preview(showBackground = true)
@Composable
fun RegistramentosInstrumentosScreenView(){
    ElectricAppTheme(true) {

        RegistroInstrumentosScreens(
            uistate = FormularioInstrumentosStates(),
            onNombreCambia = {},
            onMarcaCambia = {},
            onModeloCambia = {},
            onNumSerieCambia = {},
            onFechaCalibracionCambia = {},
            onFechaVencimientoCambia = {},
            onRegistrar = {},
            onLimpiar = {},
            onActualizar = {}
        )
    }
}