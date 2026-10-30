package data.api

import data.model.LoginRequest
import data.model.LoginResponse
import data.model.NotaResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<LoginResponse>
    @POST("api/notas")
    suspend fun crearNota(@Body notaData: Map<String, String>): Response<ResponseBody>

    @GET("api/notas/{id_usuario}")
    suspend fun obtenerNotas(@Path("id_usuario") idUsuario: Int): Response<List<NotaResponse>>
}