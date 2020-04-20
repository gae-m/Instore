package com.example.instore.dress

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.instore.R
import com.example.instore.databinding.FragmentDressBinding
import com.example.instore.home.MyOnPageChangeListner
import kotlinx.android.synthetic.main.fragment_dress.*
import models.Database
import models.OneProduct
import models.Product
import java.lang.Integer.min
import kotlin.text.Typography.euro


class DressFragment : Fragment() {

    private lateinit var binding: FragmentDressBinding
    private lateinit var product: Product
    private var quantita_selz: Int = 1
    private var quant_disp: Int = 0
    private lateinit var taglia: String
    private var tagliaArray = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().invalidateOptionsMenu()
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dress,container,false)
        return binding.root
    }

    override fun onStop() {
        tagliaArray.clear()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            var imageViewArray : Array<ImageView>
            product = it.getParcelable("prodotto")!!
            binding.apply {
                textDescrizione.text = product.descrizione
                textColoreProdotto.text = product.colore
                textNomeProdotto.text = product.nome
                textPrezzo.text = product.prezzo.toString()+" "+euro
                textNome.text = product.nome
                textCodice.text = product.cod
                viewPagerProdotto.adapter = ProdottoPagerAdapter(requireContext(),product.img)
                imageViewArray = arrayOf(imageViewProdotto1,imageViewProdotto2,imageViewProdotto3)
            }
            for (i in 0..kotlin.math.min(product.img.size,imageViewArray.size) -1){
                Glide.with(requireContext()).load(product.img[i]).into(imageViewArray[i])
            }
            binding.viewPagerProdotto.addOnPageChangeListener(MyOnPageChangeListner(binding.swipeLeftImageView,binding.swipeRightImageView,Database.offerteImg.lastIndex))



                arrayOf("XS","S","M","L","XL").forEach {

                    if(product.quantita_disp[it] != 0){
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

        binding.buttonAggiungi.setOnClickListener {

            var isFind: Boolean = false
            var isAble: Boolean = false
            var isAbleToAdd: Boolean = false

            Database.productsArray.forEach {
                if(it.id == product.id) {
                    quant_disp = it.quantita_disp.get(taglia) as Int
                    if (quantita_selz <= it.quantita_disp.get(taglia) as Int) {
                        isAble = true
                    }
                }
            }

            if (isAble) {
                if (Database.cart.isEmpty()) {
                    product.let {
                        val tmpCart = OneProduct()
                        tmpCart.id = product.id
                        tmpCart.taglia = taglia
                        tmpCart.quantita_selz = quantita_selz
                        tmpCart.quantita_disp = (product.quantita_disp.get(taglia)!!)
                        tmpCart.imgUrl = product.img.get(0)
                        tmpCart.nome = product.nome
                        tmpCart.prezzo = product.prezzo
                        tmpCart.cod = product.cod

                        Database.cart.add(tmpCart)
                        Toast.makeText(
                            requireContext(),
                            "Prodotto aggiunto al carrello",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Database.cart.forEach { e ->

                        if (e.id == product.id && e.taglia == taglia) {
                            isFind = true
                            Database.productsArray.forEach {
                                if (e.id == it.id) {
                                    quant_disp = it.quantita_disp.get(taglia) as Int
                                }
                            }

                            if (quantita_selz + e.quantita_selz <= quant_disp) {
                                e.quantita_selz += quantita_selz
                                Toast.makeText(
                                    requireContext(),
                                    "Prodotto già presente nel carrello. Quantità aggiunta: $quantita_selz",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val builder = AlertDialog.Builder(requireContext())
                                builder.setTitle("InStore")
                                builder.setMessage("Quantità aggiunta non disponibile.\nControlla il tuo carrello.\nDisponibilità totale:   $quant_disp")
                                builder.setPositiveButton("OK") { _, _ ->
                                }
                                builder.show()
                            }                            }
                        }

                        if (isAbleToAdd) {
                        }

                        if (isFind != true) {
                            product.let {
                                val tmpCart = OneProduct()
                                tmpCart.id = product.id
                                tmpCart.taglia = taglia
                                tmpCart.quantita_selz = quantita_selz
                                tmpCart.quantita_disp = (product.quantita_disp.get(taglia)!!)
                                tmpCart.imgUrl = product.img.get(0)
                                tmpCart.nome = product.nome
                                tmpCart.prezzo = product.prezzo
                                tmpCart.cod = product.cod

                                Database.cart.add(tmpCart)
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
                builder.setMessage("Quantità selezionata non disponibile.\n Disponibilità :   $quant_disp")
                builder.setPositiveButton("OK") { _, _ ->
                }
                builder.show()
            }
        }
    }
}
