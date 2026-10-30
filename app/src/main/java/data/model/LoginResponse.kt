package data.model

data class LoginResponse(
    val login: Boolean,
    val id_usuario: Int,
    val token: String? = "TOKEN_SIMULADO_PARCIAL_2" // En caso de que tu API devuelva campo token, de lo contrario usamos este por defecto
)