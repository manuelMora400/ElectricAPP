package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.UsuarioRepository
import com.example.electricapp.data.remote.CrearUsuariosRequest
import com.example.electricapp.model.FormularioRegistros
import com.example.electricapp.model.Rol
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FormularioRegistrosViewModel(application: Application) : AndroidViewModel(application){

    private val repositorio = UsuarioRepository()

    private val _cuentasBD = MutableStateFlow<List<FormularioRegistros>>(emptyList())
    val cuentasBD: StateFlow<List<FormularioRegistros>> = _cuentasBD.asStateFlow()

    private val _uiState = MutableStateFlow(FormularioRegistros())
    val uiState : StateFlow<FormularioRegistros> = _uiState.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    init {
        cargarCuentas()
    }

    fun cargarCuentas(){
        ejecutar(
            onError = { mensaje ->
                _uiState.update { it.copy(errorGeneral = mensaje) }
            }
        ){
            val listaRemota = repositorio.listarUsuarios()
            _cuentasBD.value = listaRemota.map { usu ->
                FormularioRegistros(
                    id = usu.id,
                    rut = usu.rut,
                    nombre = usu.nombre,
                    telefono = usu.telefono,
                    correo = usu.correo,
                    activo = usu.activo,
                    rol = Rol.entries.find { it.name.equals(usu.rol, ignoreCase = true) } ?: Rol.CLIENTE
                )
            }
        }
    }

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

    fun onEstadoCambia(idUsuario : Long){
        ejecutar(
            onError = { mensaje ->
                _uiState.update { it.copy(errorGeneral = mensaje)}
            }
        ){
            val estadoActualizado = repositorio.cambiarEstado(idUsuario)
            _cuentasBD.update { lista ->
                lista.map { cuenta ->
                    if(cuenta.id == idUsuario){
                        cuenta.copy(activo = estadoActualizado.activo)
                    }else{
                        cuenta
                    }
                }
            }
        }
    }

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

    fun onRegistrar(onExito : () -> Unit){

        val formatoCorreo = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        val formatoRut = Regex("^(\\d{1,2}\\.\\d{3}\\.\\d{3}|\\d{7,8})-[0-9kK]$")
        val tieneNumeros = Regex("[0-9]")

        val estado = _uiState.value

        val errorRut =
                if(estado.rut.isBlank()){
                    "El rut no debe estar vacio"
                }else if (estado.rut.length > 12){
                    "El rut no debe contener mas de 12 caracteres"
                }else if(estado.rut != estado.rut.trim()){
                    "El rut no debe contener espacios en blanco"
                }else if(!estado.rut.matches(formatoRut)){
                    "Formato de RUT invalido (ej: 12345678-K o 12.345.678-K)"
                } else
                    null

        val errorNombre =
                if(estado.nombre.isBlank()){
                    "El nombre no puede estar vacio"
                }else if (estado.nombre.length !in 10..120){
                    "El nombre debe contener entre 10 a 120 caracteres"
                }else if(estado.nombre.contains(tieneNumeros)){
                    "El nombre no debe contener numeros"
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
                    "La contraseña del usuario no debe estar en blanco"
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
            erroGiro,
            errorContacto).any{it != null}

        if(hayErrores){
            _uiState.update {
                it.copy(
                    errorRut = errorRut,
                    errorNombre = errorNombre,
                    errorTelefono = errorTelefono,
                    errorCorreo = errorCorreo,
                    errorPassword = errorPassword,
                    errorGiro = erroGiro,
                    errorCotacto = errorContacto,
                    registradoExito = false
                )
            }
            return
        }

        ejecutar(
            onError = { mensaje ->
                _uiState.update { it.copy(
                    errorGeneral = mensaje,
                    registradoExito = false
                ) }
            }
        ){
            val request = CrearUsuariosRequest(
                rut = estado.rut.trim(),
                nombre = estado.nombre.trim(),
                telefono = estado.telefono.trim(),
                correo = estado.correo.trim(),
                giro = if (estado.rol == Rol.CLIENTE) estado.giro.trim() else null,
                contactoCargo = if (estado.rol == Rol.CLIENTE) estado.contactoCargo.trim() else null,
                rol = estado.rol.name
            )
            val respuestaServidor = repositorio.crearUsuario(request)

            _uiState.update { it.copy(
                registradoExito = true,
                claveTemporal = respuestaServidor.claveTemporal,
                errorGeneral = null
            ) }

            viewModelScope.launch {
                delay(4000)
                _uiState.update { it.copy(registradoExito = false) }
            }
            onExito()
        }
    }

    fun onLimpiar(){
        _uiState.value = FormularioRegistros()
    }
}
