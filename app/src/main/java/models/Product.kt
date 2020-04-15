package models

class Product {
    var id: String = ""
    var nome: String = ""
    var s: Map<String,String> = mapOf<String,String>("taglia" to "", "quant_disp" to "")
    var m: Map<String,String> = mapOf<String,String>("taglia" to "", "quant_disp" to "")
    var l: Map<String,String> = mapOf<String,String>("taglia" to "", "quant_disp" to "")
    var prezzo: Double = 0.0
    var colore: String = ""
    var sesso: String = ""
    var anno: Int = 0
    var imgUrl: String = ""

}