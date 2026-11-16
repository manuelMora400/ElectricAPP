package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.ElectricHelperDb
import com.example.electricapp.model.PerfilUsuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val db = ElectricHelperDb(application)

    private val _uiState = MutableStateFlow(PerfilUsuario())
    val uiState : StateFlow<PerfilUsuario> = _uiState.asStateFlow()

    fun cargar(idUsuario : Int){

        viewModelScope.launch(Dispatchers.IO) {
            val perfil = db.buscarPerfil(idUsuario)
            if (perfil != null) {
                _uiState.update { perfil }
            }
        }
    }

    override fun onCleared() {

        db.close()
        super.onCleared()

    }
}