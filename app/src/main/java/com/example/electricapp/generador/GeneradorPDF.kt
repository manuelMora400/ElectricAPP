package com.example.electricapp.generador

import android.content.ContentValues
import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.ui.graphics.toArgb
import com.example.electricapp.model.DetallesState
import com.example.electricapp.ui.theme.Blanco
import com.example.electricapp.ui.theme.CuadroAprobado
import com.example.electricapp.ui.theme.CuadroObservacion
import com.example.electricapp.ui.theme.CuadroRechazado
import com.example.electricapp.ui.theme.TextoOscuro
import java.io.File
import java.io.FileOutputStream

object GeneradorPdfProtocolo {

    fun generarYDescargarPdf(
        context: Context,
        detalle: DetallesState,
        indicePolarizacion: Double
    ) {
        val pdf = PdfDocument()
        val page = pdf.startPage(PdfDocument.PageInfo.Builder(595, 842, 1).create())
        val canvas = page.canvas
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val margen = 40f

        //_________TopBar con el nombre del Protocolo__________
        paint.color = android.graphics.Color.parseColor("#283593")
        canvas.drawRect(0f, 0f, 595f, 80f, paint)

        paint.color = Blanco.toArgb()
        paint.textSize = 20f
        paint.typeface = Typeface.DEFAULT_BOLD
        canvas.drawText("Detalle · ${detalle.Tag}", margen, 50f, paint)

        //Para dar separacion (Me puse tonoto)
        var y = 130f

        //____________ Estado del Protocolo ________________
        val colorEstado = when {
            detalle.resultado.equals("APROBADO", true) -> CuadroAprobado.toArgb()
            detalle.resultado.equals("OBSERVADO", true) -> CuadroObservacion.toArgb()
            else -> CuadroRechazado.toArgb()
        }
        val colorTexto = if (detalle.resultado.equals("RECHAZADO", true)) {
            Blanco.toArgb()
        } else {
            TextoOscuro.toArgb()
        }

        // 3.- Banner de Resultado (DetalleScreen)
        paint.color = colorEstado
        canvas.drawRoundRect(margen, y, 555f, y + 65f, 16f, 16f, paint)

        paint.color = colorTexto
        paint.textSize = 20f
        paint.typeface = Typeface.DEFAULT_BOLD
        canvas.drawText(
            "IP ${"%.2f".format(indicePolarizacion)} · ${detalle.resultado}",
            margen + 20f,
            y + 40f,
            paint
        )

        // Avanzamos 'y' para que las tarjetas se dibujen DEBAJO del banner y no encima
        y += 95f

        // 4.- Tarjetas de datos (De Validacion / Nombre de la que se encargo)
        val ingeniero = detalle.nombreIngeniero?.ifBlank { "Pendiente de validación" }
            ?: "Pendiente de validación"

        val tarjetas = listOf(
            "${detalle.Tag} · ${detalle.Instalacion}" to "Prueba del ${detalle.fechaPrueba} (${detalle.estado})",
            "Medición ${detalle.devanado} · ${detalle.tensionPrueba} kV" to "1 min ${detalle.medicion1Min} MΩ · 10 min ${detalle.medicion10Min} MΩ",
            "Formalizado por" to ingeniero


        )

        //_____ Diseno y Arquitectura del PDF _____
        tarjetas.forEach { (titulo, subtitulo) ->
            // Para el fondo y ek borde
            paint.style = Paint.Style.FILL
            paint.color = android.graphics.Color.parseColor("#F5F7FA")
            canvas.drawRoundRect(margen, y, 555f, y + 75f, 16f, 16f, paint)

            // Titulo y su descripcion
            paint.color = TextoOscuro.toArgb()
            paint.textSize = 15f
            paint.typeface = Typeface.DEFAULT_BOLD
            canvas.drawText(titulo, margen + 20f, y + 32f, paint)

            paint.textSize = 13f
            paint.typeface = Typeface.DEFAULT
            canvas.drawText(subtitulo, margen + 20f, y + 54f, paint)

            y += 95f
        }

        pdf.finishPage(page)
        guardarArchivo(context, pdf, "Protocolo_${detalle.Tag}.pdf")
        pdf.close()
    }

    //Para armar la ruta de guardado
    private fun guardarArchivo(context: Context, pdf: PdfDocument, nombre: String) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, nombre)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)?.let { uri ->
                    context.contentResolver.openOutputStream(uri)?.use { pdf.writeTo(it) }
                }
            } else {
                val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                FileOutputStream(File(dir, nombre)).use { pdf.writeTo(it) }
            }
            Toast.makeText(context, "PDF guardado en Descargas", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}