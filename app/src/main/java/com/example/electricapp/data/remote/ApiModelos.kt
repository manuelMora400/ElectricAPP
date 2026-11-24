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

data class CambiarEstadoRequest(
    val activo : Boolean
)

data class ContadorResponse (
    val contador : Long
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
data class VigenciaResponse(
    val idInstrumento: Long,
    val vigente: Boolean,
    val fechaVencimiento: String
)


data class CrearEquipoRequest(
    val tag: String,
    val tipo: String,
    val instalacion: String,
    val idCliente: Long        // id del usuario CLIENTE dueño (viene de ms_roles)
)

data class EquipoResponse(
    val id: Long,
    val tag: String,
    val tipo: String,
    val instalacion: String,
    val idCliente: Long
)



