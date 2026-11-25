package com.example.electricapp.Data

import androidx.compose.runtime.retain.retain
import com.example.electricapp.data.remote.ActualizarPerfilRequest
import com.example.electricapp.data.remote.CambiarClaveRequest
import com.example.electricapp.data.remote.ContadorResponse
import com.example.electricapp.data.remote.CrearUsuariosRequest
import com.example.electricapp.data.remote.LoginRequest
import com.example.electricapp.data.remote.LoginResponse
import com.example.electricapp.data.remote.RecuperarClaveRequest
import com.example.electricapp.data.remote.UsuarioApi
import com.example.electricapp.data.remote.UsuarioResponse
import java.io.IOException

class FakeUsuarioApi : UsuarioApi {

    private val usuarios = listOf(
        UsuarioResponse(
            id = 1L, nombre = "Ricardo Espinoza Lagos", correo = "respinoza@induztek.cl",
            rut = "13.871.905-7", telefono = "987541666", rol = "JEFE_TECNICO",
            activo = true, giro = null, contactoCargo = null, claveTemporal = null
        ),
        UsuarioResponse(
            id = 4L, nombre = "Marcela Fuentes Rojas", correo = "mfuentes@envpacifico.ejemplo",
            rut = "14.226.791-8", telefono = "+56900000101", rol = "CLIENTE",
            activo = true, giro = "Agroindustria", contactoCargo = "Jefa de Mantenimiento", claveTemporal = null
        )
    )
    var sinConexion = false
    var ultimoBuscar: LoginRequest? = null

    override suspend fun crearUsuarios(datos: CrearUsuariosRequest): UsuarioResponse = TODO()


    override suspend fun login(datos: LoginRequest): LoginResponse {
        if(sinConexion) throw IOException("Servidor apagado")
        ultimoBuscar = datos
        val logeado = usuarios.first{it.correo == datos.correo}

        return LoginResponse(
            idUsuario = logeado.id,
            nombre = logeado.nombre,
            rol = logeado.rol,
            debeCambiarClave = false
        )
    }


    override suspend fun obtenerPefil(id: Long): UsuarioResponse = TODO()
    override suspend fun listarUsuarios(): List<UsuarioResponse> = TODO()
    override suspend fun cambiaEstado(id: Long): UsuarioResponse = TODO()
    override suspend fun cambiarClave(id: Long, datos: CambiarClaveRequest) = TODO()
    override suspend fun actualizar(id: Long, datos: ActualizarPerfilRequest): UsuarioResponse = TODO()
    override suspend fun recuperarClave(datos: RecuperarClaveRequest): UsuarioResponse = TODO()
    override suspend fun cantidadCuentasActivas(): ContadorResponse = TODO()
}
