package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.ContadoresPanelJefe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JefeTecnicoViewModel(application: Application) : AndroidViewModel(application) {

    private val bd = ElectricHelperDb(application)
    private  val _uistate = MutableStateFlow(ContadoresPanelJefe())
    val uistate : StateFlow<ContadoresPanelJefe> = _uistate.asStateFlow()

    init {cargarResumenes()}

    fun cargarResumenes(){
        viewModelScope.launch (Dispatchers.IO){
            val registroContadores = ContadoresPanelJefe(
                cuentasActivas = bd.cantidadCuentasActivas(),
                instrumentosRegistrados = bd.cantidadInstRegistrados()
            )
            _uistate.update {registroContadores}
        }
    }
    override fun onCleared() {
        bd.close()
        super.onCleared()
    }
}