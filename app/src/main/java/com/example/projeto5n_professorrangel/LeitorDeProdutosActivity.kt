package com.example.projeto5n_professorrangel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import org.w3c.dom.Text

class LeitorDeProdutosActivity: AppCompatActivity(R.layout.leitor_de_produtos) {

    var resultado:String = ""
    var ruaFinal:Boolean = false
    var numeroFinal:Boolean = false
    var andarFinal:Boolean = false
    var apertado:Boolean = false
    var produtoEncontrado:Boolean = false
    var codigoMaster:String = ""
    var codigoTeste:String = ""

    fun atualizar(res:String) {
        Toast.makeText(applicationContext, "Lido: " +  res , Toast.LENGTH_SHORT).show()
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
                resultado = result.contents.toString()
                val txtRua = findViewById<TextView>(R.id.txtRua)
                atualizar(resultado)
                val codigos = HashMap<String, String>()

                codigos["7894900010015"] = "7894900010015|COCA LATA 350ML|RA|N1|1A"
                codigos["7894900011517"] = "7894900011517|COCA GARRAFA 2L|RA|N1|2A"
                codigos["7891991000833"] = "7891991000833|SODA LIMONADA|RA|N1|3A"
                codigos["7896004000855"] = "7896004000855|SUCRILHOS KELLOGG'S ORIGINAL 250G |RB|N1|1A"

                if(codigoMaster == "") {
                    codigoTeste = resultado
                }
                else {
                    codigoTeste = codigoMaster
                }

                val codigo = codigos[codigoTeste]?.split("|")

                if(codigo?.get(0) == null) {
                    Toast.makeText(applicationContext, "Código EAN13 não encontrado!", Toast.LENGTH_SHORT).show()
                }
                else if(codigoMaster == ""){
                    val rua = codigo?.get(2).toString()
                    txtRua.text = codigoMaster
                    Toast.makeText(applicationContext, "Encontre a rua: $rua", Toast.LENGTH_SHORT).show()
                    codigoMaster = resultado
                    apertado = true

                }
                else {
                    apertado = true

                    val ean = codigo?.get(0).toString()
                    val rua = codigo?.get(2).toString()
                    val numero = codigo?.get(2).toString() + "-" + codigo?.get(3).toString()
                    val andar = codigo?.get(2).toString() + "-" + codigo?.get(3).toString() + "-" + codigo?.get(4).toString()
                    val produto = codigo?.get(1).toString()


                    if(rua != resultado && !ruaFinal) {
                        Toast.makeText(applicationContext, "Encontre a rua: $rua  ", Toast.LENGTH_LONG).show()
                    }
                    else {
                        ruaFinal = true
                        if(numero != resultado && !numeroFinal) {
                            Toast.makeText(applicationContext, "Encontre o número: $numero ", Toast.LENGTH_LONG).show()
                        }
                        else {
                            numeroFinal = true
                            if(andar != resultado && !andarFinal) {
                                Toast.makeText(applicationContext, "Encontre o andar: $andar", Toast.LENGTH_LONG).show()
                            }
                            else {
                                andarFinal = true
                                Toast.makeText(applicationContext, "Produto Encontrado: $produto", Toast.LENGTH_LONG).show()
                                produtoEncontrado = true
                                codigoMaster = ""
                                txtRua.isVisible = true
                                txtRua.text = "Código EAN: $ean \nRua: $rua \nNúmero: $numero \nAndar: $andar \nProduto: $produto"
                            }
                        }
                    }

                }

            }
        }
    }

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
             resultado = ""
             ruaFinal = false
             numeroFinal = false
             andarFinal = false
            apertado = false
             produtoEncontrado = false
            codigoMaster = ""
            codigoTeste = ""

            val txtRua = findViewById<TextView>(R.id.txtRua)
            txtRua.isVisible = false
            txtRua.text = ""
        }

            }

        }




