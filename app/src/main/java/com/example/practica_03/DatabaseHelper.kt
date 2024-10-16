package com.example.practica_03

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "mascotas.db"
const val DATABASE_VERSION = 1

// Definimos el nombre de la tabla y las columnas
const val TABLE_NAME = "mascotas"
const val COL_ID = "id"
const val COL_NOMBRE = "nombre"
const val COL_EDAD = "edad"
const val COL_TIPO = "tipo"
const val COL_DUENO = "dueno"
const val COL_VACUNADO = "vacunado"
const val COL_PESO = "peso"
// DatabaseHelper.kt
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear la tabla
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE TEXT,
                $COL_EDAD INTEGER,
                $COL_TIPO TEXT,
                $COL_DUENO TEXT,
                $COL_VACUNADO INTEGER,
                $COL_PESO REAL
            )
        """
        db.execSQL(createTableQuery)

        // Insertar datos iniciales
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insertar los registros iniciales
    private fun insertInitialData(db: SQLiteDatabase) {
        val mascotas = listOf(
            Mascota("Firulais", 3, "Perro", "Juan", true, 12.5f),
            Mascota("Pelusa", 2, "Gato", "Ana", false, 4.3f),
            Mascota("Max", 1, "Conejo", "Luis", true, 1.8f),
            Mascota("Rex", 5, "Perro", "Maria", true, 20.7f),
            Mascota("Lola", 4, "Gato", "Carlos", false, 3.9f)
        )

        for (mascota in mascotas) {
            val values = ContentValues().apply {
                put(COL_NOMBRE, mascota.nombre)
                put(COL_EDAD, mascota.edad)
                put(COL_TIPO, mascota.tipo)
                put(COL_DUENO, mascota.dueno)
                put(COL_VACUNADO, if (mascota.vacunado) 1 else 0)
                put(COL_PESO, mascota.peso)
            }
            db.insert(TABLE_NAME, null, values)
        }
    }

    // Función para insertar una nueva mascota
    fun insertarMascota(mascota: Mascota) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_NOMBRE, mascota.nombre)
            put(COL_EDAD, mascota.edad)
            put(COL_TIPO, mascota.tipo)
            put(COL_DUENO, mascota.dueno)
            put(COL_VACUNADO, if (mascota.vacunado) 1 else 0)
            put(COL_PESO, mascota.peso)
        }
        db.insert(TABLE_NAME, null, values)
    }

    // Función para obtener todas las mascotas
    fun getAllMascotas(): List<Mascota> {
        val mascotaList = mutableListOf<Mascota>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE))
                val edad = cursor.getInt(cursor.getColumnIndexOrThrow(COL_EDAD))
                val tipo = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO))
                val dueno = cursor.getString(cursor.getColumnIndexOrThrow(COL_DUENO))
                val vacunado = cursor.getInt(cursor.getColumnIndexOrThrow(COL_VACUNADO)) == 1
                val peso = cursor.getFloat(cursor.getColumnIndexOrThrow(COL_PESO))

                mascotaList.add(Mascota(nombre, edad, tipo, dueno, vacunado, peso))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return mascotaList
    }
}