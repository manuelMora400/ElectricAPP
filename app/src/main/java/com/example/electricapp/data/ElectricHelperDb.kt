package com.example.electricapp.data

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper

class ElectricHelperDb(context : Context) :
    SQLiteOpenHelper(context, NombreBD, null, VERSION_BD){

    companion object{
        private const val NombreBD = "electric.bd"
        private const val VERSION_BD = 1

        private const val TABLA_ROL  = "ROLES" // TABLA ROLES
        private const val ID_ROL     = "id_rol"
        private const val NOMBRE_ROL = "nombre_rol"

        private const val TABLA_USUARIO  = "USUARIOS" // TABLA USUARIOS
        private const val ID_USUARIO     = "id_usuario"
        private const val NOMBRE_USU     = "nombre"
        private const val CORREO         = "correo"
        private const val RUT            = "rut"
        private const val TELEFONO       = "telefono"
        private const val PASSWORD       = "password"
        private const val GIRO           = "giro"
        private const val ACTIVO         = "activo"
        private const val CONTACTO_CARGO = "cargo"
        private const val USER_ROL       = "id_rol"

        private const val TABLA_INSTRUMENTOS = "INSTRUMENTOS" // TABLA INSTRUMENTO
        private const val ID_INSTRUMENTO     = "id_instrumento"
        private const val NOMBRE_INSTRUMENTO = "nombre_instrumento"
        private const val MARCA              = "marca"
        private const val MODELO             = "modelo"
        private const val NUM_SERIE          = "numero_serie"

        private const val TABLA_CALIBRACION = "CALIBRACION" // TABLA CALIBRACION
        private const val ID_CALIBRACION    = "id_calibracion"
        private const val INSTRUMENTO_ID    = "id_instrumento"
        private const val FECHA_CALIBRACION = "fecha_calibracion"
        private const val FECHA_VENCIMIENTO = "fecha_vencimiento"


        private const val SQL_CREAR_ROLES =
            "CREATE TABLE $TABLA_ROL ( " +
                    "$ID_ROL INTEGER PRIMARY KEY AUTOINCREMENT, " + "$NOMBRE_ROL TEXT NOT NULL UNIQUE )"

        private const val SQL_CREAR_USUARIO =
            "CREATE TABLE $TABLA_USUARIO (" +
                    "$ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, " + " $NOMBRE_USU TEXT NOT NULL " +
                    "$CORREO TEXT NOT NULL UNIQUE" + "$RUT TEXT NOT NULL UNIQUE" + "$TELEFONO TEXT NOT NULL" +
                    "$PASSWORD TEXT NOT NULL" + "$GIRO TEXT " + "$ACTIVO INTEGER DEFAULT 0" + "$CONTACTO_CARGO TEXT" +
                    "$USER_ROL INTEGER NOT NULL REFERENCES $TABLA_ROL($ID_ROL)"
    }
}