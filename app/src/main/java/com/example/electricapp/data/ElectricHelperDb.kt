package com.example.electricapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.selects.select

class ElectricHelperDb(context : Context) :
    SQLiteOpenHelper(context, NOMBRE_BD, null, VERSION_BD){

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

        private const val TABLA_PROTOCOLO = "PROTOCOLO" //TABLA PROTOCOLO
        private const val ID_PROTOCOLO = "id_protocolo"
        private const val FECHA_HORA_PRUEBA = "fecha_prueba"
        private const val TEMPERATURA = "temperatura"
        private const val HUMEDAD = "humedad"
        private const val USU_ID = "id_usuario"
        private const val RESULTADO = "resultado"
        private const val INSTRU_ID = "id_instrumento"

        private const val TABLA_AISLACION = "AISLACION" //TABLA AISLACION
        private const val ID_PRUEBA = "id_prueba"
        private const val EQUIPO_TAG = "tag"
        private const val DEVANADO = "devanado"
        private const val TENSION_PRUEBA= "tension"
        private const val LECTURA_1MIN= "1_min"
        private const val LECTURA_10MIN= "10_min"
        private const val PROTOCOLO_ID= "id_protocolo"


        private const val SQL_CREAR_ROLES =
            "CREATE TABLE $TABLA_ROL ( " +
                    "$ID_ROL INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NOMBRE_ROL TEXT NOT NULL UNIQUE )"

        private const val SQL_CREAR_USUARIO =
            "CREATE TABLE $TABLA_USUARIO (" +
                    "$ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NOMBRE_USU TEXT NOT NULL " +
                    "$CORREO TEXT NOT NULL UNIQUE" +
                    "$RUT TEXT NOT NULL UNIQUE" +
                    "$TELEFONO TEXT NOT NULL" +
                    "$PASSWORD TEXT NOT NULL" +
                    "$GIRO TEXT " +
                    "$ACTIVO INTEGER DEFAULT 0" +
                    "$CONTACTO_CARGO TEXT" +
                    "$USER_ROL INTEGER NOT NULL REFERENCES $TABLA_ROL($ID_ROL))"

        private const val SQL_CREAR_INSTRUMENTOS =
            "CREATE TABLE $TABLA_INSTRUMENTOS (" +
                    "$ID_INSTRUMENTO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NOMBRE_INSTRUMENTO TEXT NOT NULL" +
                    "$MARCA TEXT NOT NULL" +
                    "$MODELO TEXT NOT NULL" +
                    "$NUM_SERIE TEXT NOT NULL UNIQUE)"

        private const val SQL_CREAR_CALIBRACION =
            "CREATE TABLE $TABLA_CALIBRACION (" +
                    "$ID_CALIBRACION INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$FECHA_CALIBRACION TEXT NOT NULL" +
                    "$FECHA_VENCIMIENTO TEXT NOT NULL" +
                    "$INSTRUMENTO_ID INTEGER NOT NULL REFERENCES $TABLA_INSTRUMENTOS($ID_INSTRUMENTO))"

        private const val SQL_CREAR_PROTOCOLO =
            "CREATE TABLE $TABLA_PROTOCOLO (" +
                    "$ID_PROTOCOLO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$FECHA_HORA_PRUEBA TEXT NOT NULL" +
                    "$TEMPERATURA TEXT NOT NULL" +
                    "$HUMEDAD TEXT NOT NULL" +
                    "$INSTRU_ID INTEGER NOT NULL REFERENCES $TABLA_INSTRUMENTOS($ID_INSTRUMENTO)" +
                    "$RESULTADO TEXT NOT NULL" +
                    "$USU_ID INTEGER NOT NULL REFERENCES $TABLA_USUARIO($ID_USUARIO))"

        private const val SQL_CREAR_AISLACION =
            "CREAR TABLE $TABLA_AISLACION (" +
                    "$ID_PRUEBA INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "$EQUIPO_TAG TEXT NOT NULL" +
                    "$DEVANADO TEXT NOT NULL" +
                    "$TENSION_PRUEBA TEXT NOT NULL" +
                    "$LECTURA_1MIN TEXT NOT NULL" +
                    "$LECTURA_10MIN TEXT NOT NULL" +
                    "$PROTOCOLO_ID INTEGER NOT NULL REFERENCES $TABLA_PROTOCOLO($ID_PROTOCOLO))"

        private const val SQL_INSERTAR_ROLES =
            "INSERT INTO $TABLA_ROL ($ID_ROL , $NOMBRE_ROL) VALUES " +
                    "(1, 'JEFE_TECNICO'), (2, 'INGENIERO'), (3, 'TECNICO'), (4, 'CLIENTE')"

        private const val SQL_INSERTAR_USUARIO =
            "INSERT INTO $TABLA_USUARIO "+
                    "($NOMBRE_USU, $CORREO, $RUT, $TELEFONO, $PASSWORD, $GIRO ,$ACTIVO, $CONTACTO_CARGO,$USER_ROL) VALUES" +
                    "('Ricardo Espinoza Lagos','respinoza@induztek.cl','13.871.905-7','987541666','Contra','Giro','1','Cargo',1)"+
                    "('Andrea Contreras Silva','acontreras@induztek.cl','15.906.334-8','987541666','','','1','',2)"+
                    "('Ricardo Espinoza Lagos','respinoza@induztek.cl','13.871.905-7','987541666','','','1','',2)"


        private const val SQL_INSERTAR_INSTRUMENTO =
            "INSERT INTO $TABLA_INSTRUMENTOS "+
                    "($NOMBRE_INSTRUMENTO, $MARCA, $MODELO, $NUM_SERIE) VALUES "+
                    "('Megóhmetro 5 kV','Megger','MIT525','MIT-0045821')"

        private const val SQL_INSERTAR_CALIBRACION =
            "INSERT INTO $TABLA_CALIBRACION "+
                    "($FECHA_CALIBRACION, $FECHA_VENCIMIENTO, $INSTRUMENTO_ID) VALUES "+
                    "('15-03-2026','15-03-2027',1)"+
                    "('10-10-2025','10-10-2026',2)"

        private const val SQL_INSERTAR_PROTOCOLO =
            "INSERT INTO $TABLA_PROTOCOLO "+
                    "($FECHA_HORA_PRUEBA, $TEMPERATURA, $HUMEDAD, $INSTRUMENTO_ID, $RESULTADO, $USU_ID) VALUES "+
                    "('','','',6,'',5)"

        private const val SQL_INSERTAR_AISLACION =
            "INSERT INTO $TABLA_AISLACION "+
                    "($EQUIPO_TAG, $DEVANADO, $TENSION_PRUEBA, $LECTURA_1MIN, $LECTURA_10MIN, $PROTOCOLO_ID) VALUES "+
                    "('','','','','','',3)"


        private const val SQL_SELECT_USUARIOS =
            "SELECT u.*, r.$NOMBRE_ROL AS $NOMBRE_ROL "+
                    "FROM $TABLA_USUARIO u "+
                    "INNER JOIN $TABLA_ROL r ON u.$USER_ROL = r.$ID_ROL"

        override fun onConfigure(db: SQLiteDatabase){
            super.onConfigure(db)
            db.setForeignKeyConstraintsEnabled(true)
        }
    }
}