package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.DetallesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetalleViewModel(application: Application) : AndroidViewModel(application){

    private val db = ElectricHelperDb(context = application)

    private val _uiState = MutableStateFlow(value = DetallesState())
    val uiState: StateFlow<DetallesState> = _uiState.asStateFlow()

    init {
        cargarDetalles()
    }

    fun cargarDetalles() {
        val detallesEncontrados = db.obtenerDetalle()

        _uiState.update { currentState ->
            val detalles = detallesEncontrados.firstOrNull()
            if (detalles != null) {
                detalles.copy(listaDetalles = detallesEncontrados)
            } else {
                currentState.copy(listaDetalles = emptyList())
            }
        }
    }

    fun seleccionarDetalle(detalle: DetallesState) {
        _uiState.update { currentState ->
            detalle.copy(listaDetalles = currentState.listaDetalles)
        }
    }

    override fun onCleared() {
        db.close()
        super.onCleared()
    }
}