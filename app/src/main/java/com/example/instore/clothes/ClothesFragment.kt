package com.example.instore.clothes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.instore.R
import com.example.instore.databinding.FragmentClothesBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_clothes.*
import models.Database

/**
 * A simple [Fragment] subclass.
 */
class ClothesFragment : Fragment() {

    private lateinit var adapter: ClothesAdapter
    private var gridLayoutManager:GridLayoutManager? = null
    private lateinit var binding: FragmentClothesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_clothes,container,false)
        arguments?.let{
            (activity as AppCompatActivity).supportActionBar?.title = ClothesFragmentArgs.fromBundle(it).categoria
        }
//        setHasOptionsMenu(true)
        requireActivity().invalidateOptionsMenu()
        return binding.root
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
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.cart_menu,menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.cartItem -> findNavController().navigate(R.id.cartFragment)
//        }
//        return super.onOptionsItemSelected(item)
//    }
}
