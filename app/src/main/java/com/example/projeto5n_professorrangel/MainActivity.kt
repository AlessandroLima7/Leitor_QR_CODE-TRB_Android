package com.example.projeto5n_professorrangel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val usuarios = HashMap<String, String>()
        usuarios["alessandro"] = "alessandro|Alessandro dos Santos|2665"
        usuarios["lucas"] = "lucas|Lucas de Sousa|2356"
        usuarios["fernando"] = "fernando|Fernando Alves|2582"
        usuarios["gustavo"] = "gustavo|Gustavo Machado|2679"
        usuarios["leandro"] = "leandro|Leandro Ramos|2077"
        usuarios["angel"] = "angel|Angel Javier|2659"

        val edtNome = findViewById<EditText>(R.id.edtNome)
        edtNome.requestFocus()
        val edtSenha = findViewById<EditText>(R.id.edtSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        btnEntrar.setOnClickListener {
            val strNome: String = edtNome.text.toString()
            val strSenha: String = edtSenha.text.toString()
            val usuario = usuarios[strNome]?.split("|")
            if(usuario?.get(0)?.compareTo(strNome) != 0) {
                Toast.makeText(applicationContext, "Usuário ou Senha Incorreto. Tente novamente.", Toast.LENGTH_LONG).show()
                edtNome.setText("")
                edtSenha.setText("")
            } else if(usuario?.get(2)?.compareTo(strSenha) != 0) {
                Toast.makeText(applicationContext, "Usuário ou Senha Incorreto. Tente novamente.", Toast.LENGTH_LONG).show()
                edtSenha.setText("")
                edtNome.setText("")
            } else {
                Toast.makeText(applicationContext, "Seja bem-vindo " + usuario[1].toString()+".", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LeitorDeProdutosActivity::class.java)
                startActivity(intent)
            }
        }

    }
}