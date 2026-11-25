package com.example.electricapp.generador

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

@RequiresPermission(allOf =
    [Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION]
)
@SuppressLint("MissingPermission")
fun obtenerUbicacionActual(
    context: Context,
    onUbicacionObtenida: (Double?, Double?) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                onUbicacionObtenida(location.latitude, location.longitude)
            } else {
                fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { currentLoc ->
                        onUbicacionObtenida(currentLoc?.latitude, currentLoc?.longitude)
                    }
                    .addOnFailureListener { onUbicacionObtenida(null, null) }
            }
        }
        .addOnFailureListener {
            onUbicacionObtenida(null, null)
        }
}
