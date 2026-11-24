package com.example.electricapp.viewmodel

import com.example.electricapp.viewModel.EditarPerfilViewModel
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EditarPerfilTest {

    private lateinit var viewModel : EditarPerfilViewModel


    private fun rellenlar(){
        viewModel.onNombreCambia("Manuel Ignacio Mora Acuña")
        viewModel.onTelefonoCambia("+56 9 3131 2908")
        viewModel.onCorreoCambia("Manuel@gmail.com")
    }

    @Before
    fun preparar(){
        viewModel = EditarPerfilViewModel()
    }


    @Test
    fun onGuardar_NoCumpleUnaReglas_ResultadoError(){

        rellenlar()

        viewModel.onNombreCambia("manue7777cr7")
        viewModel.onGuardar({})
        val estado = viewModel.uiState.value

        assertEquals( "Ingresa nombre y apellido, solo letras", estado.errorNombre)
    }

    @Test
    fun puedeEditarCorreo(){
        rellenlar()

        viewModel.onNombreCambia("")
        viewModel.onCorreoCambia("manuelgmail.com")
        viewModel.onGuardar({})

        val estado = viewModel.uiState.value

        assertEquals("El nombre es obligatorio", estado.errorNombre)
        assertNull(estado.errorCorreo)
    }

    @Test
    fun onGuardar_datosVacios_mostrarError(){
        rellenlar()

        viewModel.onCorreoCambia("")
        viewModel.onNombreCambia("")
        viewModel.onTelefonoCambia("")
        viewModel.onGuardar ({})

        val estado = viewModel.uiState.value

        assertEquals("El nombre es obligatorio", estado.errorNombre)
        assertNull(estado.errorCorreo)
        assertEquals("El telefono es obligatorio", estado.errorTelefono)


    }


}