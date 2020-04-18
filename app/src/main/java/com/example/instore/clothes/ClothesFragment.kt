package com.example.instore.clothes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
    private var categoria: String = ""
    private var spanCount: Int = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_clothes,container,false)

        arguments?.let{
            categoria = ClothesFragmentArgs.fromBundle(it).categoria
            (activity as AppCompatActivity).supportActionBar?.title = categoria
        }

        requireActivity().invalidateOptionsMenu()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSpanCount2.setOnClickListener {
            spanCount = 2
            getFragmentManager()?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        }

        buttonSpanCount1.setOnClickListener {
            spanCount = 1
            getFragmentManager()?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        }

        var tmpProductArray = mutableListOf<Product>()
        gridLayoutManager = GridLayoutManager(requireContext(), spanCount, LinearLayoutManager.VERTICAL, false)
        binding.listaProdotti.layoutManager = gridLayoutManager
        binding.listaProdotti.setHasFixedSize(true)
        when(categoria){
            "Uomo","Donna","Bambino","Bambina"->{
                Database.productsArray.forEach {
                    if (it.categoria == categoria) tmpProductArray.add(it)
                }
            }
            "Risultati"->{
                arguments?.getString("query")?.let {query: String ->
                    Database.productsArray.forEach {
                        if (it.nome.contains(query,true) ||
                            it.descrizione.contains(query,true) ||
                            it.categoria.contains(query,true) ||
                            it.colore.contains(query,true)) tmpProductArray.add(it)
                    }
                }
            }
        }


        adapter = ClothesAdapter(tmpProductArray, requireContext())
        binding.listaProdotti.adapter = adapter

    }

}
