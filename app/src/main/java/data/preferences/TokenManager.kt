package data.preferences

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("PREFS_TOKEN", Context.MODE_PRIVATE)

    companion object {
        private const val USER_TOKEN = "user_token"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name" // Constante añadida
    }

    fun saveToken(token: String, idUsuario: Int, username: String) { // Parámetro username añadido
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putInt(USER_ID, idUsuario)
        editor.putString(USER_NAME, username) // Guardamos el nombre
        editor.apply()
    }

    fun getToken(): String? = prefs.getString(USER_TOKEN, null)

    fun getUsername(): String? = prefs.getString(USER_NAME, "Usuario") // Método añadido

    fun clearData() {
        prefs.edit().clear().apply()
    }
    fun getUserId(): Int {
        return prefs.getInt(USER_ID, -1)
    }
}