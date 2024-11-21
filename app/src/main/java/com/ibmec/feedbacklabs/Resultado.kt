package com.ibmec.feedbacklabs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Resultado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.result_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nomeUsuario = intent.getStringExtra("NOME_USUARIO") ?: "Usuário"
        val resultado = intent.getIntExtra("RESULTADO", 0)

        val scoreTextView = findViewById<TextView>(R.id.satisfaction_score)
        val label = findViewById<TextView>(R.id.satisfaction_label)
        val score = findViewById<TextView>(R.id.satisfaction_score)
        val returnButton = findViewById<Button>(R.id.btn_return_to_home)
        val resultImage = findViewById<ImageView>(R.id.result_image)
        val resultDescription = findViewById<TextView>(R.id.result_description)

        // val userScore = calcularPontuacao() // Sua lógica para calcular
        val userScore = resultado
        score.text = userScore.toString()

        if (userScore > 80) {
            resultImage.setImageResource(R.drawable.resultado1) // Imagem para alta satisfação
            resultDescription.text = "Você está muito satisfeito com o laboratório!"
        } else if (userScore > 50) {
            resultImage.setImageResource(R.drawable.resultado2) // Imagem para média satisfação
            resultDescription.text = "Você está razoavelmente satisfeito com o laboratório."
        } else {
            resultImage.setImageResource(R.drawable.resultado3) // Imagem para baixa satisfação
            resultDescription.text = "Você não está satisfeito com o laboratório."
        }

        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}