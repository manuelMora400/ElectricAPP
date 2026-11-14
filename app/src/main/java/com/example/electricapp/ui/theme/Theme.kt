package com.example.electricapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// configuramos todos los colores necesarios , ya que los que faltaba Material 3 lo hacian x nosotros
private val DarkColorScheme = darkColorScheme(
    primary = PrimarioNoche,
    onPrimary = PrimarioOscuro,
    primaryContainer = PrimarioOscuro,
    onPrimaryContainer = PrimarioClaro,
    secondary = SecundarioOscuro,
    onSecondary = TextoSobreSecundarioOscuro,
    secondaryContainer = VarianteOscura,
    onSecondaryContainer = TextoClaro,
    tertiary = Acento,
    onTertiary = Blanco,
    background = FondoOscuro,
    onBackground = TextoClaro,
    surface = SuperficieOscura,
    onSurface = TextoClaro,
    surfaceVariant = VarianteOscura,
    onSurfaceVariant = TextoSecundarioOscuro,
    outline = BordeCampoClaro,
    outlineVariant = BordeOscuro,
    error = ErrorOscuro,
    onError = TextoSobreErrorOscuro,
    errorContainer = ErrorFondoOscuro,
    onErrorContainer = ErrorFondoClaro,
    // para menu desplegables
    surfaceContainerLowest = FondoOscuro,
    surfaceContainerLow = SuperficieOscura,
    surfaceContainer = SuperficieOscura,
    surfaceContainerHigh = VarianteOscura,
    surfaceContainerHighest = VarianteOscura
)

private val LightColorScheme = lightColorScheme(
    primary = Primario,
    onPrimary = Blanco,
    primaryContainer = PrimarioClaro,
    onPrimaryContainer = PrimarioOscuro,
    secondary = SecundarioClaro,
    onSecondary = Blanco,
    secondaryContainer = VarianteClara,
    onSecondaryContainer = TextoOscuro,
    tertiary = Acento,
    onTertiary = Blanco,
    background = FondoClaro,
    onBackground = TextoOscuro,
    surface = SuperficieClara,
    onSurface = TextoOscuro,
    surfaceVariant = VarianteClara,
    onSurfaceVariant = TextoSecundarioClaro,
    outline = BordeCampoClaro,
    outlineVariant = BordeClaro,
    error = ErrorClaro,
    onError = Blanco,
    errorContainer = ErrorFondoClaro,
    onErrorContainer = ErrorClaro,
    surfaceContainerLowest = SuperficieClara,
    surfaceContainerLow = SuperficieClara,
    surfaceContainer = SuperficieClara,
    surfaceContainerHigh = FondoClaro,
    surfaceContainerHighest = VarianteClara
)

@Composable
fun ElectricAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
