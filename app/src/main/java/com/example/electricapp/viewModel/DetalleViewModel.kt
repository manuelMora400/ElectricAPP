package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ProtocoloRepository
import com.example.electricapp.model.DetallesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetalleViewModel(
    private val repositorio: ProtocoloRepository = ProtocoloRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetallesState(cargando = true))
    val uiState: StateFlow<DetallesState> = _uiState.asStateFlow()

    private var idCargado: Long? = null

    fun cargarDetalle(idProtocolo: Long, forzar: Boolean = false) {
        if (!forzar && idCargado == idProtocolo) return
        idCargado = idProtocolo

        _uiState.update { DetallesState(idProtocolo = idProtocolo, cargando = true) }

        viewModelScope.launch {
            try {
                val detalle = repositorio.obtenerDetalle(idProtocolo)
                _uiState.update { detalle }
            } catch (e: Exception) {
                idCargado = null   // permite reintentar
                _uiState.update {
                    DetallesState(idProtocolo = idProtocolo, error = e.message ?: "Error inesperado")
                }
            }
        }
    }

    fun seleccionarDetalle(detalle: DetallesState) {
        _uiState.update { actual -> detalle.copy(listaDetalles = actual.listaDetalles) }
    }
}
