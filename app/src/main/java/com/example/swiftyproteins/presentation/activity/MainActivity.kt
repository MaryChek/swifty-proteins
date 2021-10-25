package com.example.swiftyproteins.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.ActivityMainBinding
import com.example.swiftyproteins.presentation.fragments.ProteinListFragment
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO for debug
        binding.root.postDelayed({ createRootFragment() }, 5000)

    }

    private fun createRootFragment() {
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