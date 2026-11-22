package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.EditarPerfilState
import com.example.electricapp.model.Rol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditarPerfilViewModel(application : Application) : AndroidViewModel(application){

    private val db = ElectricHelperDb(application)

    private val _uiState = MutableStateFlow(EditarPerfilState())
    val uiState : StateFlow<EditarPerfilState> = _uiState.asStateFlow()

    fun cargar(idUsuario : Long, rolSesion : Rol){
        viewModelScope.launch(Dispatchers.IO) {
            val perfil = db.buscarPerfil(idUsuario)
            if (perfil != null){
                _uiState.update {
                    it.copy(
                        idUsuario = perfil.idUsuario,
                        nombre = perfil.nombre,
                        correo = perfil.correo,
                        telefono = perfil.telefono,
                        cargo = perfil.cargo,
                        rut = perfil.rut,
                        rol = perfil.rol,
                        puedeEditarCorreo = rolSesion == Rol.JEFE_TECNICO
                    )
                }
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

    fun onCargoCambia(valor : String){
        _uiState.update { it.copy(cargo = valor, errorCargo = null, errorGeneral = null) }
    }

    fun onGuardar() : Boolean{

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
            }else if(db.correoEnUso(estado.correo, estado.idUsuario)){
                "Este correo ya esta en uso"
            }else
                null

        val errorTelefono =
            if(estado.telefono.isBlank()){
                "El telefono es obligatorio"
            }else if(!formatoTelefono.matches(estado.telefono.trim())){
                "Formato: +56 9 1234 5678"
            }else
                null

        val errorCargo =
            if(estado.rol == Rol.CLIENTE && estado.cargo.isBlank()){
                "El cargo es obligatorio"
            }else
                null

        val hayErrores = listOf(errorNombre, errorCorreo, errorTelefono, errorCargo).any{it != null}

        if(hayErrores){
            _uiState.update { it.copy(
                errorNombre = errorNombre,
                errorCorreo = errorCorreo,
                errorTelefono = errorTelefono,
                errorCargo = errorCargo
            ) }
            return false
        }

        val guardadoExito = db.actualizarPerfil(
            idUsuario = estado.idUsuario,
            nombre = estado.nombre.trim(),
            correo = estado.correo.trim(),
            telefono = estado.telefono.trim(),
            cargo = estado.cargo.trim()
        )

        if(!guardadoExito){
            _uiState.update { it.copy(errorGeneral = "Error al guardar sus datos") }
            return false
        }
        return true
    }

    override fun onCleared() {
        db.close()
        super.onCleared()
    }
}
