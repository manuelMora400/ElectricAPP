package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.EquipoRepository
import com.example.electricapp.data.InstrumentoRepository
import com.example.electricapp.data.ProtocoloRepository
import com.example.electricapp.data.remote.CrearAislacionRequest
import com.example.electricapp.data.remote.CrearProtocoloRequest
import com.example.electricapp.data.remote.InstrumentoResponse
import com.example.electricapp.model.FormularioProtocolosState
import com.example.electricapp.model.ResultadoPrueba
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormularioProtocolosViewModel() : ViewModel() {

    private val repositorio = ProtocoloRepository()
    private val repositorioEquipos = EquipoRepository()
    private val repositorioInstrumentos = InstrumentoRepository()

    private val _equipos = MutableStateFlow<List<String>>(emptyList())
    val equipos : StateFlow<List<String>> = _equipos.asStateFlow()

    private val _devanados = MutableStateFlow<List<String>>(emptyList())
    val devanados : StateFlow<List<String>> = _devanados.asStateFlow()

    private var instrumentos : List<InstrumentoResponse> = emptyList()
    private val _nombresInstrumentos = MutableStateFlow<List<String>>(emptyList())
    val nombresInstrumentos : StateFlow<List<String>> = _nombresInstrumentos.asStateFlow()

    private var idTecnico = 0L
    private var tecnicoSesion = ""

    private val _uiState = MutableStateFlow(estadoInicial())
    val uiState : StateFlow<FormularioProtocolosState> = _uiState.asStateFlow()

    init {
        cargarListas()
    }

    private fun ejecutar(onError: (String) -> Unit, accion: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                accion()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error inesperado")
            }
        }
    }

    fun cargarListas(){
        ejecutar(
            onError = { mensaje ->
                _uiState.update { it.copy(errorGeneral = mensaje) }
            }
        ){
            _equipos.value = repositorioEquipos.listarEquipos().map { "${it.tag} - ${it.instalacion}" }
            _devanados.value = repositorio.listarDevanados().map { it.replace("_", "-") }
            instrumentos = repositorioInstrumentos.CargarInstrumentos()
            _nombresInstrumentos.value = instrumentos.map { textoInstrumento(it) }
        }
    }

    fun onEquipoCambia(valor : String){
        _uiState.update { it.copy(equipo = valor, errorEquipo = null, mensajeExito = false) }
    }

    fun onTemperaturaCambia(valor : String){
        _uiState.update { it.copy(temperatura = valor, errorTemperatura = null, mensajeExito = false) }
    }

    fun onHumedadCambia(valor : String){
        _uiState.update { it.copy(humedad = valor, errorHumedad = null, mensajeExito = false) }
    }

    fun onDevanadoCambia(valor : String){
        _uiState.update { it.copy(devanado = valor, errorDevanado = null, mensajeExito = false) }
    }

    fun onTensionCambia(valor : String){
        _uiState.update { it.copy(tensionPrueba = valor, errorTensionPrueba = null, mensajeExito = false) }
    }

    fun onLecturaUnMinutoCambia(valor : String){
        _uiState.update { it.copy(lecturaUnMinuto = valor, errorLecturaUnMinuto = null, mensajeExito = false) }
        calcularResultado()
    }

    fun onLecturaDiezMinutosCambia(valor : String){
        _uiState.update { it.copy(lecturaDiezMinutos = valor, errorLecturaDiezMinutos = null, mensajeExito = false) }
        calcularResultado()
    }

    fun onInstrumentoCambia(valor : String){
        _uiState.update { it.copy(instrumento = valor, errorInstrumento = errorCalibracion(valor), mensajeExito = false) }
    }

    fun onTecnicoSesion(id : Long, nombre : String){
        idTecnico = id
        tecnicoSesion = nombre
        _uiState.update { it.copy(tecnico = nombre) }
    }

    private fun textoInstrumento(instrumento : InstrumentoResponse) : String{
        return "${instrumento.nombre} (${instrumento.numeroSerie})"
    }

    private fun estadoInicial() : FormularioProtocolosState{
        return FormularioProtocolosState(
            fechaHora = fechaActual("dd/MM/yyyy HH:mm"),
            tecnico = tecnicoSesion
        )
    }

    private fun fechaActual(formato : String) : String{
        return SimpleDateFormat(formato, Locale.getDefault()).format(Date())
    }

    private fun fechaBackendAUi(fechaIso : String) : String{
        val partes = fechaIso.split("-")
        return if (partes.size == 3) "${partes[2]}/${partes[1]}/${partes[0]}" else fechaIso
    }

    private fun numero(valor : String) : Double?{
        return valor.replace(",", ".").toDoubleOrNull()
    }

    private fun errorCalibracion(nombre : String) : String?{

        val instrumento = instrumentos.find { textoInstrumento(it) == nombre }
        if(instrumento == null) return null

        return if(!instrumento.vigente){
            "Calibracion vencida el ${fechaBackendAUi(instrumento.fechaVencimiento)}, no se puede usar"
        }else
            null
    }

    private fun calcularResultado(){

        val estado = _uiState.value
        val lecturaUno = numero(estado.lecturaUnMinuto)
        val lecturaDiez = numero(estado.lecturaDiezMinutos)

        if(lecturaUno == null || lecturaDiez == null || lecturaUno <= 0 || lecturaDiez <= 0){
            _uiState.update { it.copy(indicePolarizacion = null, resultado = null) }
            return
        }

        val indice = lecturaDiez / lecturaUno

        val resultado =
            if(lecturaUno < 100){
                ResultadoPrueba.RECHAZADO
            }else if(indice >= 2.0){
                ResultadoPrueba.APROBADO
            }else if(indice >= 1.0){
                ResultadoPrueba.OBSERVADO
            }else
                ResultadoPrueba.RECHAZADO

        _uiState.update { it.copy(indicePolarizacion = indice, resultado = resultado) }
    }

    private fun resultadoBackend(resultado : ResultadoPrueba) : String{
        return if(resultado == ResultadoPrueba.OBSERVADO) "OBSERVACION" else resultado.name
    }

    private fun validarCampos() : Boolean{

        val estado = _uiState.value
        val temperatura = numero(estado.temperatura)
        val humedad = numero(estado.humedad)
        val tension = numero(estado.tensionPrueba)
        val lecturaUno = numero(estado.lecturaUnMinuto)
        val lecturaDiez = numero(estado.lecturaDiezMinutos)

        val errorEquipo =
            if(estado.equipo.isBlank()){
                "Debe seleccionar un equipo"
            }else
                null

        val errorTemperatura =
            if(estado.temperatura.isBlank()){
                "La temperatura ambiente es obligatoria"
            }else if(temperatura == null){
                "La temperatura debe ser un numero"
            }else if(temperatura !in -20.0..60.0){
                "La temperatura debe estar entre -20 y 60 °C"
            }else
                null

        val errorHumedad =
            if(estado.humedad.isBlank()){
                "La humedad relativa es obligatoria"
            }else if(humedad == null){
                "La humedad debe ser un numero"
            }else if(humedad !in 0.0..100.0){
                "La humedad debe estar entre 0 y 100 %"
            }else
                null

        val errorDevanado =
            if(estado.devanado.isBlank()){
                "Debe seleccionar el devanado probado"
            }else
                null

        val errorTension =
            if(estado.tensionPrueba.isBlank()){
                "La tension de prueba es obligatoria"
            }else if(tension == null){
                "La tension debe ser un numero"
            }else if(tension <= 0){
                "La tension de prueba debe ser mayor a 0"
            }else if(tension > 10){
                "La tension de prueba no puede superar los 10 kV"
            }else
                null

        val errorLecturaUno =
            if(estado.lecturaUnMinuto.isBlank()){
                "La lectura a 1 minuto es obligatoria"
            }else if(lecturaUno == null){
                "La lectura debe ser un numero"
            }else if(lecturaUno <= 0){
                "La lectura no puede ser negativa ni cero"
            }else
                null

        val errorLecturaDiez =
            if(estado.lecturaDiezMinutos.isBlank()){
                "La lectura a 10 minutos es obligatoria"
            }else if(lecturaDiez == null){
                "La lectura debe ser un numero"
            }else if(lecturaDiez <= 0){
                "La lectura no puede ser negativa ni cero"
            }else
                null

        val errorInstrumento =
            if(estado.instrumento.isBlank()){
                "Debe seleccionar el instrumento utilizado"
            }else
                errorCalibracion(estado.instrumento)

        val hayErrores = listOf(
            errorEquipo,
            errorTemperatura,
            errorHumedad,
            errorDevanado,
            errorTension,
            errorLecturaUno,
            errorLecturaDiez,
            errorInstrumento
        ).any { it != null }

        _uiState.update { it.copy(
            errorEquipo = errorEquipo,
            errorTemperatura = errorTemperatura,
            errorHumedad = errorHumedad,
            errorDevanado = errorDevanado,
            errorTensionPrueba = errorTension,
            errorLecturaUnMinuto = errorLecturaUno,
            errorLecturaDiezMinutos = errorLecturaDiez,
            errorInstrumento = errorInstrumento,
            errorGeneral = null,
            mensajeExito = false
        ) }

        return !hayErrores
    }

    fun onRegistrar(onExito : () -> Unit = {}){

        val valido = validarCampos()
        if(!valido) return

        val estado = _uiState.value
        val instrumento = instrumentos.find { textoInstrumento(it) == estado.instrumento }
        val resultado = estado.resultado

        if(instrumento == null || resultado == null || idTecnico == 0L){
            _uiState.update { it.copy(errorGeneral = "No se pudo registrar, vuelva a iniciar sesion") }
            return
        }

        val partes = estado.equipo.split(" - ")
        val tag = partes[0]
        val instalacion = if(partes.size > 1) partes[1] else ""

        ejecutar(
            onError = { mensaje ->
                _uiState.update { it.copy(errorGeneral = mensaje) }
            }
        ){
            val protocolo = repositorio.registrarProtocolo(
                CrearProtocoloRequest(
                    temperatura = numero(estado.temperatura) ?: 0.0,
                    humedad = numero(estado.humedad) ?: 0.0,
                    resultadoPrueba = resultadoBackend(resultado),
                    idTecnico = idTecnico,
                    idInstrumento = instrumento.id
                )
            )

            repositorio.registrarAislacion(
                CrearAislacionRequest(
                    tag = tag,
                    instalacion = instalacion,
                    enumDevanado = estado.devanado.replace("-", "_"),
                    tension = numero(estado.tensionPrueba) ?: 0.0,
                    lectura1Min = numero(estado.lecturaUnMinuto) ?: 0.0,
                    lectura10Min = numero(estado.lecturaDiezMinutos) ?: 0.0,
                    idProtocolo = protocolo.idProtocolo
                )
            )

            _uiState.value = estadoInicial().copy(mensajeExito = true)
            onExito()

            delay(7000)
            _uiState.update { it.copy(mensajeExito = false) }
        }
    }

    fun onLimpiar(){
        _uiState.value = estadoInicial()
    }
}
