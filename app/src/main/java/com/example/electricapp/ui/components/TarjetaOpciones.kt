package com.example.electricapp.ui.components


import androidx.compose.material3.Icon
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
        targetValue = if(presiona) 0.96f else 1f,
        label = "Hundir tarjeta opcion"
    )

    Card(
        interactionSource = accionCard,
        modifier = modifier.graphicsLayer{
            scaleX = escalado
            scaleY = escalado
        },
        onClick = onClick,
        colors = EstilosTarjetas.coloresPanel(),
        shape = EstilosTarjetas.formaPanel,
        border = EstilosTarjetas.bordeSuave(),
        elevation = EstilosTarjetas.sinElevacion()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(Dimens.padTarjetaPanel),
            verticalArrangement = Arrangement.spacedBy(Dimens.espacioMed)
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.cajaIcono)
                    .clip(RoundedCornerShape(Dimens.radioCajaIcono))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icono),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(Dimens.iconoPanel)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.espacioChico)) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
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
            titulo = "Gestionar equipos",
            subtitulo = "Gestione equipos",
            icono = R.drawable.ic_historial_equipo,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun previewTarjetasOpcionesOscuro(){
    ElectricAppTheme(true) {
        TarjetaOpciones(
            titulo = "Gestionar cuenta",
            subtitulo = "Gestione cuentas",
            icono = R.drawable.ic_historial_equipo,
            onClick = {}
        )
    }
}
