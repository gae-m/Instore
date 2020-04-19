package com.example.instore.dress

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.instore.R
import com.example.instore.databinding.FragmentDressBinding
import kotlinx.android.synthetic.main.fragment_dress.*
import models.Database
import models.Product



class DressFragment : Fragment() {

    private lateinit var binding: FragmentDressBinding
    private lateinit var product: Product
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
        requireActivity().invalidateOptionsMenu()
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dress,container,false)
        return binding.root


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

                binding.textDescrizione.text = prodotto.descrizione
                binding.textColoreProdotto.text = prodotto.colore
                binding.textNomeProdotto.text = prodotto.nome
                binding.textPrezzoProdotto.text = prodotto.prezzo.toString()
                Glide.with(this).load(prodotto.img[0]).into(binding.imageView2)
                binding.textColore.text = prodotto.colore
                binding.textNome.text = prodotto.nome

                arrayOf("XS","S","M","L","XL").forEach {

                    if(prodotto.quantita_disp[it] != 0){
                        tagliaArray.add(it)
                    }
                }

                //TAGLIA SPINNER
                val tagliaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tagliaArray)
                binding.tagliaSpinner.adapter = tagliaAdapter

                binding.tagliaSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Toast.makeText(requireContext(), "Prodotto esaurito", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        taglia = tagliaArray[position]
                    }
                }

                //QUANTITA SPINNER
                val quantAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf(1,2,3,4,5))
                binding.spinnerQuantita.adapter = quantAdapter

                binding.spinnerQuantita.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        quantita_selz = position + 1

                    }

                }

            }
        }

        binding.buttonAggiungi.setOnClickListener {

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
                    product.let {
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
                        Toast.makeText(
                            requireContext(),
                            "Prodotto aggiunto al carrello",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Database.cart.forEach { e ->

                        if (e["id"] == product?.id && e["taglia"] == taglia) {
                            isFind = true
                            Toast.makeText(
                                requireContext(),
                                "Prodotto gia presente nel carrello",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

                    if (isFind != true) {
                        product.let {
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
    }
}
