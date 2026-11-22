package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.SesionPreferencias
import com.example.electricapp.data.UsuarioRepository
import com.example.electricapp.data.remote.CambiarClaveRequest
import com.example.electricapp.model.FormularioLoginState
import com.example.electricapp.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class FormularioLoginViewModel(application : Application): AndroidViewModel(application){

    private  val repositorio = UsuarioRepository()
    private val sesion = SesionPreferencias(application)

    private val _uiState = MutableStateFlow(FormularioLoginState())
    val uiState : StateFlow<FormularioLoginState> = _uiState.asStateFlow()



    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()


    init {
        restaurarSesion()
    }

    fun onCorreoCambia (valor : String){
        _uiState.update{it.copy(correo = valor, errorCorreo = null, errorGeneral = null, logeadoExitoso = false)}
    }
    fun onPasswordCambia(valor : String){
        _uiState.update { it.copy(passwordHash = valor, errorPassword = null, errorGeneral = null, logeadoExitoso = false) }
    }
    fun onVisibilidad (){
        _uiState.update {it.copy(passwordVisible = !it.passwordVisible)}
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


    private fun restaurarSesion() {
        val id = sesion.leer()
        if (id == -1L) return
        ejecutar(onError = {sesion.borrar()}){
            val perfil = repositorio.obtenerPefil(id)
            val rol = Rol.valueOf(perfil.rol.uppercase())
            _uiState.update {
                it.copy(
                    idUsuario = perfil.id,
                    nombre = perfil.nombre,
                    logeadoExitoso = true,
                    cambiarClave = perfil.claveTemporal !=null ,
                    rol = rol
                )
            }
        }
    }

    // parte de cambiar clave
    fun onNuevaClave(valor : String){
        _uiState.update {it.copy(nuevaClave = valor, errorNuevaClave = null, errorGeneral = null, logeadoExitoso = false,
            cumpleLargoPassword = valor.length in 8..12,
            cumpleMayusculaPassword = valor.any{it.isUpperCase()},
            cumpleSiboloPassword = valor.contains(".") || valor.contains("-"),)}
    }
    fun onConfirmarClave(valor: String){

        _uiState.update {it.copy(confirmarClave = valor, errorConfirmarClave = null, errorGeneral = null, logeadoExitoso = false)}
    }

    fun onGuardarClaveNueva(onExito: () -> Unit){

        val estado = _uiState.value
        val nuevaClave = estado.nuevaClave

        val errorNuevaPassword =

            if (nuevaClave.isBlank()){
                "La contraseña no puede estar vacia"
            }else if(!nuevaClave.any{it.isUpperCase()}){
                "La contraseña debe contener al menos una letra en mayuscula"
            }else if(!nuevaClave.contains(".") && !nuevaClave.contains("-")){
                "La contraseña debe contener al menos un punto ( . ) o guion ( - )"
            }else if (nuevaClave.length !in 8..12){
                "La contraseña debe tener un minimo de 8 y maximo de 12 digitos"
            }else if(nuevaClave == estado.passwordHash){
                "La contraseña no debe ser igual a la anterior"
            }else
                null

        val errorConfirmarClave =
            if(estado.confirmarClave.isBlank()){
                "Debe confirmar su clave"
            }else if(estado.confirmarClave != estado.nuevaClave){
                "Las contraseñas no coinciden"
            }else
                null

        if(errorNuevaPassword != null || errorConfirmarClave != null){
          _uiState.update { it.copy(
              errorNuevaClave = errorNuevaPassword,
              errorConfirmarClave = errorConfirmarClave
          ) }
          return
        }

       ejecutar(
           onError = { mensaje ->
               _uiState.update { it.copy(
                   errorGeneral = mensaje,
                   logeadoExitoso = false
               ) }
           }
       ){
           val datos = CambiarClaveRequest(
               claveActual = estado.passwordHash,
               claveNueva = nuevaClave,
               confirmarClave = estado.confirmarClave
           )
           repositorio.cambiarClave(
               id = estado.idUsuario,
               datos  = datos
           )
           _uiState.update { it.copy(
               cambiarClave = false,
               passwordHash = nuevaClave,
               nuevaClave = "",
               confirmarClave = ""
           ) }
           onExito()
       }
    }

    fun onIdentificadiorClave( valor: String){
        _uiState.update { it.copy(idRecuperacion = valor, errorRecuperacion = null) }
    }

    fun onEnviarClaveTemporal() {
        val id = _uiState.value.idRecuperacion.trim()
        if (id.isBlank()) {
            _uiState.update { it.copy(errorRecuperacion = "Ingresa tu RUT o correo correspondiente") }
            return
        }
        ejecutar(

            onError = { mensaje ->
                _uiState.update { it.copy(
                    errorRecuperacion = mensaje,
                    logeadoExitoso = false
                ) }
            }
        ){
            val respuestaServidor = repositorio.recuperarClave(
                    identificador = id
            )
            _uiState.update { it.copy(
                errorRecuperacion = null,
                telefonoRecuperacion = respuestaServidor.telefono,
                claveTemporal = respuestaServidor.claveTemporal
            ) }
        }
    }

    fun onClaveTemporalEnviada() {
        onLimpiarRecuperacion()
    }

    fun onLimpiarRecuperacion() {
        _uiState.update {
            it.copy(
                idRecuperacion = "",
                errorRecuperacion = null,
                telefonoRecuperacion = null,
                claveTemporal = null
            )
        }
    }

    fun onIniciar(onExito : (Rol) -> Unit){

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
          return
        }

       ejecutar(
           onError = { mensaje ->
               _uiState.update { it.copy(
                   errorGeneral = mensaje,
                   logeadoExitoso = false
               ) }
           }
       ){
           val respuestaServidor = repositorio.login(
               correo = estado.correo.trim(),
               password = estado.passwordHash
           )
           val rolEmum = Rol.valueOf(respuestaServidor.rol.uppercase())

           _uiState.update { it.copy(
               idUsuario = respuestaServidor.idUsuario,
               nombre = respuestaServidor.nombre,
               errorCorreo = null,
               errorPassword = null,
               errorGeneral = null,
               logeadoExitoso = true,
               cambiarClave = respuestaServidor.debeCambiarClave,
               rol = rolEmum
           ) }
           sesion.guardar(respuestaServidor.idUsuario)
           onExito(rolEmum)
       }
    }

    fun onNombreActualizado(nombre : String){
        _uiState.update { it.copy(nombre = nombre) }
    }

    fun onCerrarSesion(){
        sesion.borrar()
        _uiState.update { FormularioLoginState() }
    }
}
