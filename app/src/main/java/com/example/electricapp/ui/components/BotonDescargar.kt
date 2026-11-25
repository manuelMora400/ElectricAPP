package com.example.electricapp.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.electricapp.R
import com.example.electricapp.generador.GeneradorPdfProtocolo
import com.example.electricapp.model.DetallesState
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun BotonDescargar(
    detalle: DetallesState,
    indicePolarizacion: Double,
    onDescargado: () -> Unit = {},
    modifier: Modifier = Modifier,
    iconodescargar: Any
) {
    val contexto = LocalContext.current

    val permisoRequerido = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    fun ejecutarDescarga() {
        GeneradorPdfProtocolo.generarYDescargarPdf(
            context = contexto,
            detalle = detalle,
            indicePolarizacion = indicePolarizacion
        )
        onDescargado()
    }

    val permisoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { aceptado ->
        if (aceptado) {
            ejecutarDescarga()
        } else {
            Toast.makeText(
                contexto,
                "Permiso denegado para guardar en el almacenamiento",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            val tienePermiso = ContextCompat.checkSelfPermission(
                contexto,
                permisoRequerido
            ) == PackageManager.PERMISSION_GRANTED

            if (tienePermiso) {
                ejecutarDescarga()
            } else {
                permisoLauncher.launch(permisoRequerido)
            }
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_descargar),
            contentDescription = "Descargar PDF",
            modifier = Modifier.size(Dimens.tamanoIcono)
        )
        Spacer(modifier = Modifier.width(Dimens.espacioChico))
        Text(text = "Descargar PDF")
    }
}

@Preview(showBackground = true)
@Composable
fun BotonDescargarPdfView() {
    ElectricAppTheme(false) {
        BotonDescargar(
            detalle = DetallesState(
                Tag = "TR-01",
                Instalacion = "Planta Lampa",
                medicion1Min = "9800",
                medicion10Min = "21560",
                devanado = "AT-T",
                tensionPrueba = "5",
                resultado = "Aprobado",
                estado = "Formalizado",
                nombreIngeniero = "Andrea Contreras",
                fechaPrueba = "13/09/2025",
            ),
            indicePolarizacion = 2.20,
            iconodescargar = R.drawable.ic_descargar
        )
    }
}