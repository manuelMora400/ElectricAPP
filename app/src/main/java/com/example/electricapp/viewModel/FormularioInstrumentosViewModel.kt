package com.example.electricapp.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.InstrumentoRepository
import com.example.electricapp.data.remote.CrearInstrumentoRequest
import com.example.electricapp.data.remote.RenovarCalibracionRequest
import com.example.electricapp.model.FormularioInstrumentosStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FormularioInstrumentosViewModel() : ViewModel() {


    private val repositorio = InstrumentoRepository()

    private val _instrumentosBD = MutableStateFlow<List<FormularioInstrumentosStates>>(emptyList())
    val instrumentosBD: StateFlow<List<FormularioInstrumentosStates>> = _instrumentosBD.asStateFlow()

    private val _uiState = MutableStateFlow(FormularioInstrumentosStates())
    val uiState: StateFlow<FormularioInstrumentosStates> = _uiState.asStateFlow()

    init {
        cargarInstrumentos()
    }

    private fun ejecutar(
        onError: (String) -> Unit = {},
        accion: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                accion()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error inesperado")
            }
        }
    }

    private fun fechaUiABackend(fechaUi: String): String {
        val partes = fechaUi.split("/")
        return if (partes.size == 3) "${partes[2]}-${partes[1]}-${partes[0]}" else fechaUi
    }

    private fun fechaBackendAUi(fechaIso: String): String {
        val partes = fechaIso.split("-")
        return if (partes.size == 3) "${partes[2]}/${partes[1]}/${partes[0]}" else fechaIso
    }

    fun cargarInstrumentos() {
        ejecutar {
            val listaRemota = repositorio.CargarInstrumentos()
            _instrumentosBD.value = listaRemota.map { item ->
                FormularioInstrumentosStates(
                    id = item.id.toInt(),
                    nombre = item.nombre,
                    marca = item.marca,
                    modelo = item.modelo,
                    numSerie = item.numeroSerie,
                    fechaCalibracion = fechaBackendAUi(item.fechaCalibracion),
                    fechaVencimiento = fechaBackendAUi(item.fechaVencimiento),
                    vencido = !item.vigente
                )
            }
        }
    }

    fun onNombreCambia(valor: String) {
        _uiState.update { it.copy(nombre = valor, errorNombre = null, mensajeExito = false) }
    }

    fun onMarcaCambia(valor: String) {
        _uiState.update { it.copy(marca = valor, errorMarca = null, mensajeExito = false) }
    }

    fun onModeloCambia(valor: String) {
        _uiState.update { it.copy(modelo = valor, errorModelo = null, mensajeExito = false) }
    }

    fun onNumSerieCambia(valor: String) {
        _uiState.update { it.copy(numSerie = valor, errorNumeroSerie = null, mensajeExito = false) }
    }

    fun onFechaCalifracionCambia(valor: String) {
        _uiState.update { it.copy(fechaCalibracion = valor, errorFechaCalibracion = null, mensajeExito = false) }
    }

    fun onFechaVencimientoCambia(valor: String) {
        _uiState.update { it.copy(fechaVencimiento = valor, errorFechaVencimiento = null, mensajeExito = false) }
    }

    private fun formatoFecha(fecha: String): Int? {
        val parte = fecha.split("/")
        if (parte.size != 3) return null

        val dias = parte[0]
        val meses = parte[1]
        val anios = parte[2]

        return "$anios$meses$dias".toIntOrNull()
    }

    private fun validarCampos(soloRenovacion : Boolean = false): Boolean {
        val estado = _uiState.value
        val fechaCalibracion = formatoFecha(estado.fechaCalibracion)
        val fechaVencimiento = formatoFecha(estado.fechaVencimiento)

        val errorNombre =

            if(soloRenovacion){
                null
            } else if (estado.nombre.isBlank()) {
                "El nombre del Instrumento no debe estar vacio"
            } else if (estado.nombre.length !in 7..30) {
                "El nombre del instrumento debe tener entre 7 y 30 caracteres"
            } else
                null

        val errorMarca =
            if(soloRenovacion){
                null
            } else if (estado.marca.isBlank()) {
                "La marca representativa del instrumento no debe estar vacia"
            } else if (estado.marca.length !in 6..30) {
                "La marca del instrumento debe contener entre 6 a 30 caracteres"
            } else
                null

        val errorModelo =
            if(soloRenovacion){
                null
            } else if (estado.modelo.isBlank()) {
                "El modelo del instrumento no debe estar vacio"
            } else if (estado.modelo.length !in 6..40) {
                "El modelo del instrumento debe estar entre 6 a 40 caracteres"
            } else
                null

        val errorNumSerie =
            if (estado.numSerie.isBlank()) {
                "El numero de serie no debe estar vacio"
            } else if (estado.numSerie.length !in 6..25) {
                "El numero de serie debe contener entre 6 a 25 caracteres"
            } else if (estado.numSerie != estado.numSerie.trim()) {
                "El numero de serie no debe contener espacios en blanco"
            } else
                null

        val errorFechaCalibracion =
            if (estado.fechaCalibracion.isBlank()) {
                "La fecha de calibracion del instrumento no debe estar vacia"
            } else if (estado.fechaCalibracion.length != 10) {
                "El formato de fecha no debe tener mas 10 caracteres (DD/MM/AAAA)"
            } else
                null

        val errorFechaVencimiento =
            if (estado.fechaVencimiento.isBlank()) {
                "La fecha de vencimiento no puede estar vacia"
            } else if (estado.fechaVencimiento.length != 10) {
                "La fecha de vencimiento no debe tener mas de 10 caracteres (DD/MM/AAAA)"
            } else if (fechaVencimiento != null && fechaCalibracion != null && fechaVencimiento <= fechaCalibracion) {
                "La fecha de vencimiento debe ser posterior a la fecha de calibracion"
            } else
                null

        val hayErrores = listOf(
            errorNombre,
            errorMarca,
            errorModelo,
            errorNumSerie,
            errorFechaCalibracion,
            errorFechaVencimiento
        ).any { it != null }

        _uiState.update {
            it.copy(
                errorNombre = errorNombre,
                errorMarca = errorMarca,
                errorModelo = errorModelo,
                errorNumeroSerie = errorNumSerie,
                errorFechaCalibracion = errorFechaCalibracion,
                errorFechaVencimiento = errorFechaVencimiento,
                mensajeExito = false
            )
        }

        return !hayErrores
    }

    fun onRegistrar(onExito : () -> Unit = {}) {
        val valido = validarCampos()
        if (!valido) return

        val estado = _uiState.value
        val request = CrearInstrumentoRequest(
            nombre = estado.nombre.trim(),
            marca = estado.marca.trim(),
            modelo = estado.modelo.trim(),
            numeroSerie = estado.numSerie.trim(),
            fechaCalibracion = fechaUiABackend(estado.fechaCalibracion),
            fechaVencimiento = fechaUiABackend(estado.fechaVencimiento)
        )

        ejecutar(
            onError = { mensaje ->
                _uiState.update { it.copy(errorNumeroSerie = mensaje) }
            }
        ) {
            repositorio.RegistrarInstrumento(request)
            cargarInstrumentos()
            _uiState.update { it.copy(mensajeExito = true) }
            onExito()

            delay(7000)
            _uiState.update { it.copy(mensajeExito = false) }
        }
    }

    fun onActualizar(onExito : () -> Unit) {
        val actualizadoValido = validarCampos(soloRenovacion = true)
        if (!actualizadoValido) return

        val estado = _uiState.value

        ejecutar(
            onError = { mensaje ->
                _uiState.update { it.copy(errorNumeroSerie = mensaje)}
            }
        ){
            val actualizar = RenovarCalibracionRequest(
                fechaCalibracion = fechaUiABackend(estado.fechaCalibracion),
                fechaVencimiento = fechaUiABackend(estado.fechaVencimiento)
            )
            repositorio.renovarCalibracion(estado.numSerie, actualizar)
            cargarInstrumentos()
            _uiState.update { it.copy(mensajeExito = true) }
            onExito()

            delay(7000)
            _uiState.update { it.copy(mensajeExito = false) }
        }
    }

    fun onSeleccionarInstrumento(instrumento: FormularioInstrumentosStates) {
        _uiState.value = FormularioInstrumentosStates(
            id = instrumento.id,
            nombre = instrumento.nombre,
            marca = instrumento.marca,
            modelo = instrumento.modelo,
            numSerie = instrumento.numSerie,
            modoRenovar = true
        )
    }

    fun onLimpiar() {
        _uiState.value = FormularioInstrumentosStates()
    }
}