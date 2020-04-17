package com.example.instore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.instore.cart.CartAdpter
import kotlinx.android.synthetic.main.fragment_dress.*
import models.Database
import models.OnceProduct
import models.Product

/**
 * A simple [Fragment] subclass.
 */


class DressFragment : Fragment() {

    var product: Product? = null
    var taglia: String = "S"
    var quantita_selz: Int = 1
    var isFind: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val prodotto: Product? = it.getParcelable("prodotto")   //TODO: Il nome dovrebbe essere in un unico punto!!
            product = prodotto
            prodotto?.let {

                textDescrizione.text = prodotto.descrizione
                textColoreProdotto.text = prodotto.colore
                textNomeProdotto.text = prodotto.nome
                textPrezzoProdotto.text = prodotto.prezzo.toString()
                Glide.with(this).load(prodotto.imgUrl).into(imageView2)

            }
        }

        buttonAggiungi.setOnClickListener {
            if (Database.cart.isEmpty()) {
                product.let { p ->
                    var map = mutableMapOf<String, Any?>(
                        "id" to product?.id,
                        "taglia" to taglia,
                        "quantita_selz" to quantita_selz,
                        "quantita_disp" to (product?.quantita_disp?.get("S")),
                        "imgUrl" to product?.imgUrl,
                        "nome" to product?.nome,
                        "prezzo" to product?.prezzo)
                        println("ISEMPTY-------------------")
                        Database.cart.add(map)
                }
            } else {
                Database.cart.forEach { e ->

                    if (e["id"] == product?.id) {
                        println("IDTROVATOOO-----------------------")
                        isFind = true
                        e["quantita_selz"] = e["quantita_selz"] as Int + 1
                    }

                }

                if(isFind != true){
                    product.let { p ->
                        var map = mutableMapOf<String, Any?>(
                            "id" to product?.id,
                            "taglia" to taglia,
                            "quantita_selz" to quantita_selz,
                            "quantita_disp" to (product?.quantita_disp?.get("S")),
                            "imgUrl" to product?.imgUrl,
                            "nome" to product?.nome,
                            "prezzo" to product?.prezzo)

                        Database.cart.add(map)
                        }

                }
            }

        }
            //Database.cartArray.add(prodottoCart)
    }
}
