package com.example.practica_03


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var nombreInput: EditText
    private lateinit var edadInput: EditText
    private lateinit var tipoInput: EditText
    private lateinit var duenoInput: EditText
    private lateinit var vacunadoInput: CheckBox
    private lateinit var pesoInput: EditText
    private lateinit var guardarButton: Button
    private lateinit var consultarButton: Button
    private lateinit var resultadoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asegúrate de que este sea el layout correcto

        // Instancia del DatabaseHelper
        val dbHelper = DatabaseHelper(this)

        // Inicialización de las vistas
        nombreInput = findViewById(R.id.nombreInput)
        edadInput = findViewById(R.id.edadInput)
        tipoInput = findViewById(R.id.tipoInput)
        duenoInput = findViewById(R.id.duenoInput)
        vacunadoInput = findViewById(R.id.vacunadoInput)
        pesoInput = findViewById(R.id.pesoInput)
        guardarButton = findViewById(R.id.guardarButton)
        consultarButton = findViewById(R.id.consultarButton)
        resultadoTextView = findViewById(R.id.resultadoTextView)

        // Configuración del botón "Guardar"
        guardarButton.setOnClickListener {
            agregarNuevaMascota(dbHelper)
        }

        // Configuración del botón "Consultar"
        consultarButton.setOnClickListener {
            mostrarMascotas(dbHelper)
        }
    }

    // Función para agregar una nueva mascota
    private fun agregarNuevaMascota(dbHelper: DatabaseHelper) {
        val nombre = nombreInput.text.toString()
        val edad = edadInput.text.toString().toInt()
        val tipo = tipoInput.text.toString()
        val dueno = duenoInput.text.toString()
        val vacunado = vacunadoInput.isChecked
        val peso = pesoInput.text.toString().toFloat()

        // Insertar los datos en la base de datos
        dbHelper.insertarMascota(Mascota(nombre, edad, tipo, dueno, vacunado, peso))
        resultadoTextView.text = "¡Mascota agregada exitosamente!"
        limpiarCampos()
    }

    // Función para mostrar todas las mascotas
    private fun mostrarMascotas(dbHelper: DatabaseHelper) {
        val mascotas = dbHelper.getAllMascotas()
        val resultado = StringBuilder()

        for (mascota in mascotas) {
            resultado.append("""
                Nombre: ${mascota.nombre}, 
                Edad: ${mascota.edad}, 
                Tipo: ${mascota.tipo}, 
                Dueño: ${mascota.dueno}, 
                Vacunado: ${mascota.vacunado}, 
                Peso: ${mascota.peso}
                \n
            """.trimIndent())
        }

        resultadoTextView.text = resultado.toString()
    }

    // Limpiar los campos de entrada
    private fun limpiarCampos() {
        nombreInput.text.clear()
        edadInput.text.clear()
        tipoInput.text.clear()
        duenoInput.text.clear()
        vacunadoInput.isChecked = false
        pesoInput.text.clear()
    }
}
