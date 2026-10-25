package com.example.electricapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.electricapp.model.Estado
import com.example.electricapp.model.ProtocoloItem
import com.example.electricapp.ui.styles.EstilosTarjetas


@Composable
fun ProtoCards(protocolo: ProtocoloItem){
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = EstilosTarjetas.forma,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = protocolo.fecha,
                color = Color.LightGray,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Protocolo ${protocolo.codigo}",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))

            val (textoEstado, colorFondo) = when (protocolo.estado) {
                Estado.OBSERVACION -> "En observacion" to Color(0xFFD97706) // Naranja
                Estado.APROBADO -> "Aprobado" to Color(0xFF10B981) // Verde
                Estado.RECHAZADO -> "Rechazado" to Color(0xFFDC2626) // Rojo
                Estado.ACTUALIZANDO -> (protocolo.detalleReemplazo ?: "Reemplazado") to Color(0xFF374151) // Gris
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
                    .background(colorFondo, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 6.dp, horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = textoEstado,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}