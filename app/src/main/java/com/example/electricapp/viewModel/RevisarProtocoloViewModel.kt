package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ProtocoloRepository
import com.example.electricapp.model.ProtocoloRevision
import com.example.electricapp.model.RevisarProtocoloState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RevisarProtocoloViewModel() : ViewModel() {

    private val repositorio = ProtocoloRepository()
    private val _uistate = MutableStateFlow(RevisarProtocoloState())
    val uistate : StateFlow<RevisarProtocoloState> = _uistate.asStateFlow()

    private fun ejecutar(onError: (String) -> Unit, accion: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                accion()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error inesperado")
            }
        }
    }

    fun cargar(idProtocolo : Int){
        ejecutar( onError = { mensaje ->
            _uistate.update { it.copy(errorGeneral = mensaje) }
        }
        ){
            val detalle = repositorio.obtenerDetalle(idProtocolo.toLong())
            _uistate.update {
                it.copy(
                    protocolo = ProtocoloRevision(
                        idProtocolo = detalle.idProtocolo.toInt(),
                        tag = detalle.Tag,
                        instalacion = detalle.Instalacion,
                        fechaPrueba = detalle.fechaPrueba,
                        devanado = detalle.devanado,
                        tensionPrueba = detalle.tensionPrueba,
                        medicion1Min = detalle.medicion1Min,
                        medicion10Min = detalle.medicion10Min,
                        resultado = detalle.resultado.uppercase(),
                        estado = detalle.estado
                    )
                )
            }
        }
    }

    fun onPedirFormalizar(){
        _uistate.update { it.copy(mostrarConfirmacion = true, errorGeneral = null) }
    }

    fun onCancelar(){
        _uistate.update { it.copy(mostrarConfirmacion = false) }
    }

    fun onConfirmarFormalizar(){
        val protocolo = _uistate.value.protocolo ?: return
        ejecutar(
            onError = { mensaje ->
                _uistate.update { it.copy(mostrarConfirmacion = false, errorGeneral = mensaje) }
            }
        ){
            repositorio.formalizar(protocolo.idProtocolo.toLong())
            _uistate.update { it.copy(mostrarConfirmacion = false, formalizado = true) }
        }
    }
}
