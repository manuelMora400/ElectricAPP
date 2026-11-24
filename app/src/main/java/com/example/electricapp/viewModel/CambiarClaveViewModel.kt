package com.example.electricapp.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.UsuarioRepository
import com.example.electricapp.data.remote.CambiarClaveRequest
import com.example.electricapp.model.CambiarClaveState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CambiarClaveViewModel() : ViewModel(){

    private val repositorio = UsuarioRepository()
    private val _uiState = MutableStateFlow(CambiarClaveState())
    val uiState : StateFlow<CambiarClaveState> = _uiState.asStateFlow()

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

    fun onClaveActual(valor : String){
        _uiState.update { it.copy(claveActual = valor, errorClaveActual = null, errorGeneral = null) }
    }

    fun onNuevaClave(valor : String){
        _uiState.update { it.copy(nuevaClave = valor, errorNuevaClave = null, errorGeneral = null,
            cumpleLargoPassword = valor.length in 8..12,
            cumpleMayusculaPassword = valor.any{it.isUpperCase()},
            cumpleSimboloPassword = valor.contains(".") || valor.contains("-")) }
    }

    fun onConfirmarClave(valor : String){
        _uiState.update { it.copy(confirmarClave = valor, errorConfirmarClave = null, errorGeneral = null) }
    }

    fun onVisibilidad(){
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun onGuardarClaveNueva(idUsuario : Long, onExitoso : () -> Unit){

        val estado = _uiState.value
        val nuevaClave = estado.nuevaClave

        val errorClaveActual =
            if(estado.claveActual.isBlank()){
                "Debe ingresar su contraseña actual"
            }else
                null

        val errorNuevaPassword =
            if(nuevaClave.isBlank()){
                "La contraseña no puede estar vacia"
            }else if(!nuevaClave.any{it.isUpperCase()}){
                "La contraseña debe contener al menos una letra en mayuscula"
            }else if(!nuevaClave.contains(".") && !nuevaClave.contains("-")){
                "La contraseña debe contener al menos un punto ( . ) o guion ( - )"
            }else if(nuevaClave.length !in 8..12){
                "La contraseña debe tener un minimo de 8 y maximo de 12 digitos"
            }else if(nuevaClave == estado.claveActual){
                "La contraseña no debe ser igual a la anterior"
            }else
                null

        val errorConfirmarClave =
            if(estado.confirmarClave.isBlank()){
                "Debe confirmar su clave"
            }else if(estado.confirmarClave != nuevaClave){
                "Las contraseñas no coinciden"
            }else
                null

        if(errorClaveActual != null || errorNuevaPassword != null || errorConfirmarClave != null){
            _uiState.update { it.copy(
                errorClaveActual = errorClaveActual,
                errorNuevaClave = errorNuevaPassword,
                errorConfirmarClave = errorConfirmarClave
            ) }
            return
        }
        ejecutar(
            onError = { mensaje ->
                if(mensaje == "La contraseña actual es incorrecta")
                    _uiState.update { it.copy(errorClaveActual = mensaje) }
                    else{
                    _uiState.update { it.copy(
                        errorGeneral = mensaje
                    ) }
                }
            }
        ){
            val datos = CambiarClaveRequest(
                claveActual = estado.claveActual,
                claveNueva = nuevaClave,
                confirmarClave = estado.confirmarClave
            )
            repositorio.cambiarClave(
                id = idUsuario,
                datos  = datos
            )
            _uiState.value = CambiarClaveState()
            onExitoso()
        }

    }
}
