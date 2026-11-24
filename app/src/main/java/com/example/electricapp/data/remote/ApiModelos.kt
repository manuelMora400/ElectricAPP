package com.example.electricapp.data.remote

data class LoginRequest(
    val correo : String,
    val password: String
)

data class LoginResponse(
    val idUsuario : Long,
    val nombre  : String,
    val rol     : String,
    val debeCambiarClave: Boolean
)
data class CambiarClaveRequest (
    val claveActual    : String,
    val claveNueva     : String,
    val confirmarClave    : String)

data class UsuarioResponse(
    val id: Long,
    val nombre: String,
    val correo: String,
    val rut: String,
    val telefono: String,
    val rol: String,
    val activo: Boolean,
    val claveTemporal: String?
)
data class CrearUsuariosRequest(
    val rut : String,
    val nombre : String,
    val telefono : String,
    val correo : String,
    val rol : String,
    val giro : String?,
    val contactoCargo : String?
)

data class RecuperarClaveRequest(
    val identificar : String
)

data class CambiarEstadoRequest(
    val activo : Boolean
)

data class ProtocoloResponse(
    val idProtocolo: Long,
    val fechaPrueba: String?,
    val temperatura: Double?,
    val humedad: Double?,
    val latitud: Double?,
    val longitud: Double?,
    val estadoProtocolo: String?
)

data class AislacionResponse(
    val id: Long,
    val tag: String?,
    val instalacion: String?,
    val enumDevanado: String?,
    val tension: Double?,
    val lectura1Min: Double?,
    val lectura10Min: Double?,
    val idProtocolo: Long?
)
