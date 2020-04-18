package com.example.instore

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController

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