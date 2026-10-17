package com.example.electricapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun CamposFormularios(

    valor : String,
    onValorCambia : (String) -> Unit,
    etiquetaCampo : String,
    @DrawableRes icono : Int,
    modifier : Modifier = Modifier,
    error : String? = null,
    tipoTeclado : KeyboardType

){
    OutlinedTextField(
        value = valor,
        onValueChange = onValorCambia,
        modifier = Modifier.fillMaxWidth().padding(Dimens.espacioMed),
        label = {Text(etiquetaCampo)},
        leadingIcon = {
            Icon(
                painter = painterResource(icono),
                contentDescription = null,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
        },
        isError = error != null,
        supportingText ={if(error != null) Text(error)},
        keyboardOptions = KeyboardOptions(
            keyboardType = tipoTeclado,
            imeAction = ImeAction.Next
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CamposFormularioView(){
    ElectricAppTheme(false) {
        Column(
        ) {
            CamposFormularios(
                valor = "Manuel",
                onValorCambia = {},
                etiquetaCampo = "Rut",
                icono = R.drawable.ic_rut,
                Modifier.padding(60.dp),
                error = "Ingrese un rut valido",
                KeyboardType.Text
            )
            CamposFormularios(
                valor = "Manuel Mora",
                onValorCambia = {},
                etiquetaCampo = "Nombre completo",
                icono = R.drawable.ic_nombre_formulario,
                Modifier.padding(60.dp),
                error = null,
                KeyboardType.Text
            )
            CamposFormularios(
                valor = "manuel@gmail.com",
                onValorCambia = {},
                etiquetaCampo = "Correo electronico",
                icono = R.drawable.ic_correo,
                Modifier.padding(60.dp),
                error = null,
                KeyboardType.Text
            )
            CamposFormularios(
                valor = "+56 9 329392",
                onValorCambia = {},
                etiquetaCampo = "Telefono",
                icono = R.drawable.ic_telefono,
                Modifier.padding(60.dp),
                error = null,
                KeyboardType.Text
            )
            CamposFormularios(
                valor = "manuel",
                onValorCambia = {},
                etiquetaCampo = "Contraseña",
                icono = R.drawable.ic_candado,
                Modifier.padding(60.dp),
                error = null,
                KeyboardType.Password
            )
            CamposFormularios(
                valor = "Empresa",
                onValorCambia = {},
                etiquetaCampo = "Cargo",
                icono = R.drawable.ic_cargo,
                Modifier.padding(60.dp),
                error = null,
                KeyboardType.Text
            )
            CamposFormularios(
                valor = "nose",
                onValorCambia = {},
                etiquetaCampo = "contacto cargo",
                icono = R.drawable.ic_contacto_cargo,
                Modifier.padding(60.dp),
                error = null,
                KeyboardType.Text
            )
        }
    }
}