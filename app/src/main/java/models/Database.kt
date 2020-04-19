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



    fun getElencoProdotti(completion: (List<Product>) -> Unit) {
        val docRef = db.collection("negozi").document("00001").collection("prodotti")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    var product =
                        gson.fromJson(JSONObject(document.data).toString(), Product::class.java)
                    productsArray.add(product)

                }
                completion(productsArray)
            }

            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun venduto(id: String, quant_disp: Int, quant_vend: Int, taglia: String) {
        val docRef =
            db.collection("negozi").document("00001").collection("prodotti").document(id)

        docRef
            .update(mapOf("quantita_disp.${taglia}" to quant_disp.toInt() - quant_vend.toInt()))
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }


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