package com.example.instore

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import models.Database
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.*
import androidx.navigation.ui.*
import com.example.instore.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var qrScanIntegrator: IntentIntegrator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Database.loadProducts {
        }

        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.navHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
        binding.navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(binding.navView)
            when (it.itemId) {
                R.id.homeItem -> navController.popBackStack(R.id.homeFragment, false)
                else -> goToClothes(it.title as String)
            }
        }

        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator.setBeepEnabled(false)
        qrScanIntegrator.setPrompt("Inquadra il QRCode dell'articolo")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        when(navController.currentDestination?.id){
            R.id.homeFragment,R.id.clothesFragment -> {
                menuInflater.inflate(R.menu.cart_menu,menu)
                val searchItem = menu?.findItem(R.id.searchItem)
                (searchItem?.actionView as SearchView).apply {
                    setOnQueryTextListener(QueryListener(searchItem,navController))
                    setOnQueryTextFocusChangeListener { _, hasFocus ->
                        if(hasFocus) binding.searchBackground.visibility = View.VISIBLE
                        else{
                            searchItem.collapseActionView()
                            binding.searchBackground.visibility = View.GONE
                        }
                    }
                }

            }
            R.id.dressFragment->{
                menuInflater.inflate(R.menu.cart_menu,menu)
                menu?.findItem(R.id.searchItem)?.apply {
                    setVisible(false)
                    setEnabled(false)
                }
                menu?.findItem(R.id.scanItem)?.apply {
                    setVisible(false)
                    setEnabled(false)
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.scanItem -> scan()
            R.id.cartItem -> navController.navigate(R.id.cartFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    fun goToClothes(categoria: String): Boolean{
        val bundle = Bundle()
        bundle.putString("categoria",categoria)
        navController.navigate(R.id.clothesFragment,bundle, NavOptions.Builder().setPopUpTo(R.id.homeFragment,false).build())
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.closeDrawer(binding.navView)
        return super.onSupportNavigateUp() || navController.navigateUp(drawerLayout)
    }

    override fun onBackPressed() {
        drawerLayout.closeDrawer(binding.navView)
        super.onBackPressed()
    }

    private fun scan() {
        qrScanIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            // If QRCode has no data.
            if(result.contents != null){
                // If QRCode contains data.
                val bundle = Bundle()
                bundle.putString("categoria", "Risultati")
                bundle.putString("query", result.contents)
                navController.navigate(R.id.clothesFragment, bundle)
            }
        }
    }

}
