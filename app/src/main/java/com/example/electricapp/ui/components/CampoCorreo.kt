package com.example.electricapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
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


@Composable
fun CampoCorreo(
    valor : String,
    onValorCambia : (String) -> Unit,
    etiquetaCorreo : String,
    @DrawableRes iconoCorreo : Int,
    modifier: Modifier = Modifier,
    error : String? = null,
    tecladoTipo : KeyboardType = KeyboardType.Text
){
    OutlinedTextField(

        value = valor,
        onValueChange = onValorCambia,
        modifier = Modifier.fillMaxWidth(),
        label = {Text(etiquetaCorreo)},
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconoCorreo),
                contentDescription = null,
                modifier = Modifier.size(Dimens.tamanoLogo)
            )
        },
        isError = error != null,
        supportingText = {if(error != null)Text(error)},
        keyboardOptions = KeyboardOptions(
            keyboardType = tecladoTipo,
            imeAction = ImeAction.Next
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun CampoCorreoView(){
    CampoCorreo(
        valor = "manuel@gmail.com",
        onValorCambia = {},
        etiquetaCorreo = "Correo electronico",
        iconoCorreo = R.drawable.ic_correo,
        modifier = Modifier.size(8.dp),
        error = "Ingresa un correo valido"
    )
}