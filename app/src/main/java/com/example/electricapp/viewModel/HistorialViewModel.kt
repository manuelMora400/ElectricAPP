package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.model.HistorialState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistorialViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(HistorialState())

    val uiState: StateFlow<HistorialState> = _uiState.asStateFlow()

    fun onTagBuscar (valor : String){
        _uiState.update { currentState -> currentState.copy(tagBuscar = valor, tagError = null, busquedaExitosa = false) }
    }

    fun onInstalacion (valor: String){
        _uiState.update { currentState -> currentState.copy(instalacion = valor, intalacionError = null, busquedaExitosa = false) }
    }

    fun Busqueda() : Boolean{
        val estado = _uiState.value

        val errorSerie =
            if (estado.tagBuscar.isBlank()){
                "El campo debe tener informacion"
            }else if (estado.tagBuscar.length > 12){
                "El este campo no puede ser mayor a 15 caracteres"
            }else if (!estado.tagBuscar.contains("-")){
                "La serie debe tener - "
            }else{
                null
            }

        val errorLugar =
            if ( estado.instalacion.isBlank()){
                "Instalacion no puede estar vacia"
            }else if (estado.instalacion.length !in 10..25){
                "Este campo no acepta menos de 10 y mas de 25 caracteres"
            }else{
                null
            }

        val Errores = listOf(errorSerie, errorLugar).any{ it != null }
        _uiState.update { it.copy(
            tagError = errorSerie,
            intalacionError = errorLugar,
            busquedaExitosa = !Errores
        ) }

        if(!Errores){
            viewModelScope.launch {
                delay(4000)
                _uiState.update { it.copy(busquedaExitosa = false) }
            }
        }
        return !Errores
    }
}