package com.example.practica_03

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

// MainActivity.kt
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instancia del DatabaseHelper
        val dbHelper = DatabaseHelper(this)

        // Obtener todas las mascotas
        val mascotas = dbHelper.getAllMascotas()
        for (mascota in mascotas) {
            Log.d("Mascota", """
                Nombre: ${mascota.nombre}, 
                Edad: ${mascota.edad}, 
                Tipo: ${mascota.tipo}, 
                Dueño: ${mascota.dueno}, 
                Vacunado: ${mascota.vacunado}, 
                Peso: ${mascota.peso}
            """.trimIndent())
        }
    }
}