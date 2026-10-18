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
import com.example.electricapp.model.Rol
import com.example.electricapp.ui.styles.EstilosCampos
import com.example.electricapp.ui.theme.ElectricAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionPerfilRegistro(
    rol : Rol = Rol.CLIENTE, // por defaul dejaremos por cliente
    texto : Int,
    onRolSeleccionado : (Rol) -> Unit,
    modifier : Modifier = Modifier
){
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = {expandido = it}
    ) {
        OutlinedTextField(
            value = stringResource(rol.texto),
            onValueChange = {},
            readOnly = true,
            label = {Text(stringResource(texto))},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)},
            shape = EstilosCampos.forma,
            colors = EstilosCampos.colores(),
            modifier = modifier.fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ){
            Rol.entries.forEach { roles ->
                DropdownMenuItem(
                    text = {Text(stringResource(roles.texto))},
                    onClick = {
                        onRolSeleccionado(roles)
                        expandido = false
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SeleccionPerfilRegistroView(){
    ElectricAppTheme(false) {
        var elegir by remember { mutableStateOf(Rol.JEFE_TECNICO) }
        SeleccionPerfilRegistro(
            rol = elegir,
            R.string.campo_perfil_registro,
            {elegir = it},
            Modifier.fillMaxWidth()
        )
    }
}
