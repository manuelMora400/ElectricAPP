package com.example.electricapp.Data

import com.example.electricapp.data.ProtocoloRepository
import com.example.electricapp.model.Rol
import com.example.electricapp.viewModel.HistorialViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

class HistorialViewModelTest {
    private lateinit var apiFalsa: FakeProtocoloApi
    private lateinit var viewModel: HistorialViewModel

    @Before
    fun preparar() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        apiFalsa = FakeProtocoloApi()
        viewModel = HistorialViewModel(ProtocoloRepository(api = apiFalsa))
    }

    @After
    fun limpiar() {
        Dispatchers.resetMain()
    }

    private fun buscarComoTecnico(tag: String, instalacion: String, onExito: () -> Unit = {}) {
        viewModel.onRol(Rol.TECNICO)
        viewModel.onTagBuscar(tag)
        viewModel.onInstalacion(instalacion)
        viewModel.Busqueda(onExito)
    }
}