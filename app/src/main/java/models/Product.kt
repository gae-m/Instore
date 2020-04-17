package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product : Parcelable {
    var id: String = ""
    var nome: String = ""
    var quantita_disp = mapOf<String, Int>("S" to 0, "M" to 0, "L" to 0 )
    var prezzo: Double = 0.0
    var colore: String = ""
    var categoria: String = ""
    var imgUrl: String = ""
    var nuovi_arrivi: Boolean = false
    var descrizione: String = ""

}