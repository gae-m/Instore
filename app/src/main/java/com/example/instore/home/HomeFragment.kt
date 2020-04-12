package com.example.instore.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
            R.drawable.offerta1,
            R.drawable.offerta2,
            R.drawable.offerta3
        )
        //val offerteViewPager = MyViewPager(this.requireContext())
        val adapter = OffertePagerAdapter(this.requireContext(), imageOfferte)
        binding.viewPager.adapter = adapter


        return binding.root
    }


}
