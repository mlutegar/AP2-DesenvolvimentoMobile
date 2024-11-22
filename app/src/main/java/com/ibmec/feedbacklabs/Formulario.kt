package com.ibmec.feedbacklabs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Formulario : AppCompatActivity() {
    private val respostasSelecionadas = mutableListOf<Int>() // Armazena todas as respostas selecionadas

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
                R.drawable.pergunta2, // Substituir por imagem correspondente
                listOf("Manuseio de equipamentos no laboratório", "Empréstimo de equipamentos/notebooks", "Assistência em práticas")
            ),

            // Tela 2: Qualidade do Atendimento
            Triple(
                "$nomeUsuario, como você avalia a cordialidade do técnico que o(a) atendeu?",
                R.drawable.pergunta3, // Substituir por imagem correspondente
                listOf("Excelente", "Bom", "Regular", "Ruim")
            ),
            Triple(
                "$nomeUsuario, o técnico solucionou sua dúvida ou problema de forma satisfatória?",
                R.drawable.pergunta4, // Substituir por imagem correspondente
                listOf("Sim", "Parcialmente", "Não")
            ),
            Triple(
                "$nomeUsuario, como você avalia o tempo de resposta/atendimento?",
                R.drawable.pergunta5, // Substituir por imagem correspondente
                listOf("Rápido", "Dentro do esperado", "Lento")
            ),
            Triple(
                "$nomeUsuario, como você avalia o profissionalismo do técnico que o(a) atendeu?",
                R.drawable.pergunta6, // Substituir por imagem correspondente
                listOf("Excelente", "Bom", "Regular", "Ruim")
            ),
            Triple(
                "$nomeUsuario, o técnico explicou as informações de maneira clara e compreensível?",
                R.drawable.pergunta7, // Substituir por imagem correspondente
                listOf("Sim", "Parcialmente", "Não")
            ),
            Triple(
                "$nomeUsuario, como você avalia a eficiência do técnico no atendimento?",
                R.drawable.pergunta8, // Substituir por imagem correspondente
                listOf("Excelente", "Boa", "Regular", "Ruim")
            ),
            Triple(
                "$nomeUsuario, como você avalia o estado dos equipamentos utilizados pelo técnico durante o atendimento?",
                R.drawable.pergunta9, // Substituir por imagem correspondente
                listOf("Excelente", "Bom", "Regular", "Ruim")
            ),

            // Tela 3: Feedback e Sugestões
            Triple(
                "$nomeUsuario, você recomendaria os serviços dos técnicos do laboratório para outros alunos/professores?",
                R.drawable.pergunta10, // Substituir por imagem correspondente
                listOf("Sim", "Não")
            ),
            Triple(
                "$nomeUsuario, você gostaria de receber atualizações sobre melhorias no laboratório?",
                R.drawable.pergunta11, // Substituir por imagem correspondente
                listOf("Sim", "Não")
            )
        )

        fun calcularResultado(respostas: List<Int>): Int {
            val pesoRespostas = listOf( // Pesos apenas para perguntas de qualidade do atendimento
                null, // Pergunta 1: Sem peso (contexto)
                null, // Pergunta 2: Sem peso (contexto)
                listOf(100, 80, 60, 40), // Pergunta 3: Cordialidade
                listOf(100, 70, 50), // Pergunta 4: Solução de dúvidas
                listOf(100, 80, 60), // Pergunta 5: Tempo de atendimento
                listOf(100, 80, 60, 40), // Pergunta 6: Profissionalismo
                listOf(100, 70, 50), // Pergunta 7: Clareza
                listOf(100, 80, 60, 40), // Pergunta 8: Eficiência
                listOf(100, 80, 60, 40), // Pergunta 9: Estado dos equipamentos
                null, // Pergunta 10: Sem peso (recomendação)
                null  // Pergunta 11: Sem peso (atualizações)
            )

            var totalPeso = 0
            var totalPontos = 0

            respostas.forEachIndexed { index, respostaIndex ->
                if (respostaIndex >= 0 && pesoRespostas[index] != null) { // Aplica peso apenas para perguntas com pesos
                    val pesos = pesoRespostas[index]!!
                    totalPontos += pesos[respostaIndex]
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
            val selectedOption = findViewById<RadioGroup>(R.id.answers_group).checkedRadioButtonId
            val selectedIndex = when (selectedOption) {
                R.id.answer_1 -> 0
                R.id.answer_2 -> 1
                R.id.answer_3 -> 2
                R.id.answer_4 -> 3
                else -> -1 // Nenhuma opção selecionada
            }

            if (selectedIndex != -1) {
                // Salva a resposta selecionada
                respostasSelecionadas.add(selectedIndex)

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
            } else {
                // Exibe mensagem de alerta se nenhuma opção foi selecionada
                Toast.makeText(this, "Por favor, selecione uma opção para continuar.", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<ImageButton>(R.id.btn_cancel).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}