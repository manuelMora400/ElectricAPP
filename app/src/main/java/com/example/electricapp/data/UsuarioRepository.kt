package com.example.electricapp.data


import com.example.electricapp.data.remote.CambiarClaveRequest
import com.example.electricapp.data.remote.CrearUsuariosRequest
import com.example.electricapp.data.remote.LoginRequest
import com.example.electricapp.data.remote.LoginResponse
import com.example.electricapp.data.remote.RecuperarClaveRequest
import com.example.electricapp.data.remote.RetrofitClient
import com.example.electricapp.data.remote.UsuarioApi
import com.example.electricapp.data.remote.UsuarioResponse
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class UsuarioRepository(
    private val usuarioApi : UsuarioApi = RetrofitClient.usuarioApi
) {
    suspend fun login(correo: String, password : String) : LoginResponse = llamar{
         usuarioApi.login(LoginRequest(correo, password));
    }
    suspend fun crearUsuario(datos : CrearUsuariosRequest): UsuarioResponse = llamar{
         usuarioApi.crearUsuarios(datos);
    }
    suspend fun cambiarEstado(id : Long) : UsuarioResponse = llamar{
         usuarioApi.cambiaEstado(id);
    }
    suspend fun obtenerPefil(id : Long): UsuarioResponse = llamar{
         usuarioApi.obtenerPefil(id);
    }
    suspend fun cambiarClave(id : Long, datos : CambiarClaveRequest) = llamar{
        usuarioApi.cambiarClave(id, datos)
    }
    suspend fun recuperarClave(identificador: String) : UsuarioResponse = llamar{
        usuarioApi.recuperarClave(RecuperarClaveRequest(identificador))
    }

    private suspend fun <T> llamar(peticion: suspend () -> T) : T = try{
        peticion()
    }catch (e : HttpException){
        throw Exception(mensajeDelServidor(e))
    }catch (e: IOException){
        throw Exception("No se pudo conectar con el servidor")
    }

    private fun mensajeDelServidor(e: HttpException): String {
        val cuerpo = e.response()?.errorBody()?.string()
        val mensaje = try {
            JSONObject(cuerpo ?: "").optString("message")
        } catch (ex: Exception) {
            ""
        }
        return mensaje.ifBlank { "Error del servidor (${e.code()})" }
    }
}

