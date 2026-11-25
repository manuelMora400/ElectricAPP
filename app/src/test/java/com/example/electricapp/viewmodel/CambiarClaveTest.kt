package com.example.electricapp.viewmodel

import com.example.electricapp.viewModel.CambiarClaveViewModel
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CambiarClaveTest {

    private lateinit var viewModel: CambiarClaveViewModel

    private  fun rellenar(){

        viewModel.onClaveActual("Man7elCR7.")
        viewModel.onNuevaClave("Abc.12345")
        viewModel.onConfirmarClave("Abc.12345")

    }

    @Before
    fun preparar(){
        viewModel = CambiarClaveViewModel()
    }

    @Test
    fun onNuevaClave_cumpleLargoPassword_cumplePalabraMayuscula_cumpleSimbolos_esperadoCumplirReglas(){

        rellenar()
        val estado = viewModel.uiState.value

        assertTrue(estado.cumpleLargoPassword)
        assertTrue(estado.cumpleMayusculaPassword)
        assertTrue(estado.cumpleSimboloPassword)

    }

    @Test
    fun onNuevaClave_cumpleLargoPassword_cumplePalabraMayuscula_cumpleSimbolos_esperadoNoCumple(){

        viewModel.onNuevaClave("12345")
        val estado = viewModel.uiState.value

        assertFalse(estado.cumpleLargoPassword)
        assertFalse (estado.cumpleSimboloPassword)
        assertFalse (estado.cumpleMayusculaPassword)
    }

    @Test
    fun onConfirmarCalve_debeSerIgualNuevaClave_SeEspera_Error(){
        rellenar()
        viewModel.onConfirmarClave("ManuelCR8.")
        viewModel.onGuardarClaveNueva(1L, {})
        val estado = viewModel.uiState.value

        assertEquals( "Las contraseñas no coinciden", estado.errorConfirmarClave)
    }

    @Test
    fun onClaveActual_probarVacia_esperado_mensajeError(){
        rellenar()

        viewModel.onClaveActual("")
        viewModel.onGuardarClaveNueva(1L, {})
        val estado = viewModel.uiState.value
        assertEquals(  "Debe ingresar su contraseña actual", estado.errorClaveActual)
    }
}