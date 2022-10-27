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
import org.w3c.dom.Text

class LeitorDeProdutosActivity: AppCompatActivity(R.layout.leitor_de_produtos) {
    var pipe:String = "|"
    var leuLista:Boolean = false
    var produtosParaBusca = HashMap<String, Int>()
    var listaCodProdutos: MutableList<String> = mutableListOf()
    var relatorioFinal: String = ""
    var qtdEscaneado:Int = 0
    var coletados:Int = 0
    var qtdAprovada:Boolean = false
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
    var codigoFinal:Boolean = false
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
            relatorioFinal = ""

            n = ""
            a = ""

            ruaFinal = false
            numeroFinal = false
            andarFinal = false
            codigoFinal = false
            produtoEncontrado = false
            qtdAprovada = false
            leuLista = false

            listaCodProdutos.clear()
            produtosParaBusca.clear()

            val txtRua = findViewById<TextView>(R.id.txtRua)
            val txtRelatorio = findViewById<TextView>(R.id.txtRelatorio)
            txtRua.isVisible = false
            txtRua.text = ""
            txtRelatorio.isVisible = false
            txtRelatorio.text = ""
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

        if(pipe in result || leuLista) {
            leuLista = true
            resultRead(result)
            if (produtosParaBusca.isEmpty()) {
                if (produtosParaBusca.isEmpty() && result.indexOf("|") != 1) {
                    var diviProdutos = result.split("|")
                    for (i in diviProdutos) {
                        var p = i.split(":")
                        produtosParaBusca[p[0]] = p[1].toInt()
                        listaCodProdutos.add(p[0])


                    }
                    var product: String = listaCodProdutos[0]
                    var tudo = pro.search(product)
                    if (tudo == "Código EAN13 não encontrado.") {
                        Toast.makeText(
                            applicationContext,
                            "Código EAN13 não encontrado!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
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
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Por favor, leia uma lista de produtos.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                val txtRua = findViewById<TextView>(R.id.txtRua)

                if (result != rua && !ruaFinal) {
                    var resp = rua.replace("R", "RUA ")
                    txtRua.isVisible = true
                    txtRua.text = "Encontre: $resp"
                } else {
                    ruaFinal = true
                    if (result != numero && !numeroFinal) {
                        var resp = rua.replace("R", "RUA ")
                        resp += n.replace("N", " - NÚMERO ")
                        txtRua.text = "Encontre: $resp"
                    } else {
                        numeroFinal = true
                        if (result != andar && !andarFinal) {
                            var resp = rua.replace("R", "RUA ")
                            resp += n.replace("N", " - NÚMERO ")
                            resp += " - " + a.replace("A", "ª ANDAR ")
                            txtRua.text = "Encontre: $resp"
                        } else {
                            andarFinal = true
                            if (result != codigoMaster && !codigoFinal) {
                                var resp = rua.replace("R", "RUA ")
                                resp += n.replace("N", " - NÚMERO ")
                                resp += " - " + a.replace("A", "ª ANDAR ")
                                resp += " - " + codigoMaster
                                txtRua.text = "Encontre: $resp"
                            } else {
                                qtdEscaneado += 1
                                var qtdEstoque = strQtdEstoque.toInt()
                                var qtdRetirar: Int? = produtosParaBusca[codigoMaster]
                                if (qtdRetirar != null) {

                                    if (qtdEscaneado != qtdRetirar) {
                                        var vezes: Int = qtdRetirar - qtdEscaneado
                                        Toast.makeText(
                                            applicationContext,
                                            "Coletar mais $vezes",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        val scanner = IntentIntegrator(this)
                                        scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                                        scanner.setBeepEnabled((false))
                                        scanner.initiateScan()
                                    } else {
                                        qtdEstoque -= qtdRetirar
                                        coletados = qtdRetirar
                                        qtdAprovada = true
                                    }

                                }

                                if (qtdAprovada) {

                                    val txtRelatorio = findViewById<TextView>(R.id.txtRelatorio)

                                    Toast.makeText(
                                        applicationContext,
                                        "Produto Encontrado: $produto",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    produtoEncontrado = true

                                    var estoque = qtdEstoque.toString()
                                    val tudo = pro.search(codigoMaster)
                                    val cod = tudo.split(("|"))
                                    val nume = cod[3]
                                    val and = cod[4]
                                    pro.insert(
                                        codigoMaster,
                                        "$codigoMaster|$produto|$rua|$nume|$and|$estoque"
                                    )
                                    var relatorio =
                                        "Código EAN: $codigoMaster  \nProduto: $produto\nQuantidade coletada: $coletados \nEstoque: $estoque\n"
                                    listaCodProdutos.removeAt(0)
                                    produtosParaBusca.remove(codigoMaster)
                                    codigoMaster = ""
                                    qtdEscaneado = 0
                                    coletados = 0
                                    qtdAprovada = false
                                    ruaFinal = false // PARA DIZER SE O
                                    numeroFinal = false
                                    andarFinal = false
                                    codigoFinal = false
                                    produtoEncontrado = false

                                    if (produtosParaBusca.isNotEmpty()) {
                                        var product: String = listaCodProdutos[0]
                                        var tudo = pro.search(product)
                                        if (tudo == "Código EAN13 não encontrado.") {
                                            Toast.makeText(
                                                applicationContext,
                                                "Código EAN13 não encontrado!",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        } else {
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
                                            txtRua.text = "Novo Produto\n Encontre: $resp"
                                        }
                                    } else {
                                        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
                                        btnBuscar.isVisible = false
                                        txtRua.text = "Lista finalizada."
                                    }

                                    relatorioFinal += relatorio
                                    txtRelatorio.isVisible = true
                                    txtRelatorio.text = ""
                                    txtRelatorio.text = relatorioFinal


                                }


                            }
                        }


                    }
                }
            }

        }
        else {
            Toast.makeText(applicationContext, "Leia uma lista!", Toast.LENGTH_LONG).show()
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




