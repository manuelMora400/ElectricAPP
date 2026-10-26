package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.theme.Dimens

@Composable
fun CampoPassword(

    valor : String,
    onContrasenaCambia : (String) -> Unit,
    etiquetaContrasena : String,
    visible : Boolean = false,
    onAlternarVisibilidad : () -> Unit,
    modifier: Modifier = Modifier,
    error : String? = null)
{
    OutlinedTextField(
        value = valor,
        onValueChange = onContrasenaCambia,
        modifier = Modifier.fillMaxWidth(),
        label = {Text(etiquetaContrasena)},
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_candado),
                contentDescription = null,
                modifier = Modifier.size(Dimens.tamanoIcono)
            )
        },
        trailingIcon = {
            IconButton(onClick = onAlternarVisibilidad) {
                Icon(
                    painter = painterResource(
                        if(visible) R.drawable.ic_ojo else R.drawable.ic_ojo_tachado
                    ),
                    contentDescription = stringResource(
                        if(visible) R.string.ocultar_contrasena else R.string.mostrar_contrasena
                    ),
                    modifier = Modifier.size(Dimens.tamanoIcono)
                )
            }
        },
        visualTransformation = if(visible) VisualTransformation.None else PasswordVisualTransformation(),
        isError = error != null,
        supportingText = { if(error != null) Text(error)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Preview(showBackground = true)
@Composable
fun ViewCampoPassword(){

    CampoPassword(
        valor = "manuelll",
        onContrasenaCambia = {},
        etiquetaContrasena = "Contraseña",
        visible = true,
        onAlternarVisibilidad = {},
        modifier = Modifier.padding(23.dp),
        error = "Datos invalidos"
    )
}