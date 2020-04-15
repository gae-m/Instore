package com.example.instore.clothes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instore.R
import kotlinx.android.synthetic.main.fragment_clothes.*
import models.Database

/**
 * A simple [Fragment] subclass.
 */
class ClothesFragment : Fragment() {

    private lateinit var adapter: ClothesAdapter
    private var gridLayoutManager:GridLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clothes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridLayoutManager = GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false)
        listaProdotti.layoutManager = gridLayoutManager
        listaProdotti.setHasFixedSize(true)
        adapter = ClothesAdapter(
            Database.productsArray,
            requireContext()
        )
        listaProdotti.adapter = adapter

    }
}
