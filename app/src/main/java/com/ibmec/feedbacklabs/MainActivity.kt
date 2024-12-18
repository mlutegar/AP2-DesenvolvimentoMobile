package com.ibmec.feedbacklabs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botaoIniciar = findViewById<Button>(R.id.btn_start)
        botaoIniciar.setOnClickListener {
            // Criando a intent explícita para chamar a SecondActivity
            val intent = Intent(this, Informacoes::class.java)
            startActivity(intent)
        }

        val botaoSobre = findViewById<Button>(R.id.btn_about)
        botaoSobre.setOnClickListener {
            // Criando a intent explícita para chamar a SecondActivity
            val intent = Intent(this, Sobre::class.java)
            startActivity(intent)
        }
    }
}