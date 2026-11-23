package com.example.electricapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.model.Estado
import com.example.electricapp.model.HistorialState
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun ProtocoloCards(
    protocolo: HistorialState,
    esCliente: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // 1. Fecha arriba en negrita (ej. 26/09/2026)
                Text(
                    text = protocolo.fechaPrueba.ifBlank { "Sin fecha" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(2.dp))

                // 2. Subtítulo secundario de rol y estado
                val subtitulo = if (esCliente) {
                    if (protocolo.formalizado) {
                        val detalleFormalizado = protocolo.tituloResultado.ifBlank { "Formalizado" }
                        "${protocolo.tagBuscar} · $detalleFormalizado"
                    } else {
                        protocolo.tagBuscar
                    }
                } else {
                    "${protocolo.tagBuscar} · ${protocolo.instalacion}"
                }

                Text(
                    text = subtitulo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 3. Píldora de estado (RN-19: pendientes no muestran resultado ni IP)
                if (esCliente && !protocolo.formalizado) {
                    etiqueta(
                        text = R.string.pendiente_formalizacion,
                        state = Estado.OBSERVACION
                    )
                } else {
                    etiqueta(
                        text = when (protocolo.estado) {
                            Estado.APROBADO -> R.string.Consulta_Aprobado
                            Estado.OBSERVACION -> R.string.Consulta_Observacion
                            else -> R.string.Consulta_Rechazado
                        },
                        state = protocolo.estado
                    )
                }
            }

            // 4. Indicador lateral de navegación ">"
            Text(
                text = "›",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProtocoloCardsView() {
    ElectricAppTheme(false) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProtocoloCards(
                protocolo = HistorialState(
                    tagBuscar = "PT-2026-0401",
                    instalacion = "Planta Lampa",
                    fechaPrueba = "26/09/2026",
                    formalizado = false
                ),
                esCliente = true
            )
            ProtocoloCards(
                protocolo = HistorialState(
                    tagBuscar = "PT-2025-0288",
                    instalacion = "Planta Lampa",
                    fechaPrueba = "13/09/2025",
                    tituloResultado = "Formalizado por A. Contreras",
                    estado = Estado.APROBADO,
                    formalizado = true
                ),
                esCliente = true
            )
        }
    }
}