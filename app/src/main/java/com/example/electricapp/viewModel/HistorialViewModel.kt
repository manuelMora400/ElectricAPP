package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.HistorialState
import com.example.electricapp.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HistorialViewModel(application: Application) : AndroidViewModel(application) {

    private val db = ElectricHelperDb(application)

    private val _uiState = MutableStateFlow(HistorialState())
    val uiState: StateFlow<HistorialState> = _uiState.asStateFlow()

    fun onRol(rol: Rol, zonaCliente: String = "") {
        _uiState.update { currentState ->
            currentState.copy(
                rolUsuario = rol,
                zonaCliente = zonaCliente,
                instalacion = if (rol == Rol.CLIENTE && zonaCliente.isNotBlank()) {
                    zonaCliente
                } else {
                    currentState.instalacion
                }
            )
        }
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
            estado.tagBuscar.length > 12 ->
                "Este campo no puede ser mayor a 12 caracteres"
            !estado.tagBuscar.contains("-") ->
                "La serie debe tener -"
            else -> null
        }

        // Si es Cliente, usa su zona asignada; si es Técnico/Jefe, valida el campo escrito
        val instalacionAFiltrar = if (estado.Cliente) {
            estado.zonaCliente.trim()
        } else {
            estado.instalacion.trim()
        }

        val errorLugar = if (estado.Cliente) {
            null
        } else {
            when {
                estado.instalacion.isBlank() ->
                    "Instalación no puede estar vacía"
                estado.instalacion.length !in 5..30 ->
                    "Este campo no acepta menos de 5 y más de 30 caracteres"
                else -> null
            }
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

        val protocolosEncontrados = db.filtrarProtocolo(
            tag = estado.tagBuscar.trim(),
            instalacion = instalacionAFiltrar
        )

        return if (protocolosEncontrados.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    tagError = null,
                    intalacionError = null,
                    busquedaExitosa = true,
                    listaProtocolo = protocolosEncontrados
                )
            }
            true
        } else {
            val mensajeError = if (estado.Cliente) {
                "No hay protocolos para ese TAG en tu zona (${estado.zonaCliente})"
            } else {
                "No hay protocolos para ese TAG en esa instalación"
            }
            _uiState.update {
                it.copy(
                    tagError = if (estado.Cliente) mensajeError else null,
                    intalacionError = if (!estado.Cliente) mensajeError else null,
                    busquedaExitosa = false,
                    listaProtocolo = emptyList()
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