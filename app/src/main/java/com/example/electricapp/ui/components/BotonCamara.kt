package com.example.electricapp.ui.components

import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.electricapp.R
import com.example.electricapp.permiso.permiso
import com.example.electricapp.ui.styles.EstilosTarjetas
import com.example.electricapp.ui.theme.Dimens
import com.example.electricapp.ui.theme.ElectricAppTheme

@Composable
fun BotonCamara(
    texto : String,
    modifier: Modifier = Modifier
){
    val contexto = LocalContext.current


    var foto by remember { mutableStateOf<Bitmap?>(null) }


    val camaraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { imagen ->
        if(imagen != null){
            foto = imagen
        }
    }


    fun abrirCamara(){
        try {
            camaraLauncher.launch(null)
        } catch (e : ActivityNotFoundException){
            Toast.makeText(contexto, contexto.getString(R.string.sin_camara), Toast.LENGTH_SHORT).show()
        }
    }


    val permisoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { aceptado ->
        if(aceptado){
            abrirCamara()
        }else{
            Toast.makeText(contexto, contexto.getString(R.string.permiso_camara_denegado), Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = {
                if(permiso.tienePermisoCamara(contexto)){
                    abrirCamara()
                }else{
                    permisoLauncher.launch(android.Manifest.permission.CAMERA)
                }
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_camara),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Text(
                text = texto,
                modifier = Modifier.padding(start = Dimens.espacioMed)
            )
        }


        foto?.let { imagen ->
            Image(
                bitmap = imagen.asImageBitmap(),
                contentDescription = stringResource(R.string.foto_tomada),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(Dimens.espacioMed)
                    .size(150.dp)
                    .clip(EstilosTarjetas.forma)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BotonCamaraView(){
    ElectricAppTheme(false) {
        BotonCamara(texto = "Tomar foto")
    }
}
