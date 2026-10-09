package com.example.electricapp.ui.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.ui.theme.Dimens

@Composable
fun LogoInicio(
    @DrawableRes imagen : Int,
    modifier: Modifier
){
    Image(
        painter = painterResource(id = imagen),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .size(40.dp)
            .shadow(elevation = 25.dp , shape = CircleShape)
            .clip(CircleShape)
            .border(width = Dimens.bordeLogo, MaterialTheme.colorScheme.surface, CircleShape)
    )
}

// Vista previa
@Preview(showBackground = true)
@Composable
private fun LogoInicioView(){
    LogoInicio(
        imagen = R.drawable.logo_inicio_sesion,
        modifier = Modifier.padding(5.dp)
    )
}