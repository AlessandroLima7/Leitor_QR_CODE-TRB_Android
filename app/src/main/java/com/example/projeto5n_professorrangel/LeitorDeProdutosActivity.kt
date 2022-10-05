package com.example.projeto5n_professorrangel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class LeitorDeProdutosActivity: AppCompatActivity(R.layout.leitor_de_produtos) {
    var pro:Produtos = Produtos();
    var codigoMaster:String = ""
    var n = ""
    var a = ""

    var rua:String = ""
    var numero:String = ""
    var andar:String = ""
    var produto:String = ""
    var strQtdEstoque:String = ""

    var ruaFinal:Boolean = false // PARA DIZER SE O
    var numeroFinal:Boolean = false
    var andarFinal:Boolean = false
    var produtoEncontrado:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leitor_de_produtos)

        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val btnRestart = findViewById<Button>(R.id.btnRestart)

        btnBuscar.setOnClickListener(){
            val scanner = IntentIntegrator(this)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.setBeepEnabled((false))
            scanner.initiateScan()
        }

        btnRestart.setOnClickListener(){
            btnBuscar.isVisible = true
            codigoMaster = ""

            rua = ""
            numero = ""
            andar = ""
            produto = ""
            strQtdEstoque = ""

            n = ""
            a = ""

            ruaFinal = false
            numeroFinal = false
            andarFinal = false
            produtoEncontrado = false

            val txtRua = findViewById<TextView>(R.id.txtRua)
            txtRua.isVisible = false
            txtRua.text = ""
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if(result.contents == null) {
                Toast.makeText(applicationContext, "Não escaneado.", Toast.LENGTH_SHORT).show()
            }
            else {
                // Toast.makeText(applicationContext, "Primeiro Scanner." + result.contents, Toast.LENGTH_SHORT).show()
                val res = result.contents.toString()
                systemLogic(res)
            }
        }
    }

    fun systemLogic(result:String) {
        resultRead(result)
        if(codigoMaster == "") {
            var tudo = pro.search(result)
            if(tudo == "Código EAN13 não encontrado.") {
                Toast.makeText(applicationContext, "Código EAN13 não encontrado!", Toast.LENGTH_SHORT).show()
            }
            else {
                val codigo = tudo.split("|")
                codigoMaster = codigo[0]
                rua = codigo[2]
                numero = codigo[2] + "-" + codigo[3]
                n = codigo[3]
                andar = codigo[2] + "-" + codigo[3] + "-" + codigo[4]
                a = codigo[4]
                produto = codigo[1]
                strQtdEstoque = codigo[5]
                var resp = rua.replace("R", "RUA ")
                val txtRua = findViewById<TextView>(R.id.txtRua)
                txtRua.isVisible = true
                txtRua.text = "Encontre: $resp"
            }
        }
        else {
            val txtRua = findViewById<TextView>(R.id.txtRua)

            if(result != rua && !ruaFinal) {
                var resp = rua.replace("R", "RUA ")
                txtRua.isVisible = true
                txtRua.text = "Encontre: $resp"
            }
            else {
                ruaFinal = true
                if(result != numero && !numeroFinal) {
                    var resp = rua.replace("R", "RUA ")
                    resp += n.replace("N", " - NÚMERO ")
                    txtRua.text = "Encontre: $resp"
                }
                else {
                    numeroFinal = true
                    if(result != andar && !andarFinal) {
                        var resp = rua.replace("R", "RUA ")
                        resp += n.replace("N", " - NÚMERO ")
                        resp += " - " + a.replace("A", "ª ANDAR ")
                        txtRua.text = "Encontre: $resp"
                    }
                    else {
                        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
                        btnBuscar.isVisible = false
                        andarFinal = true
                        Toast.makeText(applicationContext, "Produto Encontrado: $produto", Toast.LENGTH_LONG).show()
                        produtoEncontrado = true
                        var qtdEstoque = strQtdEstoque.toInt()
                        qtdEstoque -= 1
                        var estoque = qtdEstoque.toString()
                        val tudo = pro.search(codigoMaster)
                        val cod = tudo.split(("|"))
                        val nume = cod[3]
                        val and = cod[4]
                        pro.insert(codigoMaster, "$codigoMaster|$produto|$rua|$nume|$and|$estoque")
                        txtRua.text = "Código EAN: $codigoMaster \nRua: ${rua.replace("R", "RUA ")} " +
                                "\nNúmero: ${n.replace("N", "NÚMERO ")} \nAndar: ${a.replace("A", "º ANDAR")} \nProduto: $produto\nEstoque: $estoque"
                        codigoMaster = ""
                    }
                }
            }
        }
    }

    fun resultRead(res:String) {
        var resp = res

        if(res.length == 2){
            resp = res.replace("R", "RUA ")
        }
        else if(res.length == 5){
            resp = res.replace("R", "RUA ").replace("-", " - ")
            resp = resp.replace("N", "NÚMERO ")
        } else if(res.length == 8){
            resp = res.replace("R", "RUA ")
            resp = resp.replace("N", "NÚMERO ")

            var nova = resp.split("-")
            var andar = nova[2].replace("A", "º ANDAR")
            resp = nova[0] + " - " + nova[1] + " - " + andar
        }

        Toast.makeText(applicationContext, "$resp", Toast.LENGTH_SHORT).show()
    }
        }




