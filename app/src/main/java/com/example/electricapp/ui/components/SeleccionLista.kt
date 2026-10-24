package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.R
import com.example.electricapp.ui.styles.EstilosCampos
import com.example.electricapp.ui.theme.ElectricAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionLista(
    opcion : String,
    opciones : List<String>,
    texto : Int,
    onOpcionSeleccionada : (String) -> Unit,
    modifier : Modifier = Modifier,
    error : String? = null
){
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = {expandido = it}
    ) {
        OutlinedTextField(
            value = opcion,
            onValueChange = {},
            readOnly = true,
            label = {Text(stringResource(texto))},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)},
            isError = error != null,
            supportingText = {if(error != null) Text(error)},
            shape = EstilosCampos.forma,
            colors = EstilosCampos.colores(),
            modifier = modifier.fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ){
            opciones.forEach { elemento ->
                DropdownMenuItem(
                    text = {Text(elemento)},
                    onClick = {
                        onOpcionSeleccionada(elemento)
                        expandido = false
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SeleccionListaView(){
    ElectricAppTheme(false) {
        var elegir by remember { mutableStateOf("") }
        SeleccionLista(
            opcion = elegir,
            opciones = listOf("AT-BT", "AT-T", "BT-T"),
            texto = R.string.protocolo_campo_devanado,
            onOpcionSeleccionada = {elegir = it},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
