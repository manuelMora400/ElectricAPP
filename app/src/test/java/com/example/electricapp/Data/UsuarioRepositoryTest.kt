package com.example.electricapp.Data

import com.example.electricapp.data.UsuarioRepository
import com.example.electricapp.data.remote.LoginRequest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UsuarioRepositoryTest {

    private lateinit var apiFalsa : FakeUsuarioApi
    private lateinit var repositorio : UsuarioRepository

    @Before
    fun preparar(){
        apiFalsa = FakeUsuarioApi()
        repositorio = UsuarioRepository(usuarioApi =apiFalsa)
    }

    @Test
    fun sinConexion_lanzaErrorMensaje(){
        apiFalsa.sinConexion  = true

        val  error = assertThrows(Exception::class.java){
            runBlocking { repositorio.login("","")}
        }
        assertEquals("No se pudo conectar con el servidor",error.message)
    }

    @Test
    fun Login_Response_Rol_base_correo() = runBlocking {
         val usuarioLoeago = repositorio.login("mfuentes@envpacifico.ejemplo",
            "12345")
        assertEquals(4L, usuarioLoeago.idUsuario)
        assertEquals("CLIENTE", usuarioLoeago.rol)
    }

    @Test
    fun ComprobarOrden_Login() = runBlocking {
        repositorio.login("respinoza@induztek.cl", "676767")

        assertEquals("respinoza@induztek.cl",
            apiFalsa.ultimoBuscar?.correo)
    }


}