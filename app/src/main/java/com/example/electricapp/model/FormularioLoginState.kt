package com.example.electricapp.model

enum class RolesUsuarios(val idRoles : Int, val etiqueta : String){

        TECNICO     (1,  "Tecnico De terreno"),
        CLIENTE     (2,  "Solo consulta"),
        INGENIERO   (3, "Ingeniero de Pruebas"),
        JEFE_TECNICO(4,"Jefe Tecnico")
}

data class Usuario(
    val id_usuario : Int,
    val id_rol : RolesUsuarios,
    val rut : String = "",
    val nombre : String = "",
    val correo : String = "",
    val telefono : String = "",
    val passwordHash : String = "",
    val activo : Boolean = true, // por default esta activa
    val giro : String? = null,
    val contactoNombre : String? = null,
    val contactoCargo : String? = null)