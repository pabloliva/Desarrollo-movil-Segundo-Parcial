package com.example.segundoparcial.repository

import data.api.ApiService
import okhttp3.ResponseBody
import retrofit2.Response
import data.model.NotaResponse

class NotasRepository(private val apiService: ApiService) {

    suspend fun guardarNota(idUsuario: String, titulo: String, contenido: String, fecha: String): Response<ResponseBody> {
        val body = mapOf(
            "id_usuario" to idUsuario,
            "titulo" to titulo,
            "contenido" to contenido,
            "fecha_creacion" to fecha
        )
        return apiService.crearNota(body)
    }

    suspend fun obtenerNotas(idUsuario: Int): Response<List<NotaResponse>> {
        return apiService.obtenerNotas(idUsuario)
    }
}