package models

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
class Product : Parcelable {
    @IgnoredOnParcel
    var id: String = ""
    @IgnoredOnParcel
    var nome: String = ""
    @IgnoredOnParcel
    var quantita_disp = mapOf<String, Int>("S" to 0, "M" to 0, "L" to 0 )
    @IgnoredOnParcel
    var prezzo: Double = 0.0
    @IgnoredOnParcel
    var colore: String = ""
    @IgnoredOnParcel
    var categoria: String = ""
    @IgnoredOnParcel
    var img = arrayOf("", "", "", "")
    @IgnoredOnParcel
    var nuovi_arrivi: Boolean = false
    @IgnoredOnParcel
    var descrizione: String = ""

}