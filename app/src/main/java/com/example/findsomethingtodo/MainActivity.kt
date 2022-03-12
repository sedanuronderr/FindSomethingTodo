package com.example.findsomethingtodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.findsomethingtodo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding
    private lateinit var mNavController :NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavBottom()
    }


    private  fun setupNavBottom(){
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment
       mNavController = navHostFragment.navController
        binding.bottomNavigationView2.setupWithNavController(mNavController)

        val appConfigBar = AppBarConfiguration(setOf(R.id.homeFragment))
        setupActionBarWithNavController(mNavController,appConfigBar)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(mNavController,null)
    }
}