package com.example.segundoparcial

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.segundoparcial.viewmodel.CrearNotaViewModel

class CrearNotaActivity : AppCompatActivity() {

    private val viewModel: CrearNotaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_nota)

        val etTitulo = findViewById<EditText>(R.id.etTituloNota)
        val etContenido = findViewById<EditText>(R.id.etContenidoNota)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarNota)
        val progressBar = findViewById<ProgressBar>(R.id.progressCrear)

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val contenido = etContenido.text.toString().trim()
            viewModel.guardarNota(titulo, contenido)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                btnGuardar.isEnabled = false
            } else {
                progressBar.visibility = View.GONE
                btnGuardar.isEnabled = true
            }
        }

        viewModel.statusMessage.observe(this) { result ->
            result.onSuccess { mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                finish()
            }
            result.onFailure { exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}