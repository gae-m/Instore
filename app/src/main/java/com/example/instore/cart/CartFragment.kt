package com.example.instore.cart

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instore.R
import com.example.instore.databinding.FragmentCartBinding
import models.Database

class CartFragment : Fragment() {

    private lateinit var adapter: CartAdpter
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().invalidateOptionsMenu()
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listaCart.layoutManager = LinearLayoutManager(activity)
        adapter = CartAdpter(Database.cart, requireContext())
        binding.listaCart.adapter = adapter

        binding.buttonAcquista.setOnClickListener {

            Database.cart.forEach { cart ->
                Database.productsArray.forEach {
                    if (cart["id"] == it.id) {
                        Database.venduto(
                            it.id,
                            it.quantita_disp.get(cart.get("taglia")) as Int,
                            cart.get("quantita_selz") as Int,
                            cart["taglia"].toString()
                        )
                    }
                }
            }

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("InStore")
            if (Database.cart.isNotEmpty()) {

                builder.setMessage("Grazie per il tuo acquisto.")
                builder.setPositiveButton("OK") { _, _ ->
                    findNavController().popBackStack()

                }
                builder.show()

            } else{

                builder.setMessage("Carrello vuoto. Ritorna agli acquisti.")
                builder.show()

            }

            Database.cart = mutableListOf<MutableMap<String, Any?>>()
            adapter.notifyDataSetChanged()

        }
    }

}
