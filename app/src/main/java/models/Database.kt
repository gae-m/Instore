package models

import android.util.Log
import com.example.instore.cart.CartAdpter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject

object Database {

    private var db = Firebase.firestore
    private var TAG = "instore"
    private var gson = Gson()
//    var offerteImg = mutableListOf<String>()
        get() = field
        private set(value) {
            field = value
        }
    var productsArray = mutableListOf<Product>()
    var cart = mutableListOf<MutableMap<String, Any?>>()


    fun venduto(id: String, quant_disp: String, quant_vend: String) {
        val docRef =
            db.collection("negozi").document("00001").collection("prodotti").document(id)

        docRef
            .update(mapOf("quantita_disp.${taglia}" to quant_disp.toInt() - quant_vend.toInt()))
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

//    fun loadOfferte(completion: (List<String>) -> Unit){
//        val docRef = db.collection("negozi").document("00001").collection("offerte")
//        docRef.addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            if (snapshot != null) {
//                offerteImg = mutableListOf()
//                snapshot.documents.forEach { d ->
////                    offerteImg.add(gson.fromJson(JSONObject(d.data).toString(), String::class.java))
//                    d.data?.let{
//                        offerteImg.add(it.get("imgUrl") as String)
//                        println(it.get("imgUrl") as String)
//                    }
//                }
//                completion(offerteImg)
//            } else {
//                Log.d(TAG, "Current data: null")
//            }
//        }
//    }

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
                    productsArray.add(gson.fromJson(JSONObject(d.data).toString(), Product::class.java))
                }
                completion(productsArray)
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }
}