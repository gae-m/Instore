package com.example.instore.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instore.R
import kotlinx.android.synthetic.main.fragment_cart.*
import models.Database

/**
 * A simple [Fragment] subclass.
 */

class CartFragment : Fragment() {
    private lateinit var adapter: CartAdpter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listaCart.layoutManager = LinearLayoutManager(activity)
        adapter = CartAdpter(Database.cart, requireContext())
        listaCart.adapter = adapter

        buttonAcquista.setOnClickListener {
            Database.cart.forEach {cart ->
                Database.productsArray.forEach {
                    if(cart["id"] == it.id){
                        Database.venduto(it.id, it.quantita_disp["S"].toString() , cart["quantita_selz"].toString() )
                    }
                }
                }
            }
        }

}

