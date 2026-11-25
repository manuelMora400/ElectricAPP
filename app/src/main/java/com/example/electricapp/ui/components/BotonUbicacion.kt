package com.example.electricapp.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.DrawableRes
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
import com.example.electricapp.R
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun BotonVerUbicacion(
    latitud: Double?,
    longitud: Double?,
    @DrawableRes iconoUbicacion: Int,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    val contexto = LocalContext.current

    OutlinedButton(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            if (latitud != null && longitud != null) {
                val etiquetaCodificada = Uri.encode(etiqueta)
                val uriMaps = Uri.parse("geo:$latitud,$longitud?q=$latitud,$longitud($etiquetaCodificada)")
                val intentMaps = Intent(Intent.ACTION_VIEW, uriMaps).apply {
                    setPackage("com.google.android.apps.maps")
                }

                if (intentMaps.resolveActivity(contexto.packageManager) != null) {
                    contexto.startActivity(intentMaps)
                } else {
                    val uriWeb = Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitud,$longitud")
                    contexto.startActivity(Intent(Intent.ACTION_VIEW, uriWeb))
                }
            } else {
                Toast.makeText(
                    contexto,
                    "Este protocolo no tiene ubicación registrada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    ) {
        Icon(
            painter = painterResource(iconoUbicacion),
            contentDescription = "Ubicación de la prueba",
            modifier = Modifier.size(Dimens.tamanoIcono)
        )
        Spacer(modifier = Modifier.width(Dimens.espacioChico))
        Text(text = "Ver ubicación")
    }
}

@Preview(showBackground = true)
@Composable
fun BotonVerUbicacionView() {
    ElectricAppTheme(false) {
        BotonVerUbicacion(
            latitud = -33.2856,
            longitud = -70.8752,
            etiqueta = "TR-01 · Planta Lampa",
            iconoUbicacion = R.drawable.ic_ubicacion
        )
    }
}