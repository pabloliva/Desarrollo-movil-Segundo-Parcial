package com.example.segundoparcial.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import data.api.ApiClient
import data.model.NotaResponse
import data.preferences.TokenManager
import com.example.segundoparcial.repository.NotasRepository
import kotlinx.coroutines.launch

class ListadoNotasViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = ApiClient.getApiService(application)
    private val repository = NotasRepository(apiService)
    private val tokenManager = TokenManager(application)

    private val _notas = MutableLiveData<List<NotaResponse>>()
    val notas: LiveData<List<NotaResponse>> get() = _notas

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun cargarNotas() {
        val idUsuario = tokenManager.getUserId()
        if (idUsuario == -1) {
            _errorMessage.value = "Sesión inválida"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = repository.obtenerNotas(idUsuario)
                _isLoading.value = false

                if (response.isSuccessful && response.body() != null) {
                    _notas.value = response.body()
                } else {
                    _errorMessage.value = "Error al obtener datos del servidor"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = "Error No se pudo conectar a AWS"
            }
        }
    }
}