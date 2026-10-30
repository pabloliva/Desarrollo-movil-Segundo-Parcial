package data.model

data class NotaResponse(
    val Id_Notas: Int,
    val Id_Usuario: Int,
    val Tititulo: String,
    val Contenido: String,
    val Fecha_creacion: String?
)