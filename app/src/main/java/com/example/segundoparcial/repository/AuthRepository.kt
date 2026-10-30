package com.example.segundoparcial.repository

import data.api.ApiService
import data.model.LoginRequest
import data.model.LoginResponse
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {

    suspend fun login(usuario: String, contrasena: String): Response<LoginResponse> {
        val request = LoginRequest(usuario, contrasena)
        return apiService.loginUser(request)
    }
}