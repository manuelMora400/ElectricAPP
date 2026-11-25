package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ProtocoloRepository
import com.example.electricapp.model.ContadoresVistaTecnico
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TecnicoViewModel() : ViewModel() {

    private val repositorio = ProtocoloRepository()
    private val _uistate = MutableStateFlow(ContadoresVistaTecnico())
    val uistate : StateFlow<ContadoresVistaTecnico> = _uistate.asStateFlow()

    private fun ejecutar(onError: (String) -> Unit = {}, accion: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                accion()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error inesperado")
            }
        }
    }

    fun cargarResumenes(idTecnico : Long){
        ejecutar {
            val protocolos = repositorio.protocolosDelTecnico(idTecnico)
            _uistate.update {
                ContadoresVistaTecnico(
                    misProtocolos = protocolos.size,
                    porFormalizar = protocolos.count { it.estadoProtocolo == "PENDIENTE" },
                    enObservacion = protocolos.count { it.resultadoPrueba == "OBSERVACION" }
                )
            }
        }
    }
}
