package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.CambiarClaveState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CambiarClaveViewModel(application : Application) : AndroidViewModel(application){

    private val db = ElectricHelperDb(application)

    private val _uiState = MutableStateFlow(CambiarClaveState())
    val uiState : StateFlow<CambiarClaveState> = _uiState.asStateFlow()

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

    fun onGuardarClaveNueva(idUsuario : Int) : Boolean{

        val estado = _uiState.value
        val nuevaClave = estado.nuevaClave

        val errorClaveActual =
            if(estado.claveActual.isBlank()){
                "Debe ingresar su contraseña actual"
            }else if(!db.claveCorrecta(idUsuario, estado.claveActual)){
                "La contraseña actual no es correcta"
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
            return false
        }

        val confirmadoExito = db.actualizarClave(idUsuario, nuevaClave)

        if(confirmadoExito){
            _uiState.value = CambiarClaveState()
            return true
        }else{
            _uiState.update { it.copy(
                errorGeneral = "Error al cambiar su contraseña"
            ) }
            return false
        }
    }

    override fun onCleared() {
        db.close()
        super.onCleared()
    }
}
