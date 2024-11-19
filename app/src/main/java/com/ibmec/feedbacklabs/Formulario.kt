package com.ibmec.feedbacklabs

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Formulario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val questionCounter = findViewById<TextView>(R.id.question_counter)
        val questionImage = findViewById<ImageView>(R.id.question_image)
        val questionText = findViewById<TextView>(R.id.question_text)
        val answer1 = findViewById<RadioButton>(R.id.answer_1)
        val answer2 = findViewById<RadioButton>(R.id.answer_2)
        val answer3 = findViewById<RadioButton>(R.id.answer_3)
        val answer4 = findViewById<RadioButton>(R.id.answer_4)

        val questions: List<Triple<String, Int, List<String>>> = listOf(
            Triple(
                "Como você avalia o atendimento?",
                R.drawable.pergunta1,
                listOf("Excelente", "Bom", "Regular", "Ruim")
            ),
            Triple(
                "O técnico foi cordial?",
                R.drawable.pergunta2,
                listOf("Sim", "Não", "Parcialmente", "Não se aplica")
            )
        )

        var currentQuestionIndex = 0
        fun updateQuestion() {
            val (question, imageRes, answers) = questions[currentQuestionIndex]
            questionCounter.text = "Pergunta ${currentQuestionIndex + 1} de ${questions.size}"
            questionImage.setImageResource(imageRes)
            questionText.text = question
            answer1.text = answers[0]
            answer2.text = answers[1]
            answer3.text = answers[2]
            answer4.text = answers[3]
        }

        updateQuestion()

        findViewById<Button>(R.id.btn_next).setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                updateQuestion()
            }
        }

        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish() // Fecha a atividade
        }
    }
}