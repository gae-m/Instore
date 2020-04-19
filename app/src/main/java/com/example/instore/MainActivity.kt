package com.example.instore

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import models.Database
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.*
import androidx.navigation.ui.*
import com.example.instore.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

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
            goToClothes(it.title as String)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        when(navController.currentDestination?.id){
            R.id.homeFragment,R.id.clothesFragment -> {
                menuInflater.inflate(R.menu.cart_menu,menu)
                val searchItem = menu?.findItem(R.id.searchItem)
                (searchItem?.actionView as SearchView).apply {
                    setOnQueryTextListener(QueryListener(searchItem,navController))
                    setOnQueryTextFocusChangeListener { v, hasFocus ->
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
            }
        }
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cartItem -> navController.navigate(R.id.cartFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    fun goToClothes(categoria: String): Boolean{
        drawerLayout.closeDrawer(binding.navView)
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

}
