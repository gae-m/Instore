package models

class Product {
    var id: String = ""
    var nome: String = ""
    var s = mapOf("taglia" to "", "quant_disp" to 0)
    var m = mapOf("taglia" to "", "quant_disp" to 0)
    var l = mapOf("taglia" to "", "quant_disp" to 0)
    var prezzo: Double = 0.0
    var colore: String = ""
    var sesso: String = ""
    var anno: Int = 0
    var imgUrl: String = ""

}