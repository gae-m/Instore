package com.example.instore.dress

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.instore.R
import kotlinx.android.synthetic.main.fragment_dress.*
import kotlinx.coroutines.delay
import models.Database
import models.Product

/**
 * A simple [Fragment] subclass.
 */


class DressFragment : Fragment() {

    var product: Product? = null
    var quantita_selz: Int = 1
    var isFind: Boolean = false
    var isAble: Boolean = false
    var quant_disp: Int = 0
    lateinit var taglia: String
    var tagliaArray = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().invalidateOptionsMenu()
        return inflater.inflate(R.layout.fragment_dress, container, false)
    }

    override fun onStop() {
        tagliaArray = arrayListOf<String>()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            val prodotto: Product? = it.getParcelable("prodotto")
            product = prodotto
            prodotto?.let {

                textDescrizione.text = prodotto.descrizione
                textColoreProdotto.text = prodotto.colore
                textNomeProdotto.text = prodotto.nome
                textPrezzoProdotto.text = prodotto.prezzo.toString()
                Glide.with(this).load(prodotto.img[0]).into(imageView2)
                textColore.text = prodotto.colore
                textNome.text = prodotto.nome

                arrayOf("XS","S","M","L","XL").forEach {

                    if(prodotto.quantita_disp[it] != 0){
                        tagliaArray.add(it)
                    }
                }

                //TAGLIA SPINNER
                val tagliaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tagliaArray)
                tagliaSpinner.adapter = tagliaAdapter

                tagliaSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Toast.makeText(requireContext(), "Prodotto esaurito", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        taglia = tagliaArray[position]
                    }
                }

                //TAGLIA SPINNER
                val quantAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayOf(1,2,3,4,5))
                spinnerQuantita.adapter = quantAdapter

                spinnerQuantita.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        quantita_selz = position + 1

                    }

                }

            }
        }


        buttonAggiungi.setOnClickListener {

            Database.productsArray.forEach {
                if(it.id == product?.id) {
                    quant_disp = it.quantita_disp.get(taglia) as Int
                    if (quantita_selz <= it.quantita_disp.get(taglia) as Int) {
                        isAble = true
                    }
                }
            }

            if (isAble) {
                if (Database.cart.isEmpty()) {
                    product.let { p ->
                        var map = mutableMapOf<String, Any?>(
                            "id" to product?.id,
                            "taglia" to taglia,
                            "quantita_selz" to quantita_selz,
                            "quantita_disp" to (product?.quantita_disp?.get(taglia)),
                            "imgUrl" to product?.img?.get(0),
                            "nome" to product?.nome,
                            "prezzo" to product?.prezzo
                        )
                        println("ISEMPTY-------------------")
                        Database.cart.add(map)
                        Toast.makeText(
                            requireContext(),
                            "Prodotto aggiunto al carrello",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Database.cart.forEach { e ->

                        if (e["id"] == product?.id && e["taglia"] == taglia) {
                            println("IDTROVATOOO-----------------------")
                            isFind = true
                            Toast.makeText(
                                requireContext(),
                                "Prodotto gia presente nel carrello",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

                    if (isFind != true) {
                        product.let { p ->
                            var map = mutableMapOf<String, Any?>(
                                "id" to product?.id,
                                "taglia" to taglia,
                                "quantita_selz" to quantita_selz,
                                "quantita_disp" to (product?.quantita_disp?.get(taglia)),
                                "imgUrl" to product?.img?.get(0),
                                "nome" to product?.nome,
                                "prezzo" to product?.prezzo
                            )

                            Database.cart.add(map)
                        }
                        Toast.makeText(
                            requireContext(),
                            "Prodotto aggiunto al carrello",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("InStore")
                builder.setMessage("Quantità selezionata non disponibile.\n Disponibilità :   ${quant_disp.toString()}")
                builder.setPositiveButton("OK") { _, _ ->
                }
                builder.show()
            }
        }
        //Database.cartArray.add(prodottoCart)
    }
}
