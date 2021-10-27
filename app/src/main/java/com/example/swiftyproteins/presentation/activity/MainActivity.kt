package com.example.swiftyproteins.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.core.view.isVisible
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.ActivityMainBinding
import com.example.swiftyproteins.presentation.fragments.ProteinListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvLoading.isVisible = true
        binding.root.postDelayed({ createRootFragment() }, 1000)
    }

    private fun createRootFragment() {
        binding.tvLoading.isVisible = false

        //TODO for debug
        val fragment = ProteinListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        return super.onCreateOptionsMenu(menu)
    }
}