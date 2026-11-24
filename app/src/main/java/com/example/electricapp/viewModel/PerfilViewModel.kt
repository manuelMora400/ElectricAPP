package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.data.UsuarioRepository
import com.example.electricapp.data.remote.UsuarioResponse
import com.example.electricapp.model.PerfilUsuario
import com.example.electricapp.model.Rol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel() : ViewModel() {


    private val repositorio = UsuarioRepository()

    private val _uiState = MutableStateFlow(PerfilUsuario())
    val uiState : StateFlow<PerfilUsuario> = _uiState.asStateFlow()


    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()


    private fun ejecutar(onError: (String) -> Unit, accion: suspend () -> Unit) {
        viewModelScope.launch {
            _cargando.value = true
            try {
                accion()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error inesperado")
            } finally {
                _cargando.value = false
            }
        }
    }

    fun cargar(idUsuario : Long){

        ejecutar(
            onError = { mensaje ->
                _uiState.update { it.copy(
                    error = mensaje
                ) }
            }
        ){
            val buscarPerfil = repositorio.obtenerPefil(idUsuario)
            val enumRol = Rol.valueOf(buscarPerfil.rol.uppercase())

            val datos = PerfilUsuario(
                idUsuario = buscarPerfil.id,
                nombre = buscarPerfil.nombre,
                correo = buscarPerfil.correo,
                rut = buscarPerfil.rut,
                telefono = buscarPerfil.telefono,
                cargo = buscarPerfil.contactoCargo ?: "",
                rol = enumRol
            )
            _uiState.update {datos}
        }
    }
}