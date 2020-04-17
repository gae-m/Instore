package com.example.instore.clothes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.instore.R
import com.example.instore.databinding.FragmentClothesBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_clothes.*
import models.Database
import models.Product


class ClothesFragment : Fragment() {

    private lateinit var adapter: ClothesAdapter
    private var gridLayoutManager:GridLayoutManager? = null
    private lateinit var binding: FragmentClothesBinding
    var categoria: String = ""
    var nuovi_arrivi: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_clothes,container,false)

        arguments?.let{
            categoria = ClothesFragmentArgs.fromBundle(it).categoria
            (activity as AppCompatActivity).supportActionBar?.title = categoria
        }
        arguments?.let{
            nuovi_arrivi = ClothesFragmentArgs.fromBundle(it).nuoviArrivi
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var tmpProductArray = mutableListOf<Product>()
        gridLayoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        listaProdotti.layoutManager = gridLayoutManager
        listaProdotti.setHasFixedSize(true)

        Database.productsArray.forEach {

            if (nuovi_arrivi) {
                if (it.nuovi_arrivi == nuovi_arrivi) {
                    tmpProductArray.add(it)
                }
            } else {
                if (it.categoria == categoria) {
                    tmpProductArray.add(it)
                }
            }
        }

        adapter = ClothesAdapter(tmpProductArray, requireContext())
        listaProdotti.adapter = adapter

    }
}
