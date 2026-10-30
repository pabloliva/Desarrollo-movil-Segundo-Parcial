package com.example.segundoparcial.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.segundoparcial.R
import data.model.NotaResponse

class NotasAdapter(private var listaNotas: List<NotaResponse>) : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    class NotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tvItemTitulo)
        val tvContenido: TextView = view.findViewById(R.id.tvItemContenido)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = listaNotas[position]
        holder.tvTitulo.text = nota.Tititulo
        holder.tvContenido.text = nota.Contenido
    }

    override fun getItemCount(): Int = listaNotas.size

    fun actualizarLista(nuevaLista: List<NotaResponse>) {
        listaNotas = nuevaLista
        notifyDataSetChanged()
    }
}