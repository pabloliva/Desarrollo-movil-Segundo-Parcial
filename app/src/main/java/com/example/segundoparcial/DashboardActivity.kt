package com.example.segundoparcial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import data.preferences.TokenManager

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnGoToList = findViewById<Button>(R.id.btnGoToList)
        val btnCreateNote = findViewById<Button>(R.id.btnCreateNote)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val tokenManager = TokenManager(this)
        val usernameAutenticado = tokenManager.getUsername()

        tvWelcome.text = "¡Bienvenido, $usernameAutenticado!"

        btnLogout.setOnClickListener {
            tokenManager.clearData()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnCreateNote.setOnClickListener {
            val intent = Intent(this, CrearNotaActivity::class.java)
            startActivity(intent)
        }

        btnGoToList.setOnClickListener {
            val intent = Intent(this, ListadoNotasActivity::class.java)
            startActivity(intent)
        }
    }
}
