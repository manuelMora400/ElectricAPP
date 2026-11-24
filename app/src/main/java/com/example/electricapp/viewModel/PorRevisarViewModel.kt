package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.ProtocoloRevision
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PorRevisarViewModel(application: Application) : AndroidViewModel(application) {

    private val bd = ElectricHelperDb(application)
    private val _uistate = MutableStateFlow<List<ProtocoloRevision>>(emptyList())
    val uistate : StateFlow<List<ProtocoloRevision>> = _uistate.asStateFlow()

    init {cargarProtocolos()}

    fun cargarProtocolos(){
        viewModelScope.launch (Dispatchers.IO){
            val protocolos = bd.protocolosPorRevisar()
            _uistate.update {protocolos}
        }
    }
    override fun onCleared() {
        bd.close()
        super.onCleared()
    }
}
