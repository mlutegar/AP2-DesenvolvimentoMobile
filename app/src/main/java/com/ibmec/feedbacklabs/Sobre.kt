package com.ibmec.feedbacklabs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Sobre : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sobre)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.about_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botaoIniciar = findViewById<Button>(R.id.btn_back_to_menu)
        botaoIniciar.setOnClickListener {
            // Criando a intent explícita para chamar a SecondActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val githubButton = findViewById<Button>(R.id.github_button)
        githubButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://github.com/mlutegar/AP2-DesenvolvimentoMobile"))
            startActivity(intent)
        }

        val linkedinButton = findViewById<ImageButton>(R.id.btn_linkedin)
        linkedinButton.setOnClickListener {
            val linkedinUrl = "https://www.linkedin.com/in/seu-perfil" // Substitua pela URL do seu LinkedIn
            val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(linkedinUrl))
            startActivity(intent)
        }

        val mapsButton = findViewById<ImageButton>(R.id.btn_google_maps)
        mapsButton.setOnClickListener {
            val gmmIntentUri = android.net.Uri.parse("geo:-22.999009,-43.365556?q=IBMEC Barra da Tijuca")
            val intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            intent.setPackage("com.google.android.apps.maps") // Garantir que o Google Maps seja usado
            startActivity(intent)
        }
    }
}