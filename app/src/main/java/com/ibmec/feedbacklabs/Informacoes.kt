package com.ibmec.feedbacklabs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Informacoes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_informacoes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botaoIniciar = findViewById<Button>(R.id.btn_continue)
        botaoIniciar.setOnClickListener {
            // Criando a intent explícita para chamar a SecondActivity
            val intent = Intent(this, Formulario::class.java)
            startActivity(intent)
        }

        val botaoVoltar = findViewById<Button>(R.id.btn_back_to_menu)
        botaoVoltar.setOnClickListener {
            // Criando a intent explícita para chamar a SecondActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}