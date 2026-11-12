package com.example.electricapp.ui.components


import androidx.compose.material3.Icon
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme


@Composable
fun TarjetaOpciones(
    titulo : String,
    subtitulo : String,
    @DrawableRes icono : Int,
    onClick :  ()  -> Unit,
    modifier: Modifier = Modifier
){
 // esta animacion tendra el objetivo de si el usuario presione una opcion se unda la card
    val accionCard = remember {MutableInteractionSource()}
    val presiona by accionCard.collectIsPressedAsState()


    val escalado by animateFloatAsState(
        targetValue = if(presiona) 0.90f else 1f
    )

    Card(
        interactionSource = accionCard,
        modifier = modifier.graphicsLayer{
            scaleX = escalado
            scaleY = escalado
        },
        onClick = onClick,
        colors = EstilosTarjetas.colores(),
        shape = RoundedCornerShape(10.dp),
        border = EstilosTarjetas.bordeado(),
        elevation = EstilosTarjetas.elevacion()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Dimens.espacioMed),
            horizontalArrangement = Arrangement.spacedBy(Dimens.espacioLogoJefe),
            verticalAlignment =  Alignment.CenterVertically,
        ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(Dimens.espacioMed),
            verticalArrangement  = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(icono),
                    contentDescription = null
                )
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2
            )
        }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewTarjetasOpciones(){
    ElectricAppTheme(false) {
        TarjetaOpciones(
            titulo = "Gestionar cuenta",
            subtitulo = "Gestione cuentas",
            icono = R.drawable.ic_historial_equipo,
            onClick = {}
        )
    }
}