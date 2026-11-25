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
    val giro : String?,
    val contactoCargo : String?,
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

data class ContadorResponse (
    val contador : Long
)

data class ActualizarPerfilRequest(
    val nombre : String,
    val telefono : String,
    val correo : String? = null
)

data class CrearInstrumentoRequest(
    val nombre: String,
    val marca: String,
    val modelo: String,
    val numeroSerie: String,
    val fechaCalibracion: String,
    val fechaVencimiento: String
)
data class InstrumentoResponse(
    val id: Long,
    val nombre: String,
    val marca: String,
    val modelo: String,
    val numeroSerie: String,
    val fechaCalibracion: String,
    val fechaVencimiento: String,
    val vigente: Boolean
)
data class CrearEquipoRequest(
    val tag: String,
    val tipo: String,
    val instalacion: String,
    val idCliente: Long
)

data class EquipoResponse(
    val id: Long,
    val tag: String,
    val tipo: String,
    val instalacion: String,
    val idCliente: Long
)

data class RenovarCalibracionRequest(
    val fechaCalibracion : String,
    val fechaVencimiento: String
)

data class ProtocoloResponse(
    val idProtocolo: Long,
    val fechaPrueba: String?,
    val temperatura: Double?,
    val humedad: Double?,
    val latitud: Double?,
    val longitud: Double?,
    val estadoProtocolo: String?,
    val resultadoPrueba: String?
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

data class CambiarEstadoProtocoloRequest(
    val estadoProtocolo: String
)

data class CrearProtocoloRequest(
    val temperatura: Double,
    val humedad: Double,
    val resultadoPrueba: String,
    val idTecnico: Long,
    val idInstrumento: Long
)

data class CrearAislacionRequest(
    val tag: String,
    val instalacion: String,
    val enumDevanado: String,
    val tension: Double,
    val lectura1Min: Double,
    val lectura10Min: Double,
    val idProtocolo: Long
)
