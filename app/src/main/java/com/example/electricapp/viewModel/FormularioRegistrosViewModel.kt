package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.electricapp.model.FormularioRegistros
import com.example.electricapp.model.Rol
import com.example.electricapp.model.UsuarioEjemplo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormularioRegistrosViewModel : ViewModel(){

    val cuentasSimulado : List<UsuarioEjemplo> = listOf(
        UsuarioEjemplo(1,"Manuel Mora", Rol.CLIENTE,"manuel@gmail.com"),
        UsuarioEjemplo(2,"Martina Jimenez", Rol.INGENIERO, "Marti@gmail.com")
    )
    private val _uiState = MutableStateFlow(FormularioRegistros())
    val uiState : StateFlow<FormularioRegistros> = _uiState.asStateFlow()

    fun onRutCambia (valor : String){
        _uiState.update { it.copy( rut = valor, errorRut = null, registradoExito = false)}
    }
    fun onNombreCambia(valor : String){
        _uiState.update { it.copy(nombre = valor, errorNombre = null, registradoExito = false)}
    }
    fun onTelefonoCambia(valor: String){
        _uiState.update { it.copy(telefono = valor, errorTelefono = null, registradoExito = false)}
    }
    fun onCorreoCambia(valor : String){
        _uiState.update { it.copy(correo = valor, errorCorreo = null, registradoExito = false) }
    }
    fun onPasswordCambia(valor: String){
        _uiState.update { it.copy(password = valor, errorPassword = null, registradoExito = false)}
    }
    fun onRolCambia(valor : Rol){
        _uiState.update {it.copy(rol = valor, registradoExito = false)}
    }

    fun onGiroCambia(valor: String){
        _uiState.update { it.copy(giro = valor, registradoExito = false)}
    }
    fun onContactoCambia(valor: String){
        _uiState.update { it.copy(contactoCargo = valor,  registradoExito = false)}
    }

    fun onRegistrar(){

        val formatoCorreo = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        val estado = _uiState.value

        val errorRut =
                if(estado.rut.isBlank()){
                    "El rut no debe estar vacio"
                }else if (estado.rut.length > 12){
                    "El rut no debe contener mas de 12 caracteres"
                }else if(estado.rut != estado.rut.trim()){
                    "El rut no debe contener espacios en blanco"
                } else
                    null

        val errorNombre =
                if(estado.nombre.isBlank()){
                    "El nombre no puede estar vacio"
                }else if (estado.nombre.length !in 10..120){
                    "El nombre debe contener entre 10 a 120 caracteres"
                }else
                    null

        val errorTelefono =

                if(estado.telefono.isBlank()){
                    "El telefono no debe estar vacio"
                }else if(estado.telefono.length !in 10..20){
                    "El telefono debe contener entre 10 a 20 digitos"
                }else if(estado.telefono != estado.telefono.trim()){
                    "El telefono no debe contener espacios"
                }else
                    null

        val errorCorreo =

                if(estado.correo.isBlank()){
                    "El correo no debe estar vacio"
                }else if(estado.correo.length !in 10..120){
                    "El correo debe tener entre 10 a 120 caracteres"
                }else if(estado.correo != estado.correo.trim()){
                    "El correo no debe contener espacios en blanco"
                }else if(!estado.correo.matches(formatoCorreo)){
                    "Formato invalido"
                }else
                    null

        val errorPassword =

                if(estado.password.isBlank()){
                    "La contraseña del usuario debe estar en blanco"
                }else if(estado.password.length !in 8..12 ){
                    "La contraseña debe contener entre 8 a 12 caracteres"
                }else if(!estado.password.contains(".") && !estado.password.contains("-")){
                    "La contraseña debe contener al menos un punto ( . ) o guion ( - ) "
                }else if(!estado.password.any{it.isUpperCase()}){
                    "La contraseña debe contener al menos 1 caracter en mayuscula"
                }else
                    null

        val erroGiro =

            if(estado.rol == Rol.CLIENTE){
                if(estado.giro.isBlank()){
                    "El giro en clientes no puede estar vacio"
                }else if(estado.giro.length !in 10..80){
                    "El giro debe contener entre 10 a 80 caracteres"
                }else
                    null
            }else
                null

        val errorContacto =
            if(estado.rol == Rol.CLIENTE){
                if(estado.contactoCargo.isBlank()){
                    "El contacto cargo no debe estar vacio"
                }else
                    null
            }else
                null

        val hayErrores = listOf(
            errorRut,
            errorNombre,
            errorTelefono,
            errorCorreo,
            errorPassword,
            erroGiro,
            errorContacto).any{it != null}

        _uiState.update {
            it.copy(
                errorRut = errorRut,
                errorNombre = errorNombre,
                errorTelefono = errorTelefono,
                errorCorreo = errorCorreo,
                errorPassword = errorPassword,
                errorGiro = erroGiro,
                errorCotacto = errorContacto,
                registradoExito = !hayErrores
            )
        }
    }

    fun onLimpiar(){
        _uiState.value = FormularioRegistros()
    }
}
