package com.example.instore

import android.util.Log

class QueryListener: androidx.appcompat.widget.SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.i("Search","QuerySubmitted")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.i("Search",newText)
        return true
    }

}