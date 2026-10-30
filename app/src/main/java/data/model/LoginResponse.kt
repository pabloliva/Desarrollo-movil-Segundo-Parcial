package data.model

data class LoginResponse(
    val login: Boolean,
    val id_usuario: Int,
    val token: String? = "TOKEN_SIMULADO_PARCIAL_2"
)