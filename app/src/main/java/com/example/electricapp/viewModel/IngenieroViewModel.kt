package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.ContadoresPanelIngeniero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IngenieroViewModel(application: Application) : AndroidViewModel(application) {

    private val bd = ElectricHelperDb(application)
    private val _uistate = MutableStateFlow(ContadoresPanelIngeniero())
    val uistate : StateFlow<ContadoresPanelIngeniero> = _uistate.asStateFlow()

    init {cargarResumenes()}

    fun cargarResumenes(){
        viewModelScope.launch (Dispatchers.IO){
            val registroContadores = ContadoresPanelIngeniero(
                porRevisar = bd.cantidadPorRevisar(),
                enObservacion = bd.cantidadEnObservacion(),
                formalizados = bd.cantidadFormalizados()
            )
            _uistate.update {registroContadores}
        }
    }
    override fun onCleared() {
        bd.close()
        super.onCleared()
    }
}
