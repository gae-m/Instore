package com.example.instore.cart

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instore.R
import com.example.instore.databinding.FragmentCartBinding
import com.google.android.gms.common.util.DataUtils
import io.opencensus.internal.StringUtils
import kotlinx.android.synthetic.main.fragment_cart.*
import models.Database
import java.text.DecimalFormat
import kotlin.text.Typography.euro

class CartFragment : Fragment() {

    private lateinit var adapter: CartAdpter
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().invalidateOptionsMenu()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listaCart.layoutManager = LinearLayoutManager(activity)
        adapter = CartAdpter(Database.cart, requireContext(),this)
        binding.listaCart.adapter = adapter
        aggiornaLayout()





        binding.buttonAcquista.setOnClickListener {

            Database.cart.forEach { cart ->
                Database.productsArray.forEach {
                    if (cart.id == it.id) {
                        Database.venduto(
                            it.id,
                            it.quantita_disp.get(cart.taglia) as Int,
                            cart.quantita_selz,
                            cart.taglia
                        )
                    }
                }
            }

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("InStore")
            builder.setMessage("Grazie per il tuo acquisto.")
            builder.setPositiveButton("OK") { _, _ ->
                findNavController().popBackStack(R.id.homeFragment,false)

            }
            builder.show()

            Database.cart.clear()
            adapter.notifyDataSetChanged()

        }
    }


    fun aggiornaLayout() {
        binding.apply {
            if (Database.cart.isEmpty()) {
                textTotaleLabel.visibility = View.INVISIBLE
                textTotaleVar.visibility = View.INVISIBLE
                buttonAcquista.visibility = View.INVISIBLE
                textCarrelloVuoto.visibility = View.VISIBLE
            } else {
                textCarrelloVuoto.visibility = View.INVISIBLE
                var somma: Double = 0.0
                Database.cart.forEach {
                    somma = somma + (it.prezzo)
                }
                val text = DecimalFormat("#,##.00").format(somma) + euro
                textTotaleVar.text = text
            }
            textTotaleVar.invalidate()
            textTotaleLabel.invalidate()
            buttonAcquista.invalidate()
            textCarrelloVuoto.invalidate()
        }
    }
}

