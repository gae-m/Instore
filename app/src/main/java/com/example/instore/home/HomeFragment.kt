package com.example.instore.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instore.R
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.instore.databinding.FragmentHomeBinding
import models.Database


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home,container,false)
        val adapter = ViewPagerAdapter(this.requireContext(),Database.offerteImg)
        binding.viewPager.adapter = adapter
        navController = findNavController()

        binding.apply {
            uomoTextView.setOnClickListener {
                goToClothes("Uomo")
            }
            donnaTextView.setOnClickListener {
                goToClothes("Donna")
            }
            bambinoTextView.setOnClickListener {
                goToClothes("Bambino")
            }
            bambinaTextView.setOnClickListener {
                goToClothes("Bambina")
            }
        }
        requireActivity().invalidateOptionsMenu()

        return binding.root
    }


    fun goToClothes(categoria: String){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToClothesFragment(categoria))
    }


}