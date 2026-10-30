package com.example.segundoparcial

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.segundoparcial.viewmodel.ListadoNotasViewModel
import com.example.segundoparcial.viewmodel.NotasAdapter

class ListadoNotasActivity : AppCompatActivity() {

    private val viewModel: ListadoNotasViewModel by viewModels()
    private lateinit var notasAdapter: NotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_notas)

        val rvNotas = findViewById<RecyclerView>(R.id.rvNotas)
        val progressListado = findViewById<ProgressBar>(R.id.progressListado)

        notasAdapter = NotasAdapter(emptyList())
        rvNotas.layoutManager = LinearLayoutManager(this)
        rvNotas.adapter = notasAdapter

        viewModel.isLoading.observe(this) { isLoading ->
            progressListado.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.notas.observe(this) { listaNotas ->
            if (listaNotas.isEmpty()) {
                Toast.makeText(this, "No tienes notas creadas aún.", Toast.LENGTH_SHORT).show()
            }
            notasAdapter.actualizarLista(listaNotas)
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.cargarNotas()
    }
}