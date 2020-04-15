package com.example.instore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import models.Database
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
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
        Database.getElencoProdotti {
            /*list.forEach { element->
                println(element.id)*/
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

    fun goToClothes(categoria: String): Boolean{
        drawerLayout.closeDrawer(binding.navView)
        val bundle = Bundle()
        bundle.putString("categoria",categoria)
        if (navController.currentDestination?.id!=R.id.clothesFragment||(navController.currentDestination?.label as String) !=categoria){
            navController.navigate(R.id.clothesFragment,bundle)
            return true
        }
        return false
    }



    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || NavigationUI.navigateUp(navController,drawerLayout)
    }

    override fun onBackPressed() {
        drawerLayout.closeDrawer(binding.navView)
        super.onBackPressed()
    }
}
