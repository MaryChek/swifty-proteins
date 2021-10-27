package com.example.swiftyproteins.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.core.view.isVisible
import com.example.swiftyproteins.R
import com.example.swiftyproteins.databinding.ActivityMainBinding
import com.example.swiftyproteins.presentation.fragments.ProteinListFragment
import com.example.swiftyproteins.presentation.fragments.base.BaseFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val currentFragment: BaseFragment<*, *>?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? BaseFragment<*, *>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvLoading.isVisible = true
        binding.root.postDelayed({ createRootFragment() }, 1000)
    }

    override fun onBackPressed() {
        when {
            currentFragment?.onBackPressed() == true -> Unit
            else -> super.onBackPressed()
        }
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