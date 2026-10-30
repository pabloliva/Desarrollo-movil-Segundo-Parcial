package data.api

import android.content.Context
import data.preferences.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://52.91.226.13:3000" // Reemplaza exactamente con tu IP de AWS válida

    fun getApiService(context: Context): ApiService {
        val tokenManager = TokenManager(context)

        // CLIENTE OKHTTP CON INTERCEPTOR AUTOMÁTICO DE HEADERS
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val token = tokenManager.getToken()

                val newRequest = if (token != null) {
                    // Si el token existe en SharedPreferences, se inyecta automáticamente en todas las peticiones
                    originalRequest.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                } else {
                    originalRequest
                }
                chain.proceed(newRequest)
            }.build()

        // CONSTRUCCIÓN DE RETROFIT
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}