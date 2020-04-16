package models

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONObject

object Database {

        private var db = Firebase.firestore
        private var TAG = "instore"
        private var gson = Gson()
        var productsArray = mutableListOf<Product>()

    fun getElencoProdotti(){
        db.collection("negozi").document("00001").collection("prodotti")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    //println(document.id)

                    var product = gson.fromJson(JSONObject(document.data).toString(), Product::class.java)
                    productsArray.add(product)

                    //var productToUpload = gson.toJson(product)
                    //println(product.taglia)
                    //println(product.nome)
                    //Log.d(TAG, "${document.id} => ${document.data}")
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }


    fun getProdotto(id: String) : OneProduct{
        var product = OneProduct()
        val docRef = db.collection("negozi").document("00001").collection("prodotti").document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                   product = gson.fromJson(JSONObject(document.data).toString(), OneProduct::class.java)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
        return product
    }

    fun venduto(id: String, quant_disp: Int){
        val washingtonRef = db.collection("negozi").document("00001").collection("prodotti").document(id)

// Set the "isCapital" field of the city 'DC'
        washingtonRef
            .update("quant_disp", quant_disp - 1)
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


    /*AVVISO CAMBIAMENTO REALTIME DI UN CAMPO DI UN RECORD
     val docRef = db.collection("products").document("PMql4plt8sA1YXiq1DoW")
     docRef.addSnapshotListener { snapshot, e ->
         if (e != null) {
             Log.w(TAG, "Listen failed.", e)
             return@addSnapshotListener
         }

         if (snapshot != null && snapshot.exists()) {
             Log.d(TAG, "Current data: ${snapshot.data}")
             product = gson.fromJson(JSONObject(snapshot.data).toString(), Product::class.java)
             println(product?.prezzo)
             println(product?.name)

         } else {
             Log.d(TAG, "Current data: null")
         }
     }*/
}