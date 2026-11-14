package com.example.electricapp.permiso

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object permiso {

    fun tienePermisoCamara(contexto : Context) : Boolean {
        return ContextCompat.checkSelfPermission(
            contexto,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    // permiso para SMS
    fun tienePermisoSMS(contexto : Context) : Boolean{
        return ContextCompat.checkSelfPermission(
            contexto,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

}
