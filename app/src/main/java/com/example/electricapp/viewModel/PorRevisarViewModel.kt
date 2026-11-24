package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ProtocoloRepository
import com.example.electricapp.model.ProtocoloRevision
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PorRevisarViewModel() : ViewModel() {

    private val repositorio = ProtocoloRepository()
    private val _uistate = MutableStateFlow<List<ProtocoloRevision>>(emptyList())
    val uistate : StateFlow<List<ProtocoloRevision>> = _uistate.asStateFlow()

    fun cargarProtocolos(){
        viewModelScope.launch {
            try {
                val protocolos = repositorio.buscarHistorial("", "")
                    .filter { !it.formalizado }
                    .sortedBy { it.idProtocolo }
                    .map {
                        ProtocoloRevision(
                            idProtocolo = it.idProtocolo.toInt(),
                            tag = it.tagBuscar,
                            instalacion = it.instalacion,
                            fechaPrueba = it.fechaPrueba
                        )
                    }
                _uistate.update { protocolos }
            } catch (e: Exception) {
                _uistate.update { emptyList() }
            }
        }
    }
}
