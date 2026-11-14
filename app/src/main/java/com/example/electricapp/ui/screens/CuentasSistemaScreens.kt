package com.example.electricapp.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.electricapp.R
import com.example.electricapp.model.FormularioRegistros
import com.example.electricapp.ui.components.Bienvenida
import com.example.electricapp.ui.components.UsuariosCards
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme
import com.example.electricapp.viewModel.FormularioRegistrosViewModel


@Composable
fun screensRegistro(
     viewModel : FormularioRegistrosViewModel = viewModel(),
     onAgregarCuenta : () -> Unit,
     onVolver: () -> Unit
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cuentas by viewModel.cuentasBD.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.cargarCuentas()
    }

    RegistrosScreens (
        uiState = uiState,
        cuentas = cuentas,
        onEstadoCambia = viewModel::onEstadoCambia,
        onAgregarCuenta = onAgregarCuenta,
        onVolver = onVolver
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrosScreens(

    uiState : FormularioRegistros,
    cuentas : List<FormularioRegistros>,
    onEstadoCambia :(String, Boolean) -> Unit,
    onAgregarCuenta : () -> Unit,
    onVolver : () -> Unit

){
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,

        //este bloque permitira dibujar un boton + flotante en la pantalla, alli usare registro cuentas
        // asi separamos pantallas con cada una de sus responsabilidades

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregarCuenta,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            ) {
                Icon(painter = painterResource(R.drawable.ic_agregar),
                    contentDescription = stringResource(R.string.agregar_cuenta))
            }

        },
        topBar = {
            TopAppBar(
                title = {Text(text = stringResource(R.string.titulo_cuentas_sistema))},
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            painter = painterResource(R.drawable.ic_volver),
                            contentDescription = stringResource(id = R.string.volver)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(innerPadding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioPantalla)

        ) {
            item {Bienvenida(
                texto = stringResource(R.string.titulo_administrar_cuentas),
                descripcion = stringResource(R.string.subtitulo_administrar_cuentas),
            )
            }
                items(cuentas, key = {it.correo}){
                        usuarios ->
                    UsuariosCards(
                        usuario = usuarios,
                        onEstadoCambia = { nuevoEstado -> onEstadoCambia(usuarios.correo, nuevoEstado)})
                }
            }

        }
    }

@Preview(showBackground = true)
@Composable
fun RegistrosScreensView(){

    ElectricAppTheme(true) {
        RegistrosScreens (
            uiState = FormularioRegistros(),
            cuentas = emptyList(),
            onEstadoCambia = {_, _ ->},
            onAgregarCuenta = {},
            onVolver = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrosScreensView2(){

    ElectricAppTheme(false) {
        RegistrosScreens (
            uiState = FormularioRegistros(),
            cuentas = emptyList(),
            onEstadoCambia = {_, _ ->},
            onAgregarCuenta = {},
            onVolver = {}
        )
    }
}
