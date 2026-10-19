package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.electricapp.model.HistorialState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HistorialViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(HistorialState())

    val uiState: StateFlow<HistorialState> = _uiState.asStateFlow()

    fun onTagBuscar (valor : String){
        _uiState.update { currentState -> currentState.copy(tagBuscar = valor) }
    }

    fun onInstalacion (valor: String){
        _uiState.update { currentState -> currentState.copy(instalacion = valor) }
    }

    fun realizarBusqueda(){
        val tag = _uiState.value.tagBuscar
        val instalacion = _uiState.value.instalacion
    }

}