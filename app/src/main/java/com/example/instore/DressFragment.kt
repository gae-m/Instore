package com.example.instore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.instore.databinding.FragmentDressBinding
import kotlinx.android.synthetic.main.fragment_dress.*
import models.Database
import models.Product

/**
 * A simple [Fragment] subclass.
 */


class DressFragment : Fragment() {
    private lateinit var binding: FragmentDressBinding
    private lateinit var product: Product
    private var taglia: String = "S"
    private var quantita_selz: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dress, container, false)
        requireActivity().invalidateOptionsMenu()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            product = it.getParcelable("prodotto")
            binding.textDescrizione.text = product.descrizione
            binding.textColoreProdotto.text = product.colore
            binding.textNomeProdotto.text = product.nome
            binding.textPrezzoProdotto.text = product.prezzo.toString()
            Glide.with(this).load(product.imgUrl).into(binding.imageView2)
        }

        binding.buttonAggiungi.setOnClickListener {
            var isFind = false

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
