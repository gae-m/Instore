package com.example.instore.dress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.instore.R
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
    var taglia: String = "S"
    var quantita_selz: Int = 1
    var isFind: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dress, container, false)
        requireActivity().invalidateOptionsMenu()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            product = it.getParcelable("prodotto")
            binding.apply {
                textDescrizione.text = product.descrizione
                textColoreProdotto.text = product.colore
                textNomeProdotto.text = product.nome
                textPrezzoProdotto.text = product.prezzo.toString()
                viewPagerProdotto.adapter = ProdottoPagerAdapter(requireContext(),product.img)
            }

        }

        buttonAggiungi.setOnClickListener {

            if (Database.cart.isEmpty()) {
                    val map = mutableMapOf<String, Any?>(
                    "id" to product.id,
                    "taglia" to taglia,
                    "quantita_selz" to quantita_selz,
                    "quantita_disp" to (product.quantita_disp.get("S")),
                    "imgUrl" to product.img.get(0),
                    "nome" to product.nome,
                    "prezzo" to product.prezzo)
                    println("ISEMPTY-------------------")
                    Database.cart.add(map)
            } else {
                Database.cart.forEach { e ->

                    if (e["id"] == product.id) {
                        println("IDTROVATOOO-----------------------")
                        isFind = true
                        e["quantita_selz"] = e["quantita_selz"] as Int + 1
                    }

                }

                if(isFind != true){
                    val map = mutableMapOf<String, Any?>(
                    "id" to product.id,
                    "taglia" to taglia,
                    "quantita_selz" to quantita_selz,
                    "quantita_disp" to (product.quantita_disp.get("S")),
                    "imgUrl" to product.img.get(0),
                    "nome" to product.nome,
                    "prezzo" to product.prezzo)
                    Database.cart.add(map)
                }
            }
            Toast.makeText(requireContext(), "Prodotto aggiunto al carrello", Toast.LENGTH_SHORT).show()
        }
        //Database.cartArray.add(prodottoCart)
    }
}
