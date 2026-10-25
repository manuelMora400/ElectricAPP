package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.FormularioLoginState
import com.example.electricapp.ui.screens.logeadoExito
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class FormularioLoginViewModel(application : Application): AndroidViewModel(application){


    private val db = ElectricHelperDb(application)

    private val _uiState = MutableStateFlow(FormularioLoginState())
    val uiState : StateFlow<FormularioLoginState> = _uiState.asStateFlow()

    fun onCorreoCambia (valor : String){
        _uiState.update{it.copy(correo = valor, errorCorreo = null, logeadoExitoso = false)}
    }
    fun onPasswordCambia(valor : String){
        _uiState.update { it.copy(passwordHash = valor, errorPassword = null, logeadoExitoso = false) }
    }
    fun onVisibilidad (){
        _uiState.update {it.copy(passwordVisible = !it.passwordVisible)}
    }

    fun onIniciar() : Boolean{

        val formatoCorreo = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        val estado = _uiState.value

        val errorCorreo =
            if(estado.correo.isBlank()){
                "El correo es obligatorio"
            }else if (estado.correo.length > 25){
                "El correo es muy largo"
            }else if(!formatoCorreo.matches(estado.correo)){
                "Formato invalido"
            }else if(estado.correo != estado.correo.trim()){
                "El correo no debe contener espacios en blanco"
            }else
                null

        val errorPassword =
            if (estado.passwordHash.isBlank()){
                "La contraseña no puede estar vacia"
            }else if(!estado.passwordHash.any{it.isUpperCase()}){
                "La contraseña debe contener al menos una letra en mayuscula"
            }else if(!estado.passwordHash.contains(".") || !estado.passwordHash.contains("-")){
                "La contraseña debe contener al menos un (.) y (-)"
            }else if (estado.passwordHash.length !in 8..12){
                "La contraseña debe tener un minimo de 8 y maximo de 12 digitos"
            }else
                null

        val hayErrores = listOf(errorCorreo, errorPassword).any{it != null}

        if (hayErrores){
            _uiState.update { it.copy(
                errorCorreo = errorCorreo,
                errorPassword = errorPassword,
                logeadoExitoso = false
            ) }
            return false
        }

        val cuentaEncontrada = db.buscarCuentasLogin(
            correo = estado.correo,
            password = estado.passwordHash
        )

        return  if (cuentaEncontrada != null){
            _uiState.update {
                it.copy(
                    errorCorreo = null,
                    errorPassword = null,
                    logeadoExitoso = true,
                    rol = cuentaEncontrada.rol
                )
            }
            true
        }else{
            _uiState.update {
                it.copy(
                    errorPassword = "Credenciales incorrectas",
                    logeadoExitoso = false
                )
            }
            false
        }
    }
    override fun onCleared() {
        db.close()
        super.onCleared()
    }
}
