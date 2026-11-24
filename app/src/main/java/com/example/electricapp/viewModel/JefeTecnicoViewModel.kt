package com.example.electricapp.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.InstrumentoRepository
import com.example.electricapp.data.UsuarioRepository
import com.example.electricapp.model.ContadoresPanelJefe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JefeTecnicoViewModel() : ViewModel() {

    private  val repositorio = InstrumentoRepository()
    private  val repositorioUsu = UsuarioRepository()

    private  val _uistate = MutableStateFlow(ContadoresPanelJefe())
    val uistate : StateFlow<ContadoresPanelJefe> = _uistate.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    init {cargarResumenes()}

    private fun ejecutar(onError: (String) -> Unit = {}, accion: suspend () -> Unit) {
        viewModelScope.launch {
            _cargando.value = true
            try {
                accion()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrio un error inesperado")
            } finally {
                _cargando.value = false
            }
        }
    }

    fun cargarResumenes(){
       ejecutar(
           onError =  {}
       ){
           val contadorInstru = repositorio.contarInstrumentos().contador.toInt()
           val cuentasActivas = repositorioUsu.cantidadUsaurios().contador.toInt()
           _uistate.update { ContadoresPanelJefe(
               cuentasActivas = cuentasActivas,
               instrumentosRegistrados = contadorInstru
           ) }
       }
    }
}