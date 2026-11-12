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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.electricapp.ui.styles.EstilosTarjetas

@Composable
fun tarjetaContadores(
    valor : Int,
    etiqueta : String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = EstilosTarjetas.colores(),
        border = EstilosTarjetas.bordeado()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
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
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}