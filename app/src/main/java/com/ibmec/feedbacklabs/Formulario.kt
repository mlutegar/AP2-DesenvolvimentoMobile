package com.ibmec.feedbacklabs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
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

        val nomeUsuario = intent.getStringExtra("NOME_USUARIO") ?: "Usuário"
        val matriculaUsuario = intent.getStringExtra("MATRICULA_USUARIO") ?: "Matrícula não fornecida"

        val questionCounter = findViewById<TextView>(R.id.question_counter)
        val questionImage = findViewById<ImageView>(R.id.question_image)
        val questionText = findViewById<TextView>(R.id.question_text)
        val answer1 = findViewById<RadioButton>(R.id.answer_1)
        val answer2 = findViewById<RadioButton>(R.id.answer_2)
        val answer3 = findViewById<RadioButton>(R.id.answer_3)
        val answer4 = findViewById<RadioButton>(R.id.answer_4)

        val questions: List<Triple<String, Int, List<String>>> = listOf(
            Triple(
                "$nomeUsuario, qual é o seu vínculo com o IBMEC?",
                R.drawable.pergunta1, // Substituir por imagem correspondente
                listOf("Aluno(a)", "Professor(a)", "Outro")
            ),
            Triple(
                "$nomeUsuario, você utilizou o serviço de:",
                R.drawable.pergunta1, // Substituir por imagem correspondente
                listOf("Manuseio de equipamentos no laboratório", "Empréstimo de equipamentos/notebooks", "Assistência em práticas")
            ),

            // Tela 2: Qualidade do Atendimento
            Triple(
                "$nomeUsuario, como você avalia a cordialidade do técnico que o(a) atendeu?",
                R.drawable.pergunta1, // Substituir por imagem correspondente
                listOf("Excelente", "Bom", "Regular", "Ruim")
            ),
            Triple(
                "$nomeUsuario, o técnico solucionou sua dúvida ou problema de forma satisfatória?",
                R.drawable.pergunta1, // Substituir por imagem correspondente
                listOf("Sim", "Parcialmente", "Não")
            ),
            Triple(
                "$nomeUsuario, como você avalia o tempo de resposta/atendimento?",
                R.drawable.pergunta1, // Substituir por imagem correspondente
                listOf("Rápido", "Dentro do esperado", "Lento")
            ),

            // Tela 3: Feedback e Sugestões
            Triple(
                "$nomeUsuario, você recomendaria os serviços dos técnicos do laboratório para outros alunos/professores?",
                R.drawable.pergunta1, // Substituir por imagem correspondente
                listOf("Sim", "Não")
            ),
            Triple(
                "$nomeUsuario, o que você sugere para melhorar o atendimento?",
                R.drawable.pergunta1, // Substituir por imagem correspondente
                listOf("Preencha no campo de texto abaixo") // Representação para campo de texto aberto
            ),
            Triple(
                "$nomeUsuario, você gostaria de receber atualizações sobre melhorias no laboratório?",
                R.drawable.pergunta1, // Substituir por imagem correspondente
                listOf("Sim", "Não")
            )
        )

        fun calcularResultado(respostas: List<Int>): Int {
            val pesoRespostas = listOf( // Pesos atribuídos às respostas por pergunta
                listOf(100, 80, 60), // Pergunta 1
                listOf(100, 90, 80), // Pergunta 2
                listOf(100, 80, 60, 40), // Pergunta 3
                listOf(100, 70, 50), // Pergunta 4
                listOf(100, 80, 60), // Pergunta 5
                listOf(100, 50), // Pergunta 6
                listOf(100), // Pergunta 7 (campo aberto pode ser tratado de forma personalizada)
                listOf(100, 50) // Pergunta 8
            )

            var totalPeso = 0
            var totalPontos = 0

            respostas.forEachIndexed { index, respostaIndex ->
                if (respostaIndex >= 0 && index < pesoRespostas.size) {
                    totalPontos += pesoRespostas[index][respostaIndex]
                    totalPeso += 100
                }
            }

            return if (totalPeso > 0) (totalPontos * 100) / totalPeso else 0
        }

        var currentQuestionIndex = 0
        fun updateQuestion() {
            val (question, imageRes, answers) = questions[currentQuestionIndex]
            questionCounter.text = "Pergunta ${currentQuestionIndex + 1} de ${questions.size}"
            questionImage.setImageResource(imageRes)
            questionText.text = question

            // Atualiza as alternativas com base no tamanho da lista de respostas
            val radioButtons = listOf(answer1, answer2, answer3, answer4)
            for (i in radioButtons.indices) {
                if (i < answers.size) {
                    radioButtons[i].text = answers[i]
                    radioButtons[i].visibility = View.VISIBLE
                } else {
                    radioButtons[i].visibility = View.GONE
                }
            }

            findViewById<RadioGroup>(R.id.answers_group).clearCheck()
        }

        updateQuestion()

        findViewById<Button>(R.id.btn_next).setOnClickListener {
            val respostasSelecionadas = mutableListOf<Int>()

            val selectedOption = findViewById<RadioGroup>(R.id.answers_group).checkedRadioButtonId
            val selectedIndex = when (selectedOption) {
                R.id.answer_1 -> 0
                R.id.answer_2 -> 1
                R.id.answer_3 -> 2
                R.id.answer_4 -> 3
                else -> -1 // Nenhuma opção selecionada
            }

            if (selectedIndex != -1) {
                respostasSelecionadas.add(selectedIndex) // Salva a resposta selecionada
            }

            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                updateQuestion()
            } else {
                // Navega para a tela de Resultado
                val resultado = calcularResultado(respostasSelecionadas)
                val intent = Intent(this, Resultado::class.java) // Substitua Resultado pelo nome da sua classe de resultado
                intent.putExtra("NOME_USUARIO", nomeUsuario)
                intent.putExtra("MATRICULA_USUARIO", matriculaUsuario)
                intent.putExtra("RESULTADO", resultado)
                startActivity(intent)
                finish() // Finaliza a atividade atual para evitar voltar ao formulário
            }
        }


        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // Substitua Resultado pelo nome da sua classe de resultado
            startActivity(intent)
            finish() // Finaliza a atividade atual para evitar voltar ao formulário
        }
    }
}