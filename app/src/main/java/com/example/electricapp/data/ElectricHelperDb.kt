package com.example.electricapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.electricapp.model.Estado
import com.example.electricapp.model.FormularioInstrumentosStates
import com.example.electricapp.model.FormularioLoginState
import com.example.electricapp.model.FormularioProtocolosState
import com.example.electricapp.model.FormularioRegistros
import com.example.electricapp.model.HistorialState
import com.example.electricapp.model.Rol

class ElectricHelperDb(context : Context) :
    SQLiteOpenHelper(context, NombreBD, null, VERSION_BD){

    companion object{
        private const val NombreBD = "electric.bd"
        private const val VERSION_BD = 2

        private const val TABLA_ROL  = "ROLES"
        private const val ID_ROL     = "id_rol"
        private const val NOMBRE_ROL = "nombre_rol"

        private const val TABLA_USUARIO  = "USUARIOS"
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

        private const val TABLA_INSTRUMENTOS = "INSTRUMENTOS"
        private const val ID_INSTRUMENTO     = "id_instrumento"
        private const val NOMBRE_INSTRUMENTO = "nombre_instrumento"
        private const val MARCA              = "marca"
        private const val MODELO             = "modelo"
        private const val NUM_SERIE          = "numero_serie"

        private const val TABLA_CALIBRACION = "CALIBRACION"
        private const val ID_CALIBRACION    = "id_calibracion"
        private const val INSTRUMENTO_ID    = "id_instrumento"
        private const val FECHA_CALIBRACION = "fecha_calibracion"
        private const val FECHA_VENCIMIENTO = "fecha_vencimiento"

        private const val TABLA_PROTOCOLO = "PROTOCOLO"
        private const val ID_PROTOCOLO = "id_protocolo"
        private const val FECHA_HORA_PRUEBA = "fecha_prueba"
        private const val TEMPERATURA = "temperatura"
        private const val HUMEDAD = "humedad"
        private const val USU_ID = "id_usuario"
        private const val RESULTADO = "resultado"
        private const val ESTADO_PROTOCOLO = "estado"
        private const val INSTRU_ID = "id_instrumento"

        private const val TABLA_AISLACION = "AISLACION"
        private const val ID_PRUEBA = "id_prueba"
        private const val EQUIPO_TAG = "tag"
        private const val INSTALACION = "instalacion"
        private const val DEVANADO = "devanado"
        private const val TENSION_PRUEBA= "tension"
        private const val LECTURA_1MIN= "min_1"
        private const val LECTURA_10MIN= "min_10"
        private const val PROTOCOLO_ID= "id_protocolo"


        private const val SQL_CREAR_ROLES =
            "CREATE TABLE $TABLA_ROL ( " +
                    "$ID_ROL INTEGER PRIMARY KEY, " +
                    "$NOMBRE_ROL TEXT NOT NULL UNIQUE )"

        private const val SQL_CREAR_USUARIO =
            "CREATE TABLE $TABLA_USUARIO (" +
                    "$ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NOMBRE_USU TEXT NOT NULL, " +
                    "$CORREO TEXT NOT NULL UNIQUE," +
                    "$RUT TEXT NOT NULL UNIQUE," +
                    "$TELEFONO TEXT NOT NULL," +
                    "$PASSWORD TEXT NOT NULL," +
                    "$GIRO TEXT, " +
                    "$ACTIVO INTEGER DEFAULT 1," +
                    "$CONTACTO_CARGO TEXT," +
                    "$USER_ROL INTEGER NOT NULL REFERENCES $TABLA_ROL($ID_ROL))"

        private const val SQL_CREAR_INSTRUMENTOS =
            "CREATE TABLE $TABLA_INSTRUMENTOS (" +
                    "$ID_INSTRUMENTO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NOMBRE_INSTRUMENTO TEXT NOT NULL," +
                    "$MARCA TEXT NOT NULL," +
                    "$MODELO TEXT NOT NULL," +
                    "$NUM_SERIE TEXT NOT NULL UNIQUE)"

        private const val SQL_CREAR_CALIBRACION =
            "CREATE TABLE $TABLA_CALIBRACION (" +
                    "$ID_CALIBRACION INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$FECHA_CALIBRACION TEXT NOT NULL," +
                    "$FECHA_VENCIMIENTO TEXT NOT NULL," +
                    "$INSTRUMENTO_ID INTEGER NOT NULL REFERENCES $TABLA_INSTRUMENTOS($ID_INSTRUMENTO))"

        private const val SQL_CREAR_PROTOCOLO =
            "CREATE TABLE $TABLA_PROTOCOLO (" +
                    "$ID_PROTOCOLO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$FECHA_HORA_PRUEBA TEXT NOT NULL," +
                    "$TEMPERATURA TEXT NOT NULL," +
                    "$HUMEDAD TEXT NOT NULL," +
                    "$INSTRU_ID INTEGER NOT NULL REFERENCES $TABLA_INSTRUMENTOS($ID_INSTRUMENTO)," +
                    "$RESULTADO TEXT NOT NULL," +
                    "$ESTADO_PROTOCOLO TEXT NOT NULL DEFAULT 'REGISTRADO_TERRENO'," +
                    "$USU_ID INTEGER NOT NULL REFERENCES $TABLA_USUARIO($ID_USUARIO))"

        private const val SQL_CREAR_AISLACION =
            "CREATE TABLE $TABLA_AISLACION (" +
                    "$ID_PRUEBA INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "$EQUIPO_TAG TEXT NOT NULL," +
                    "$INSTALACION TEXT NOT NULL," +
                    "$DEVANADO TEXT NOT NULL," +
                    "$TENSION_PRUEBA TEXT NOT NULL, " +
                    "$LECTURA_1MIN TEXT NOT NULL," +
                    "$LECTURA_10MIN TEXT NOT NULL," +
                    "$PROTOCOLO_ID INTEGER NOT NULL REFERENCES $TABLA_PROTOCOLO($ID_PROTOCOLO))"

        private const val SQL_INSERTAR_ROLES =
            "INSERT INTO $TABLA_ROL ($ID_ROL , $NOMBRE_ROL) VALUES " +
                    "(1, 'JEFE_TECNICO'), (2, 'INGENIERO'), (3, 'TECNICO'), (4, 'CLIENTE')"

        private const val SQL_INSERTAR_USUARIO =
            "INSERT INTO $TABLA_USUARIO "+
                    "($NOMBRE_USU, $CORREO, $RUT, $TELEFONO, $PASSWORD, $GIRO ,$ACTIVO, $CONTACTO_CARGO,$USER_ROL) VALUES " +
                    "('Ricardo Espinoza Lagos','respinoza@induztek.cl','13.871.905-7','987541666','Lolsitox-.','Giro',1,'Cargo',1)," +
                    "('Andrea Contreras Silva','acontreras@induztek.cl','15.906.334-8','987541666','Cololo.-892','Giro',1,'Cargo',2)," +
                    "('Valentina Soto','vsoto@induztek.cl','18.871.905-7','987541666','Ude.-oloDE','giro',1,'Cargo',3)," +
                    "('Marcela Fuentes Rojas','mfuentes@envpacifico.ejemplo','14.226.791-8','+56900000101','Cliente.-01','Agroindustria',1,'Jefa de Mantenimiento',4)," +
                    "('Tomas Leiva Araya','tleiva@losaromos.ejemplo','18.390.452-3','+56900000104','Cliente.-02','Salud',1,'Supervisor Electrico',4)," +
                    "('Patricio Navarro Soto','pnavarro@cerroalto.ejemplo','12.645.308-6','+56900000103','Cliente.-03','Mineria',1,'Jefe de Mantenimiento Electrico',4)"


        private const val SQL_INSERTAR_INSTRUMENTO =
            "INSERT INTO $TABLA_INSTRUMENTOS "+
                    "($NOMBRE_INSTRUMENTO, $MARCA, $MODELO, $NUM_SERIE) VALUES "+
                    "('Megóhmetro 5 kV','Megger','MIT525','MIT-0045821'),"+
                    "('Megóhmetro 10 kV','Megger','MIT1025','MIT-0071233'),"+
                    "('Medidor de relación (TTR)','Megger','TTR300','TTR-30044'),"+
                    "('Micro-ohmímetro','Megger','DLRO10X','DLR-10882'),"+
                    "('Equipo de inyección secundaria','Omicron','CMC 356','CMC-HB411')"

        private const val SQL_INSERTAR_CALIBRACION =
            "INSERT INTO $TABLA_CALIBRACION "+
                    "($FECHA_CALIBRACION, $FECHA_VENCIMIENTO, $INSTRUMENTO_ID) VALUES "+
                    "('15/03/2026','15/03/2027',1),"+
                    "('10/10/2025','10/10/2026',2),"+
                    "('02/06/2026','02/06/2027',3),"+
                    "('20/08/2025','20/08/2026',4),"+
                    "('05/01/2026','05/01/2027',5)"


        private const val SQL_INSERTAR_PROTOCOLO =
            "INSERT INTO $TABLA_PROTOCOLO "+
                    "($FECHA_HORA_PRUEBA, $TEMPERATURA, $HUMEDAD, $INSTRU_ID, $RESULTADO, $ESTADO_PROTOCOLO, $USU_ID) VALUES "+
                    "('14/09/2024 10:00','18','55',1,'APROBADO','FORMALIZADO',3),"+
                    "('13/09/2025 10:00','21','48',1,'APROBADO','FORMALIZADO',3),"+
                    "('26/09/2026 09:30','19','60',1,'OBSERVADO','REGISTRADO_TERRENO',3),"+
                    "('26/09/2026 11:00','19','60',1,'APROBADO','REGISTRADO_TERRENO',3)"

        private const val SQL_INSERTAR_AISLACION =
            "INSERT INTO $TABLA_AISLACION " +
                    "($EQUIPO_TAG, $INSTALACION, $DEVANADO, $TENSION_PRUEBA, $LECTURA_1MIN, $LECTURA_10MIN, $PROTOCOLO_ID) VALUES " +
                    "('TR-01','Planta Lampa','AT-T','5','12500','31250', 1),"+
                    "('TR-01','Planta Lampa','AT-T','5','9800','21560', 2),"+
                    "('TR-01','Planta Lampa','AT-T','5','6100','10980', 3),"+
                    "('TR-02','Planta Lampa','AT-T','5','15200','36480', 4)"
        
        private const val SQL_SELECT_USUARIOS =
            "SELECT u.*, r.$NOMBRE_ROL AS $NOMBRE_ROL "+
                    "FROM $TABLA_USUARIO u "+
                    "INNER JOIN $TABLA_ROL r ON u.$USER_ROL = r.$ID_ROL"
    }

    override fun onConfigure(db: SQLiteDatabase){
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate (db: SQLiteDatabase){

        db.execSQL(SQL_CREAR_ROLES)
        db.execSQL(SQL_CREAR_USUARIO)
        db.execSQL(SQL_CREAR_INSTRUMENTOS)
        db.execSQL(SQL_CREAR_CALIBRACION)
        db.execSQL(SQL_CREAR_PROTOCOLO)
        db.execSQL(SQL_CREAR_AISLACION)
        db.execSQL(SQL_INSERTAR_ROLES)
        db.execSQL(SQL_INSERTAR_USUARIO)
        db.execSQL(SQL_INSERTAR_INSTRUMENTO)
        db.execSQL(SQL_INSERTAR_CALIBRACION)
        db.execSQL(SQL_INSERTAR_PROTOCOLO)
        db.execSQL(SQL_INSERTAR_AISLACION)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_AISLACION")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PROTOCOLO")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_CALIBRACION")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_INSTRUMENTOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_USUARIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_ROL")
        onCreate(db)
    }

    fun listaRoles(): List<Rol> {
        val roles = mutableListOf<Rol>()
        readableDatabase.rawQuery(
            "SELECT $ID_ROL, $NOMBRE_ROL FROM $TABLA_ROL ORDER BY $ID_ROL", null
        ).use { cursor ->
            val nombreIndexeado = cursor.getColumnIndexOrThrow(NOMBRE_ROL)
            while (cursor.moveToNext()){
                val rolNombre = cursor.getString(nombreIndexeado)
                roles.add(
                    Rol.valueOf(rolNombre)
                )
            }
        }
        return roles
    }
    private fun valoresUsuarios(usuario : FormularioRegistros) = contentValuesOf().apply{
        put(NOMBRE_USU , usuario.nombre)
        put(CORREO, usuario.correo)
        put(RUT, usuario.rut)
        put(TELEFONO, usuario.telefono)
        put(PASSWORD, usuario.password)
        put(GIRO , usuario.giro)
        put(ACTIVO, 1)
        put(USER_ROL, usuario.rol.ordinal + 1)
        put(CONTACTO_CARGO,usuario.contactoCargo)
    }

    fun insertarUsuario(usuario : FormularioRegistros):Long =
        writableDatabase.insert(TABLA_USUARIO, null, valoresUsuarios(usuario))

    private fun valoresInstrumentos(instrumento : FormularioInstrumentosStates) = contentValuesOf().apply {
        put(NOMBRE_INSTRUMENTO, instrumento.nombre)
        put(MARCA , instrumento.marca)
        put(MODELO, instrumento.modelo)
        put(NUM_SERIE, instrumento.numSerie)
    }

    fun insertarInstrumentos(instrumentos : FormularioInstrumentosStates) : Long =
        writableDatabase.insert(TABLA_INSTRUMENTOS, null, valoresInstrumentos(instrumentos))


    private fun valoresCalibraciones(calibracion : FormularioInstrumentosStates, idInstrumentos : Int) = contentValuesOf().apply {
        put(FECHA_CALIBRACION, calibracion.fechaCalibracion)
        put(FECHA_VENCIMIENTO, calibracion.fechaVencimiento)
        put(ID_INSTRUMENTO, idInstrumentos)
    }

    fun insertarCalibraciones(calibracion: FormularioInstrumentosStates,idInstrumentos : Int) : Long =
        writableDatabase.insert(TABLA_CALIBRACION, null, valoresCalibraciones(calibracion, idInstrumentos))


    fun buscarInstrumentoPorSerie(numSerie : String) : Int?{
        readableDatabase.rawQuery(
            "SELECT $ID_INSTRUMENTO FROM $TABLA_INSTRUMENTOS WHERE $NUM_SERIE = ?",
            arrayOf(numSerie)
        ).use { cursor ->
            if(cursor.moveToFirst()){
                return cursor.getInt(cursor.getColumnIndexOrThrow(ID_INSTRUMENTO))
            }
        }
        return null
    }

    fun listaInstrumentos() : List<FormularioInstrumentosStates>{

        val instrumentos = mutableListOf<FormularioInstrumentosStates>()
        val consultaSql =
            "SELECT i.$ID_INSTRUMENTO, i.$NOMBRE_INSTRUMENTO, i.$MARCA, i.$MODELO, i.$NUM_SERIE, " +
                    "c.$FECHA_CALIBRACION, c.$FECHA_VENCIMIENTO " +
                    "FROM $TABLA_INSTRUMENTOS i " +
                    "INNER JOIN $TABLA_CALIBRACION c ON c.$INSTRUMENTO_ID = i.$ID_INSTRUMENTO " +
                    "WHERE c.$ID_CALIBRACION = (SELECT MAX($ID_CALIBRACION) FROM $TABLA_CALIBRACION " +
                    "WHERE $INSTRUMENTO_ID = i.$ID_INSTRUMENTO) " +
                    "ORDER BY i.$NOMBRE_INSTRUMENTO"

        readableDatabase.rawQuery(consultaSql, null).use { cursor ->
            while (cursor.moveToNext()){
                instrumentos.add(
                    FormularioInstrumentosStates(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_INSTRUMENTO)),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_INSTRUMENTO)),
                        marca = cursor.getString(cursor.getColumnIndexOrThrow(MARCA)),
                        modelo = cursor.getString(cursor.getColumnIndexOrThrow(MODELO)),
                        numSerie = cursor.getString(cursor.getColumnIndexOrThrow(NUM_SERIE)),
                        fechaCalibracion = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_CALIBRACION)),
                        fechaVencimiento = cursor.getString(cursor.getColumnIndexOrThrow(FECHA_VENCIMIENTO))
                    )
                )
            }
        }
        return instrumentos
    }

    private fun valoresProtocolos(protocolos : FormularioProtocolosState,idUsuario : Int,idInstrumento : Int) = contentValuesOf().apply {
        put(FECHA_HORA_PRUEBA, protocolos.fechaHora)
        put(TEMPERATURA, protocolos.temperatura)
        put(HUMEDAD, protocolos.humedad)
        put(RESULTADO, protocolos.resultado?.name ?: "RECHAZADO")
        put(ESTADO_PROTOCOLO, "REGISTRADO_TERRENO")
        put(USU_ID, idUsuario)
        put(INSTRU_ID, idInstrumento)
    }

    fun insertarProtocolos(protocolos: FormularioProtocolosState, idUsuario : Int,idInstrumento : Int) : Long =
        writableDatabase.insert(TABLA_PROTOCOLO, null, valoresProtocolos(protocolos, idUsuario, idInstrumento))

    private fun valoresAislacion(protocolos : FormularioProtocolosState, tag : String, instalacion : String, idProtocolo : Int) = contentValuesOf().apply {
        put(EQUIPO_TAG, tag)
        put(INSTALACION, instalacion)
        put(DEVANADO, protocolos.devanado)
        put(TENSION_PRUEBA, protocolos.tensionPrueba)
        put(LECTURA_1MIN, protocolos.lecturaUnMinuto)
        put(LECTURA_10MIN, protocolos.lecturaDiezMinutos)
        put(PROTOCOLO_ID, idProtocolo)
    }

    fun insertarAislacion(protocolos : FormularioProtocolosState, tag : String, instalacion : String, idProtocolo : Int) : Long =
        writableDatabase.insert(TABLA_AISLACION, null, valoresAislacion(protocolos, tag, instalacion, idProtocolo))

    fun buscarCuentasLogin(correo : String, password : String) : FormularioLoginState? {

        val consultaSql =
            "SELECT u.*, r.$NOMBRE_ROL " +
                    "FROM $TABLA_USUARIO u " +
                    "INNER JOIN $TABLA_ROL r ON u.$USER_ROL = r.$ID_ROL " +
                    "WHERE u.$CORREO = ? AND u.$PASSWORD = ? AND u.$ACTIVO = 1"
           readableDatabase.rawQuery(consultaSql, arrayOf(correo, password)).use {  cursor ->

                if(cursor.moveToFirst()){
                    val rol = cursor.getColumnIndexOrThrow(NOMBRE_ROL)
                    val rolStr = cursor.getString(rol)
                    return FormularioLoginState(
                        idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow(ID_USUARIO)),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_USU)),
                        correo = correo,
                        passwordHash = password,
                        rol = Rol.valueOf(rolStr)
                    )
                }else null
           }
        return null
    }

    fun cambiarEstadoUsuario(correo: String, nuevoEstado: Boolean): Boolean {
        val valores = contentValuesOf().apply {
            put(ACTIVO, if (nuevoEstado) 1 else 0)
        }

        val filasAfectadas = writableDatabase.update(
            TABLA_USUARIO,
            valores,
            "$CORREO = ?",
            arrayOf(correo)
        )

        return filasAfectadas > 0
    }


    fun filtrarCuentasCreadas() : List<FormularioRegistros>{

        val cuentas = mutableListOf<FormularioRegistros>()
        readableDatabase.rawQuery(
            "SELECT $NOMBRE_USU, $CORREO, $ACTIVO " +
            " FROM $TABLA_USUARIO", null
        ).use { cursor ->

            while (cursor.moveToNext()){
                val estadoIndex = cursor.getColumnIndexOrThrow(ACTIVO)
                cuentas.add(
                    FormularioRegistros(
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_USU)),
                        correo = cursor.getString(cursor.getColumnIndexOrThrow(CORREO)),
                        activo = cursor.getInt(estadoIndex) == 1
                    )
                )
            }
        }
        return cuentas
    }


    fun filtrarProtocolo(tag : String, instalacion : String): List<HistorialState> {
        val protocolo = mutableListOf<HistorialState>()
        val consultaSQL = """
        SELECT a.$EQUIPO_TAG, a.$INSTALACION, p.$FECHA_HORA_PRUEBA, p.$RESULTADO, p.$ESTADO_PROTOCOLO
        FROM $TABLA_AISLACION a
        INNER JOIN $TABLA_PROTOCOLO p ON a.$PROTOCOLO_ID = p.$ID_PROTOCOLO
        WHERE a.$EQUIPO_TAG LIKE ? AND a.$INSTALACION LIKE ?
        ORDER BY p.$ID_PROTOCOLO DESC
    """.trimIndent()


        readableDatabase.rawQuery(consultaSQL, arrayOf(tag, "%$instalacion%")).use { cursor ->
            val idxTag = cursor.getColumnIndexOrThrow(EQUIPO_TAG)
            val idxInstalacion = cursor.getColumnIndexOrThrow(INSTALACION)
            val idxFecha = cursor.getColumnIndexOrThrow(FECHA_HORA_PRUEBA)
            val idxResultado = cursor.getColumnIndexOrThrow(RESULTADO)
            val idxEstado = cursor.getColumnIndexOrThrow(ESTADO_PROTOCOLO)

            while (cursor.moveToNext()) {
                val resultadoStr = cursor.getString(idxResultado)
                val estadoEnum = when (resultadoStr) {
                    "APROBADO" -> Estado.APROBADO
                    "OBSERVADO" -> Estado.OBSERVACION
                    else -> Estado.RECHAZADO
                }

                protocolo.add(
                    HistorialState(
                        tagBuscar = cursor.getString(idxTag),
                        instalacion = cursor.getString(idxInstalacion),
                        fechaPrueba = cursor.getString(idxFecha),
                        estado = estadoEnum,
                        formalizado = cursor.getString(idxEstado) == "FORMALIZADO"
                    )
                )
            }
        }
        return protocolo
    }
}
