package com.example.instore.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.instore.R
import com.example.instore.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home,container,false)
        val imageOfferte = listOf<Int>(
            R.drawable.offerta4,
            R.drawable.offerta1,
            R.drawable.offerta2
        )
        val adapter = OffertePagerAdapter(this.requireContext(), imageOfferte)
        binding.viewPager.adapter = adapter

        binding.apply {
            uomoImageView.setOnClickListener {
                goToClothes(it.contentDescription as String)
            }
            donnaImageView.setOnClickListener {
                goToClothes(it.contentDescription as String)
            }
            bambinoImageView.setOnClickListener {
                goToClothes(it.contentDescription as String)
            }
            nuoviArriviImageView.setOnClickListener {
                goToClothes(it.contentDescription as String)
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.cart_menu,menu)
    }

    fun goToClothes(categoria: String){
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToClothesFragment(categoria))
    }


}
