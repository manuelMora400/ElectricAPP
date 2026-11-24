package com.example.electricapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricapp.data.EquipoRepository
import com.example.electricapp.data.UsuarioRepository
import com.example.electricapp.data.remote.CrearEquipoRequest
import com.example.electricapp.model.FormularioEquipos
import com.example.electricapp.model.FormularioRegistros
import com.example.electricapp.model.Rol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class EquiposViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio = EquipoRepository()
    private val repositorioUsuarios = UsuarioRepository()


    private val _equipos = MutableStateFlow<List<FormularioEquipos>>(emptyList())
    val equipos: StateFlow<List<FormularioEquipos>> = _equipos.asStateFlow()


    private val _clientes = MutableStateFlow<List<FormularioRegistros>>(emptyList())
    val clientes: StateFlow<List<FormularioRegistros>> = _clientes.asStateFlow()

    private val _uiState = MutableStateFlow(FormularioEquipos())
    val uiState: StateFlow<FormularioEquipos> = _uiState.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    init {
        cargarDatos()
    }


    fun cargarDatos() {
        ejecutar(
            onError = { mensaje ->
                _uiState.update { it.copy(errorGeneral = mensaje) }
            }
        ) {
            val clientesRemotos = repositorioUsuarios.listarUsuarios()
                .filter { it.rol.equals(Rol.CLIENTE.name, ignoreCase = true) && it.activo }
                .map { FormularioRegistros(id = it.id, nombre = it.nombre, rol = Rol.CLIENTE) }
            _clientes.value = clientesRemotos

            val equiposRemotos = repositorio.listarEquipos()
            _equipos.value = equiposRemotos.map { equipo ->
                FormularioEquipos(
                    id = equipo.id,
                    tag = equipo.tag,
                    tipo = equipo.tipo,
                    instalacion = equipo.instalacion,
                    idCliente = equipo.idCliente,
                    cliente = clientesRemotos.find { it.id == equipo.idCliente }?.nombre ?: "Cliente no encontrado"
                )
            }
            _uiState.update { it.copy(errorGeneral = null) }
        }
    }

    private fun ejecutar(onError: (String) -> Unit, accion: suspend () -> Unit) {
        viewModelScope.launch {
            _cargando.value = true
            try {
                accion()
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error inesperado")
            } finally {
                _cargando.value = false
            }
        }
    }

    fun onTagCambia(valor: String) {
        _uiState.update { it.copy(tag = valor, errorTag = null, errorGeneral = null, registradoExito = false) }
    }

    fun onTipoCambia(valor: String) {
        _uiState.update { it.copy(tipo = valor, errorTipo = null, errorGeneral = null, registradoExito = false) }
    }

    fun onInstalacionCambia(valor: String) {
        _uiState.update { it.copy(instalacion = valor, errorInstalacion = null, errorGeneral = null, registradoExito = false) }
    }

    fun onClienteCambia(valor: String) {
        _uiState.update { it.copy(cliente = valor, errorCliente = null, errorGeneral = null, registradoExito = false) }
    }

    fun onRegistrar(onExito: () -> Unit) {


        val formatoTag = Regex("^[A-Za-z0-9-]+$")
        val estado = _uiState.value

        val errorTag =
            if (estado.tag.isBlank()) {
                "El TAG es obligatorio"
            } else if (estado.tag != estado.tag.trim()) {
                "El TAG no debe contener espacios al inicio o al final"
            } else if (estado.tag.length > 20) {
                "El TAG no puede superar 20 caracteres"
            } else if (!estado.tag.matches(formatoTag)) {
                "El TAG solo puede tener letras, numeros y guion (ej: TR-01)"
            } else
                null

        val errorTipo =
            if (estado.tipo.isBlank()) {
                "El tipo de equipo es obligatorio"
            } else if (estado.tipo.trim().length !in 3..40) {
                "El tipo debe tener entre 3 a 40 caracteres"
            } else
                null

        val errorInstalacion =
            if (estado.instalacion.isBlank()) {
                "La instalacion es obligatoria"
            } else if (estado.instalacion.trim().length !in 3..80) {
                "La instalacion debe tener entre 3 a 80 caracteres"
            } else
                null

        val clienteElegido = _clientes.value.find { it.nombre == estado.cliente }
        val errorCliente =
            if (estado.cliente.isBlank() || clienteElegido == null) {
                "Seleccione el cliente dueño del equipo"
            } else
                null

        val hayErrores = listOf(errorTag, errorTipo, errorInstalacion, errorCliente).any { it != null }

        if (hayErrores || clienteElegido == null) {
            _uiState.update {
                it.copy(
                    errorTag = errorTag,
                    errorTipo = errorTipo,
                    errorInstalacion = errorInstalacion,
                    errorCliente = errorCliente,
                    registradoExito = false
                )
            }
            return
        }

        ejecutar(
            onError = { mensaje ->

                _uiState.update { it.copy(errorGeneral = mensaje, registradoExito = false) }
            }
        ) {
            val request = CrearEquipoRequest(
                tag = estado.tag.trim().uppercase(),
                tipo = estado.tipo.trim(),
                instalacion = estado.instalacion.trim(),
                idCliente = clienteElegido.id
            )
            repositorio.registrarEquipo(request)

            _uiState.update { FormularioEquipos(registradoExito = true) }
            onExito()
        }
    }

    fun onLimpiar() {
        _uiState.value = FormularioEquipos()
    }
}
