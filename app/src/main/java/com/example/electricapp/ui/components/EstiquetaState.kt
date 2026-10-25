package com.example.electricapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.electricapp.model.Estado
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens


@Composable
fun etiqueta(
    text: Int,
    state: Estado
){
    val colorBlock =
        when(state){
            Estado.APROBADO -> Color.Green
            Estado.OBSERVACION -> Color.Gray
            Estado.RECHAZADO -> Color.Red
        }
    Surface(
        shape = EstilosTarjetas.forma,
        color = colorBlock,
        modifier = Modifier.padding(Dimens.espacioChico)
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.titleMedium
        )
    }
}