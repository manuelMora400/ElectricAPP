package com.example.electricapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.model.FormularioProtocolosState
import com.example.electricapp.model.InstrumentoEjemplo
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


class FormularioProtocolosViewModel : ViewModel(){

    val equipos = listOf(
        "TR-01 - Planta Lampa",
        "TR-02 - Planta Lampa",
        "TR-A - Planta Elevadora Sur",
        "TR-SS-01 - Faena Til Til"
    )

    val devanados = listOf("AT-BT", "AT-T", "BT-T")

    val instrumentos = listOf(
        InstrumentoEjemplo("Megohmetro 5 kV", "15/03/2027"),
        InstrumentoEjemplo("Megohmetro 10 kV", "10/10/2026")
    )
    private val tecnicoSesion = "Matias Godoy Herrera"

    //------------------------------------------------------------------------------------------//
    private val _uiState = MutableStateFlow(estadoInicial())
    val uiState : StateFlow<FormularioProtocolosState> = _uiState.asStateFlow()

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

    private fun estadoInicial() : FormularioProtocolosState{
        return FormularioProtocolosState(
            fechaHora = fechaActual("dd/MM/yyyy HH:mm"),
            tecnico = tecnicoSesion
        )
    }

    private fun fechaActual(formato : String) : String{
        return SimpleDateFormat(formato, Locale.getDefault()).format(Date())
    }


    private fun numero(valor : String) : Double?{
        return valor.replace(",", ".").toDoubleOrNull()
    }

    private fun formatoFecha(fecha : String) : Int?{

        val parte = fecha.split("/")

        if(parte.size != 3) return null

        val dias =  parte[0]
        val meses = parte[1]
        val anios = parte[2]

        return "$anios$meses$dias".toIntOrNull()
    }

    private fun errorCalibracion(nombre : String) : String?{

        val instrumento = instrumentos.find { it.nombre == nombre }
        if(instrumento == null) return null

        val hoy = formatoFecha(fechaActual("dd/MM/yyyy"))
        val vencimiento = formatoFecha(instrumento.fechaVencimiento)

        return if(hoy != null && vencimiento != null && vencimiento < hoy){
            "Calibracion vencida el ${instrumento.fechaVencimiento}, no se puede usar"
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
            mensajeExito = !hayErrores
        ) }

        return !hayErrores
    }

    fun onRegistrar() : Boolean{

        val valido = validarCampos()
        if(!valido) return false


        _uiState.update { it.copy(mensajeExito = true) }

        viewModelScope.launch {
            delay(7000)
            _uiState.update { it.copy(mensajeExito = false) }
        }
        return true
    }

    fun onLimpiar(){
        _uiState.value = estadoInicial()
    }
}
