package com.example.electricapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

// este componente lo usaremos dentro de la top bar, mas adelante se puede hacer que pueda poner foto
// ya sea de su cara u de su galeria..... hay que reutilizar en cliente, jefe tecnico, tecncio y ingeniero
@Composable
fun LogoPerfil(
    iniciales : String,
    onClick : () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.size(Dimens.tamanoLogoPerfil)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.background)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = iniciales,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
@Preview(showBackground = true)
@Composable
fun previewPerfil(){

    ElectricAppTheme(true) {
        LogoPerfil(
            iniciales = "JT",
            onClick = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
fun previewPerfil2(){

    ElectricAppTheme(false) {
        LogoPerfil(
            iniciales = "JT",
            onClick = {}
        )
    }
}