package com.example.electricapp.ui.components

import android.content.Context
import android.os.Build
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.electricapp.R
import com.example.electricapp.permiso.permiso
import com.example.electricapp.ui.theme.ElectricAppTheme


@Composable
fun BotonEnviarSms(
    telefono : String,
    clave : String,
    onEnviado : () -> Unit,
    modifier: Modifier = Modifier
){
    val contexto = LocalContext.current

    fun enviarClave(){
        val mensaje = contexto.getString(R.string.sms_mensaje_clave, clave)

        if(enviarSMS(contexto, telefono, mensaje)){
            Toast.makeText(contexto, contexto.getString(R.string.sms_enviado), Toast.LENGTH_SHORT).show()
            onEnviado()
        }else{
            Toast.makeText(contexto, contexto.getString(R.string.sms_error), Toast.LENGTH_SHORT).show()
        }
    }

    val permisoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { aceptado ->
        if(aceptado){
            enviarClave()
        }else{
            Toast.makeText(contexto, contexto.getString(R.string.permiso_sms_denegado), Toast.LENGTH_SHORT).show()
        }
    }

    TextButton(
        modifier = modifier,
        onClick = {
            if(permiso.tienePermisoSMS(contexto)){
                enviarClave()
            }else{
                permisoLauncher.launch(android.Manifest.permission.SEND_SMS)
            }
        }
    ) {
        Text(text = stringResource(R.string.btn_enviar_sms))
    }
}

private fun enviarSMS(contexto : Context, numero : String, mensaje : String) : Boolean{

    // replace saca los espacios -> "+56 9 1234 5678" queda "+56912345678"
    val numeroSeparado = numero.replace(" ", "")

    try {
        val smsManager =
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                    contexto.getSystemService(SmsManager::class.java)
                }else{
                    SmsManager.getDefault()
                }

        //  talefono sin SIM
        if (smsManager == null) {
            return false
        }
        smsManager.sendTextMessage(numeroSeparado, null, mensaje, null, null)
        return true
    }catch (e : Exception){
            return false
    }
}

@Preview(showBackground = true)
@Composable
fun BotonEnviarSmsView(){
    ElectricAppTheme(false) {
        BotonEnviarSms(
            telefono = "+56912345678",
            clave = "Kx7aP2m.48",
            onEnviado = {}
        )
    }
}
