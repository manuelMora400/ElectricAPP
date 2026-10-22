package com.example.electricapp.ui.components

import android.R
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.ui.theme.Dimens

@Composable
fun CampoTag(
    valor: String,
    etiquetaTag: String,
    @DrawableRes iconoTag : Int,
    modifier: Modifier = Modifier,
    error: String? = null,
    onTagBuscar: (String) -> Unit,
    tecladoTipo : KeyboardType = KeyboardType.Text
){
    OutlinedTextField(
        value = valor,
        onValueChange = onTagBuscar,
        modifier = Modifier.fillMaxSize(),
        label = { Text(etiquetaTag) },
        leadingIcon ={
            Icon(
                painter = painterResource(id = iconoTag),
                contentDescription = null,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
        },
        isError = error != null,
        supportingText = { if(error != null) Text(error)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = tecladoTipo,
            imeAction = ImeAction.Next
        )

    )
}
@Preview(showBackground = true)
@Composable
fun ViewCampoTag(){

    CampoTag(
        valor = "PT-2026-...",
        onTagBuscar = {},
        etiquetaTag = "Busqueda por Tag",
        iconoTag = com.example.electricapp.R.drawable.ic_busqueda,
        modifier = Modifier.padding(23.dp),
        error = "Debe ingresar un Tag"
    )
    CampoTag(
        valor = "No se",
        onTagBuscar = {},
        etiquetaTag = "Busqueda por Tag",
        iconoTag = com.example.electricapp.R.drawable.ic_busqueda,
        modifier = Modifier.padding(23.dp),
        error = null
    )

}