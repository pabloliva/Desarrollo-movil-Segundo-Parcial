package com.example.segundoparcial.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import data.api.ApiClient
import data.model.LoginResponse
import data.preferences.TokenManager
import com.example.segundoparcial.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Inicializamos las dependencias usando el contexto de la aplicación
    private val apiService = ApiClient.getApiService(application)
    private val repository = AuthRepository(apiService)
    private val tokenManager = TokenManager(application)

    // LiveData para manejar los estados de la interfaz de usuario
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> get() = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun login(usuario: String, contrasena: String) {
        // Validaciones básicas de campos vacíos antes de consumir la API
        if (usuario.isBlank() || contrasena.isBlank()) {
            _loginResult.value = Result.failure(Exception("Por favor, llena todos los campos"))
            return
        }

        _isLoading.value = true

        // Lanzamos la petición asíncrona a AWS en una corrutina
        viewModelScope.launch {
            try {
                val response = repository.login(usuario, contrasena)
                _isLoading.value = false

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    if (loginResponse.login) {
                        val token = loginResponse.token ?: "TOKEN_SIMULADO_PARCIAL_2"
                        tokenManager.saveToken(token, loginResponse.id_usuario, usuario)

                        _loginResult.value = Result.success(loginResponse)
                    } else {
                        _loginResult.value = Result.failure(Exception("Credenciales incorrectas"))
                    }
                } else {
                    _loginResult.value = Result.failure(Exception("Error en el servidor: ${response.code()}"))
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _loginResult.value = Result.failure(Exception("Error de conexión: No se pudo conectar a AWS"))
            }
        }
    }
}