package com.example.instore.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instore.R
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.instore.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController


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
        navController = findNavController()

        binding.apply {
            uomoImageView.setOnClickListener {
                goToClothes("Uomo")
            }
            donnaImageView.setOnClickListener {
                goToClothes("Donna")
            }
            bambinoImageView.setOnClickListener {
                goToClothes("Bambino")
            }
            bambinaImageView.setOnClickListener {
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