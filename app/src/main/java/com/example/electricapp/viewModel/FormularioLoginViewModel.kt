package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.FormularioLoginState
import com.example.electricapp.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update



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

    fun onIniciar() : Rol?{

        val formatoCorreo = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        val estado = _uiState.value

        val errorCorreo =
            if(estado.correo.isBlank()){
                "El correo es obligatorio"
            }else if (estado.correo.length > 120){
                "El correo no debe tener mas de 120 caracteres"
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
            }else if(!estado.passwordHash.contains(".") && !estado.passwordHash.contains("-")){
                "La contraseña debe contener al menos un punto ( . ) o guion ( - )"
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
          return null
        }

        val cuentaEncontrada = db.buscarCuentasLogin(
            correo = estado.correo,
            password = estado.passwordHash
        )

        if (cuentaEncontrada != null && cuentaEncontrada.rol == Rol.INGENIERO){
            _uiState.update {
                it.copy(
                    errorPassword = "El perfil Ingeniero estara disponible en la proxima entrega",
                    logeadoExitoso = false
                )
            }
            return null
        }

        if (cuentaEncontrada != null){
            _uiState.update {
                it.copy(
                    idUsuario = cuentaEncontrada.idUsuario,
                    nombre = cuentaEncontrada.nombre,
                    errorCorreo = null,
                    errorPassword = null,
                    logeadoExitoso = true,
                    rol = cuentaEncontrada.rol
                )
            }
        }else{
            _uiState.update {
                it.copy(
                    errorPassword = "Credenciales incorrectas",
                    logeadoExitoso = false
                )
            }
            return null
        }
        return cuentaEncontrada.rol
    }


    fun onCerrarSesion(){
        _uiState.value = FormularioLoginState()
    }

    override fun onCleared() {
        db.close()
        super.onCleared()
    }
}
