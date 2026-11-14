package com.example.electricapp.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun tarjetaContadores(
    valor : Int,
    etiqueta : String,
    modifier: Modifier = Modifier,
    colorValor : Color = Color.Unspecified
){
    Card(
        modifier = modifier,
        shape = EstilosTarjetas.formaPanel,
        colors = EstilosTarjetas.coloresPanel(),
        border = EstilosTarjetas.bordeSuave(),
        elevation = EstilosTarjetas.sinElevacion()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = Dimens.rowEspacio, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // esta animacion permitira si agregamos una cuenta 7 + 1 -> subira  a 8 con una animacion
            // viserversa si se desactiva restara.
            AnimatedContent(
                targetState = valor,
                transitionSpec =  {
                    slideInVertically {it / 2 } + fadeIn() togetherWith slideOutVertically { -it / 2 } + fadeOut()
                },
                label = "Contador para dashboard"
            ) { numero ->
                Text(
                    text = numero.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colorValor.takeOrElse { MaterialTheme.colorScheme.primary }
                )
            }
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun tarjetaContadoresView(){
    ElectricAppTheme(false) {
        tarjetaContadores(valor = 7, etiqueta = "Cuentas activas")
    }
}

@Preview(showBackground = true)
@Composable
fun tarjetaContadoresViewOscuro(){
    ElectricAppTheme(true) {
        tarjetaContadores(valor = 2, etiqueta = "Instr. vencidos", colorValor = MaterialTheme.colorScheme.error)
    }
}
