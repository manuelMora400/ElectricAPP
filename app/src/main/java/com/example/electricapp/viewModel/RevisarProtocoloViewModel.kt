package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.RevisarProtocoloState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RevisarProtocoloViewModel(application: Application) : AndroidViewModel(application) {

    private val bd = ElectricHelperDb(application)
    private val _uistate = MutableStateFlow(RevisarProtocoloState())
    val uistate : StateFlow<RevisarProtocoloState> = _uistate.asStateFlow()

    fun cargar(idProtocolo : Int){
        viewModelScope.launch (Dispatchers.IO){
            val protocolo = bd.buscarProtocoloRevision(idProtocolo)
            _uistate.update { it.copy(protocolo = protocolo) }
        }
    }

    fun onPedirFormalizar(){
        _uistate.update { it.copy(mostrarConfirmacion = true) }
    }

    fun onCancelar(){
        _uistate.update { it.copy(mostrarConfirmacion = false) }
    }

    fun onConfirmarFormalizar(){
        val protocolo = _uistate.value.protocolo ?: return
        viewModelScope.launch (Dispatchers.IO){
            val ok = bd.formalizarProtocolo(protocolo.idProtocolo)
            _uistate.update { it.copy(mostrarConfirmacion = false, formalizado = ok) }
        }
    }

    override fun onCleared() {
        bd.close()
        super.onCleared()
    }
}
