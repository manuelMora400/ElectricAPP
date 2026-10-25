package com.example.electricapp.model

import androidx.annotation.StringRes
import com.example.electricapp.R


// Esta clase enum es clave para manejar los roles
// Y permisos mas adelante (Ahora lo usaremos para un menu desplegable)
// en components SeleccionPerfilRegistro.kt

enum class Rol(@StringRes val texto : Int){
    JEFE_TECNICO(R.string.rol_jefe_tecnico),
    INGENIERO(R.string.rol_ingeniero),
    TECNICO(R.string.rol_tecnico),
    CLIENTE(R.string.rol_cliente)

}