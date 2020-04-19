package com.example.instore.filtro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.instore.R
import com.example.instore.databinding.FragmentFiltroBinding

/**
 * A simple [Fragment] subclass.
 */
class FiltroFragment : Fragment() {
    private lateinit var binding: FragmentFiltroBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_filtro, container, false)
//        binding.filtriExpandableList.expandableListAdapter = FiltriListAdapter()
        return binding.root
    }

}
