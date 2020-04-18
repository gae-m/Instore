package models

import android.util.Log
import com.example.instore.cart.CartAdpter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONObject

object Database {

    private var db = Firebase.firestore
    private var TAG = "instore"
    private var gson = Gson()
    var productsArray = mutableListOf<Product>()
    var cart = mutableListOf<MutableMap<String, Any?>>()


    fun venduto(id: String, quant_disp: String, quant_vend: String) {
        val docRef =
            db.collection("negozi").document("00001").collection("prodotti").document(id)

        docRef
            .update(mapOf("quantita_disp.S" to quant_disp.toInt() - quant_vend.toInt()))
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }


    /*CAMBIARE UNO O PIU CAMPI NEL DATABASE DI FIREBASE (GSON) CLICCANDO SU UN PULSANTE
prova_button.setOnClickListener {
    product?.prezzo = 5.0
    product?.name = "bel pantalone"

    val productRef = db.collection("products").document("PMql4plt8sA1YXiq1DoW")
    var productToUpload = gson.toJson(product)
    val retMap: Map<String, Any> = Gson().fromJson(
        productToUpload, object : TypeToken<HashMap<String?, Any?>?>() {}.type
    )


    productRef
        //UTILE PER CAMBIARE UN UNICO CAMPO SENZA UTILIZZARE toJson(product) CHE PRENDE TUTTO IL RECORD
        //.update("prezzo", 3.0)
        .update(retMap)
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

}*/

    fun loadProducts(completion: (List<Product>) -> Unit) {
        val docRef = db.collection("negozi").document("00001").collection("prodotti")
    docRef.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Log.w(TAG, "Listen failed.", e)
            return@addSnapshotListener
        }

        if (snapshot != null) {
            productsArray = mutableListOf()
            snapshot.documents.forEach { d ->
                var product = gson.fromJson(JSONObject(d.data).toString(), Product::class.java)
                productsArray.add(product)
            }
            completion(productsArray)
        } else {
            Log.d(TAG, "Current data: null")
        }
    }
}
}