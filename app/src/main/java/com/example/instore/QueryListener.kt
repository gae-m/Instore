package com.example.instore

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewManager
import androidx.appcompat.widget.ViewUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.findFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.get
import com.example.instore.clothes.ClothesFragment

class QueryListener(val menuItem: MenuItem,val navController: NavController): androidx.appcompat.widget.SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            menuItem.collapseActionView()
            val bundle = Bundle()
            bundle.putString("categoria","Risultati")
            bundle.putString("query",query)
            navController.navigate(R.id.clothesFragment,bundle)
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}