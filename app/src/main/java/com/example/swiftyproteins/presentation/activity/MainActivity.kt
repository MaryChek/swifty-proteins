package com.example.swiftyproteins.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initNavigation()
    }

//    private fun initNavigation() {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        setupActionBarWithNavController(navController)
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp()
//                || super.onSupportNavigateUp()
//    }
}