package com.example.segundoparcial.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import data.api.ApiClient
import data.preferences.TokenManager
import com.example.segundoparcial.repository.NotasRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CrearNotaViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = ApiClient.getApiService(application)
    private val repository = NotasRepository(apiService)
    private val tokenManager = TokenManager(application)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _statusMessage = MutableLiveData<Result<String>>()
    val statusMessage: LiveData<Result<String>> get() = _statusMessage

    fun guardarNota(titulo: String, contenido: String) {
        if (titulo.isBlank() || contenido.isBlank()) {
            _statusMessage.value = Result.failure(Exception("El título y el contenido de la nota no pueden estar vacíos"))
            return
        }

        val idUsuario = tokenManager.getUserId()
        if (idUsuario == -1) {
            _statusMessage.value = Result.failure(Exception("Error de sesión: ID de usuario no encontrado"))
            return
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fechaActual = sdf.format(Date())

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = repository.guardarNota(idUsuario.toString(), titulo, contenido, fechaActual)
                _isLoading.value = false

                if (response.isSuccessful) {
                    _statusMessage.value = Result.success("Nota guardada.")
                } else {
                    _statusMessage.value = Result.failure(Exception("Fallo en el servidor: Cod ${response.code()}"))
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _statusMessage.value = Result.failure(Exception("Error de conexión a AWS: ${e.localizedMessage}"))
            }
        }
    }
}