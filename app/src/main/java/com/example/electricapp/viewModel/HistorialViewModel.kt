package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.HistorialState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistorialViewModel(application: Application) : AndroidViewModel(application) {

    private val db = ElectricHelperDb(application)

    private val _protocoloDb = MutableStateFlow<List<HistorialState>>(emptyList())

    private val _uiState = MutableStateFlow(HistorialState())
    val uiState: StateFlow<HistorialState> = _uiState.asStateFlow()

    init {
        cargarProtocolo()
    }
    fun cargarProtocolo(){
        _protocoloDb.value = db.filtrarProtocolo()
    }
    fun onTagBuscar(valor: String) {
        _uiState.update { currentState ->
            currentState.copy(
                tagBuscar = valor,
                tagError = null,
                busquedaExitosa = false
            )
        }
    }

    fun onInstalacion(valor: String) {
        _uiState.update { currentState ->
            currentState.copy(
                instalacion = valor,
                intalacionError = null,
                busquedaExitosa = false
            )
        }
    }

    fun Busqueda(): Boolean {
        val estado = _uiState.value

        val errorSerie = when {
            estado.tagBuscar.isBlank() ->
                "El campo debe tener información"
            estado.tagBuscar.length > 7 ->
                "Este campo no puede ser mayor a 7 caracteres"
            !estado.tagBuscar.contains("-") ->
                "La serie debe tener -"
            else -> null
        }

        val errorLugar = when {
            estado.instalacion.isBlank() ->
                "Instalación no puede estar vacía"
            estado.instalacion.length !in 5..15 ->
                "Este campo no acepta menos de 5 y más de 15 caracteres"
            else -> null
        }

        val hayErrores = listOf(errorSerie, errorLugar).any { it != null }

        if (hayErrores) {
            _uiState.update {
                it.copy(
                    tagError = errorSerie,
                    intalacionError = errorLugar,
                    busquedaExitosa = false
                )
            }
            return false
        }

        val protocoloEncontrado = db.buscarProtocolo(
            Tag = estado.tagBuscar,
            Instalacion = estado.instalacion,
            Fecha = estado.fechaPrueba!!
        )

        return if (protocoloEncontrado != null) {
            _uiState.update {
                it.copy(
                    tagError = null,
                    intalacionError = null,
                    busquedaExitosa = true
                )
            }
            true
        } else {
            _uiState.update {
                it.copy(
                    intalacionError = "Instalación no encontrada",
                    busquedaExitosa = false
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