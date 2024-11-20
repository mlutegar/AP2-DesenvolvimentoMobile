package com.ibmec.feedbacklabs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
        val botaoVoltar = findViewById<Button>(R.id.btn_back_to_menu)
        val nomeInput = findViewById<EditText>(R.id.et_name)
        val matriculaInput = findViewById<EditText>(R.id.et_matricula)

        botaoIniciar.setOnClickListener {
            val nome = nomeInput.text.toString().trim()
            val matricula = matriculaInput.text.toString().trim()

            if (nome.isEmpty() || matricula.isEmpty()) {
                // Exibe a mensagem de aviso
                Toast.makeText(this, "Por favor, preencha todos os campos antes de continuar.", Toast.LENGTH_SHORT).show()
            } else {
                // Avança para a próxima tela
                val intent = Intent(this, Formulario::class.java)
                intent.putExtra("NOME_USUARIO", nome) // Envia o nome
                intent.putExtra("MATRICULA_USUARIO", matricula) // Envia a matrícula
                startActivity(intent)
            }
        }

        botaoVoltar.setOnClickListener {
            // Criando a intent explícita para chamar a SecondActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}