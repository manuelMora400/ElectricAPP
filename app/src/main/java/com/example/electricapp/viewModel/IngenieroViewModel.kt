package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ProtocoloRepository
import com.example.electricapp.model.ContadoresPanelIngeniero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IngenieroViewModel() : ViewModel() {

    private val repositorio = ProtocoloRepository()
    private val _uistate = MutableStateFlow(ContadoresPanelIngeniero())
    val uistate : StateFlow<ContadoresPanelIngeniero> = _uistate.asStateFlow()

    private fun ejecutar(onError: (String) -> Unit = {}, accion: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                accion()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error inesperado")
            }
        }
    }

    fun cargarResumenes(){
        ejecutar {
            val protocolos = repositorio.listarProtocolos()
            _uistate.update {
                ContadoresPanelIngeniero(
                    porRevisar = protocolos.count { it.estadoProtocolo == "PENDIENTE" },
                    enObservacion = protocolos.count { it.resultadoPrueba == "OBSERVACION" },
                    formalizados = protocolos.count { it.estadoProtocolo == "FORMALIZADO" }
                )
            }
        }
    }
}
