# ElectricAPP

App Android para **Induztek**, una empresa de ingeniería eléctrica. Pasa a digital el trabajo en terreno que antes se hacía en papel: cuentas, equipos, instrumentos y protocolos de prueba.

Está hecha en Kotlin con Jetpack Compose, arquitectura MVVM y Retrofit. Se conecta a un backend de microservicios en Spring Boot.

Backend: https://github.com/manuelMora400/BackendApMovil

## Qué hace cada rol

| Rol | Qué puede hacer |
|---|---|
| Jefe técnico | Crear cuentas y enviar la clave temporal por SMS, activar o desactivar cuentas, registrar equipos, registrar instrumentos y renovar su calibración |
| Técnico | Crear protocolos de prueba de aislación (solo deja usar instrumentos con calibración vigente) y ver el resumen de sus protocolos |
| Ingeniero | Revisar y formalizar protocolos, y ver los instrumentos |
| Cliente | Ver el historial de protocolos y el detalle de cada uno |

## Cómo correrla

1. Levanta el backend siguiendo su README: Eureka, los microservicios y el API Gateway en el puerto 8081.
2. La app apunta al gateway con la constante `URL_GATEWAY` en `app/src/main/java/com/example/electricapp/data/remote/RetrofitClient.kt`. Si usas tu propio túnel, por ejemplo un Dev Tunnel de VS Code al puerto 8081, cambia esa URL.
3. Abre el proyecto en Android Studio y ejecútalo en un celular o emulador con Android 7.0 o superior.

**SMS:** la clave temporal solo llega por SMS si la app corre en un **celular físico con chip**. El emulador no puede mandar SMS a teléfonos reales.

## Usuarios de prueba

| Rol | Correo |
|---|---|
| Jefe técnico | respinoza@induztek.cl |
| Ingeniero | acontreras@induztek.cl |
| Técnico | vsoto@induztek.cl |
| Cliente | mfuentes@envpacifico.ejemplo |

Las claves de estos usuarios están en `ms_roles/src/main/java/com/backend/ms_roles/config/CargaDatosConfig.java`, en el repo del backend.

## Tests

```bash
./gradlew test
```

Son tests unitarios de ViewModels y repositorios, hechos con una API falsa.
