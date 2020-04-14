package com.example.instore.clothes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.instore.R
import com.example.instore.databinding.FragmentClothesBinding

/**
 * A simple [Fragment] subclass.
 */
class ClothesFragment : Fragment() {
    private lateinit var binding: FragmentClothesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_clothes,container,false)
        arguments?.let{
            binding.textView.text = ClothesFragmentArgs.fromBundle(it).categoria
            findNavController().graph.label = ClothesFragmentArgs.fromBundle(it).categoria
            (activity as AppCompatActivity).supportActionBar?.title = findNavController().graph.label
        }

        return binding.root
    }


}
