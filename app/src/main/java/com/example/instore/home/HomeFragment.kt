package com.example.instore.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.instore.R
import com.example.instore.databinding.FragmentHomeBinding


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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item) || NavigationUI.onNavDestinationSelected(item,navController)
    }


    fun goToClothes(categoria: String){
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToClothesFragment(categoria))
    }


}
