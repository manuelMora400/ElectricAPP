package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.UsuarioRepository
import com.example.electricapp.data.remote.ActualizarPerfilRequest
import com.example.electricapp.model.EditarPerfilState
import com.example.electricapp.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditarPerfilViewModel() : ViewModel(){

    private  val repostorio = UsuarioRepository()
    private val _uiState = MutableStateFlow(EditarPerfilState())
    val uiState : StateFlow<EditarPerfilState> = _uiState.asStateFlow()
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

    fun cargar(idUsuario : Long, rolSesion : Rol){
        ejecutar( onError = { mensaje ->
            _uiState.update { it.copy(errorGeneral = mensaje) }
        }
        ){
            val perfil = repostorio.obtenerPefil(idUsuario)
            val enumRol = Rol.valueOf(perfil.rol.uppercase())
                _uiState.update {
                    it.copy(
                        idUsuario = perfil.id,
                        nombre = perfil.nombre,
                        correo = perfil.correo,
                        telefono = perfil.telefono,
                        rut = perfil.rut,
                        rol = enumRol,
                        puedeEditarCorreo = rolSesion == Rol.JEFE_TECNICO
                    )
                }
        }
    }

    fun onNombreCambia(valor : String){
        _uiState.update { it.copy(nombre = valor, errorNombre = null, errorGeneral = null) }
    }

    fun onCorreoCambia(valor : String){
        _uiState.update { it.copy(correo = valor, errorCorreo = null, errorGeneral = null) }
    }

    fun onTelefonoCambia(valor : String){
        _uiState.update { it.copy(telefono = valor, errorTelefono = null, errorGeneral = null) }
    }

    fun onGuardar(onExitoso : (String) -> Unit){

        val formatoNombre = Regex("^[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+( [A-Za-zÁÉÍÓÚáéíóúÑñÜü]+)+$")
        val formatoCorreo = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        val formatoTelefono = Regex("^(\\+?56)? ?9 ?\\d{4} ?\\d{4}$")
        val estado = _uiState.value

        val errorNombre =
            if(estado.nombre.isBlank()){
                "El nombre es obligatorio"
            }else if(!formatoNombre.matches(estado.nombre.trim())){
                "Ingresa nombre y apellido, solo letras"
            }else
                null

        val errorCorreo =
            if(!estado.puedeEditarCorreo){
                null
            }else if(estado.correo.isBlank()){
                "El correo es obligatorio"
            }else if(estado.correo.length > 120){
                "El correo no debe tener mas de 120 caracteres"
            }else if(!formatoCorreo.matches(estado.correo.trim())){
                "Formato invalido"
            }else
                null

        val errorTelefono =
            if(estado.telefono.isBlank()){
                "El telefono es obligatorio"
            }else if(!formatoTelefono.matches(estado.telefono.trim())){
                "Formato: +56 9 1234 5678"
            }else
                null

        val hayErrores = listOf(errorNombre, errorCorreo, errorTelefono).any{it != null}

        if(hayErrores){
            _uiState.update { it.copy(
                errorNombre = errorNombre,
                errorCorreo = errorCorreo,
                errorTelefono = errorTelefono
            ) }
            return
        }
        ejecutar(
            onError = { mensaje ->
                if(mensaje == "El correo ya existe en el sistema"){
                    _uiState.update { it.copy(errorCorreo = mensaje) }
                }else{
                    _uiState.update { it.copy(errorGeneral = mensaje) }
                }
            }
        ){
            val usuarioActualizado = ActualizarPerfilRequest(
                nombre = estado.nombre.trim(),
                telefono = estado.telefono.trim() ,
                correo = if(estado.rol == Rol.JEFE_TECNICO) estado.correo.trim() else null
            )
            val guardadoExitosamente = repostorio.actualizarPerfil(estado.idUsuario, usuarioActualizado)
            onExitoso(estado.nombre.trim())
        }
    }
}
