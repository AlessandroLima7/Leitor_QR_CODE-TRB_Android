package com.example.projeto5n_professorrangel

class Produtos {

    private var codigos = HashMap<String, String>()
    init {
        codigos["7894900010015"] = "7894900010015|REFRIGERANTE COCA-COLA LATA 350ML|RA|N1|1A|1000"
        codigos["7894900011517"] = "7894900011517|REFRIGERANTE COCA-COLA GARRAFA 2L|RA|N1|2A|1000"
        codigos["7891991000833"] = "7891991000833|REFRIGERANTE SODA LIMONADA ANTARTIC LATA 350ML|RA|N1|3A|1000"
        codigos["7891991011020"] = "7891991011020|REFRIGERANTE GUARANA ANTARCTICA LATA 350ML|RA|N2|1A|1000"
        codigos["7898712836870"] = "7898712836870|REFRIGERANTE GUARANA ANTARCTICA 2L|RA|N2|2A|1000"
        codigos["7894900039924"] = "7894900039924|REFRIGERANTE FANTA LARANJA 2L|RA|N2|3A|1000"
        codigos["7894900031201"] = "7894900031201|REFRIGERANTE FANTA LARANJA LATA 350ML|RA|N2|4A|1000"
        codigos["7892840800079"] = "7892840800079|REFRIGERANTE PEPSI LATA 350ML|RA|N3|1A|1000"
        codigos["7892840813017"] = "7892840813017|REFRIGERANTE PEPSI 2L|RA|N3|2A|1000"
        codigos["7896004000855"] = "7896004000855|SUCRILHOS KELLOGG'S ORIGINAL 250G|RB|N1|1A|1000"
        codigos["7896004003979"] = "7896004003979|SUCRILHOS KELLOGG'S CHOCOLATE 320G|RB|N1|2A|1000"
        codigos["7896110005140"] = "7896110005140|PAPEL HIGIÊNICO PERSONAL FOLHA SIMPLES NEUTRO 60 METROS 4 UNIDADES|RB|N2|1A|1000"
        codigos["7896104998953"] = "7896104998953|PAPEL HIGIÊNICO MILI 4R|RB|N2|2A|1000"
        codigos["7896076002146"] = "7896076002146|PAPEL HIGIENICO DAMA 60MTR|RB|N2|3A|1000"
        codigos["7896276060021"] = "7896276060021|ARROZ AGULHINHA ARROZAL T1 5KG|RC|N1|1A|1000"
        codigos["7898295150189"] = "7898295150189|ARROZ SABOROSO 5KG|RC|N1|2A|1000"
        codigos["7896086423030"] = "7896086423030|ARROZ TRIMAIS 5KG|RC|N1|3A|1000"
        codigos["7896864400192"] = "7896864400192|FEIJAO PICININ 1KG|RC|N2|1A|1000"
        codigos["7897924800877"] = "7897924800877|FEIJAO PRETO VENEZA 1KG|RC|N2|2A|1000"
        codigos["7898084090030"] = "7898084090030|FEIJÃO PEREIRA CARIOQUINHA 1KG|RC|N2|3A|1000"
        codigos["7891959004415"] = "7891959004415|AÇUCAR REFINADO DOÇULA 1KG|RD|N1|1A|1000"
        codigos["7896032501010"] = "7896032501010|AÇÚCAR REFINADO DA BARRA 1KG|RD|N1|2A|1000"
        codigos["7896109801005"] = "7896109801005|AÇÚCAR REFINADO ESPECIAL GUARANI 1KG|RD|N1|3A|1000"
        codigos["7896319420546"] = "7896319420546|ACUCAR REFINADO CLARION 1KG|RD|N2|1A|1000"
        codigos["7896089028935"] = "7896089028935|CAFÉ TORRADO MOÍDO POUCHE CAFÉ DO PONTO 500G|RD|N2|2A|1000"
        codigos["7898286200077"] = "7898286200077|CAFE MARATA 500G|RD|N2|3A|1000"
        codigos["7891910010905"] = "7891910010905|CAFE CABOCLO 500G|RD|N3|1A|1000"
        codigos["7898079250012"] = "7898079250012|CAFE FIORENZA 500G|RD|N3|2A|1000"
        codigos["7891107000504"] = "7891107000504|OLEO DE SOJA SOYA 1L|RE|N1|1A|1000"
        codigos["7896334200550"] = "7896334200550|OLEO DE SOJA GRANOL 1L|RE|N2|1A|1000"
        codigos["7896036090107"] = "7896036090107|OLEO DE SOJA VELEIRO 1L|RE|N3|1A|1000"
    }

    fun search(resultado:String): String {

        val codigo = codigos[resultado]

        if(codigo?.get(0) == null) {
            return "Código EAN13 não encontrado."
        }
        else{
            return codigo
        }

    }

    fun checkQ(codigoEan:String, qtd:Int):String{
        val produto = codigos[codigoEan]
        if(produto?.get(0) == null) {
            return "Código EAN13 não encontrado."
        }
        else {
            var prod = produto.split("|")
            var quantProd = prod[5].toInt()
            if(qtd > quantProd){
                return "QTD. insuficiente - EAN: $codigoEan - QTD: $qtd - Estoque: $quantProd"
            }
            else{
                return "OK"
            }

        }
    }

    fun insert(codigoMaster:String, tudo:String) {
        codigos[codigoMaster] = tudo
    }

}