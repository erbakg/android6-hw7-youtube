package com.example.hw6_youtube_app.ui.activities.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.example.hw6_youtube_app.R
import com.example.hw6_youtube_app.data.service.NetworkConnectionService
import com.example.hw6_youtube_app.databinding.ActivityMainBinding
import com.example.hw6_youtube_app.ui.helper.UiHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        internetConnectionChecker()
        initNavigation()
    }

    private fun initNavigation() {
        binding.tvBack.setOnClickListener { navController.navigateUp() }
        navController.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.playlistsFragment -> {
                    binding.toolbar.isVisible = false
                }
                else -> {
                    binding.toolbar.isVisible = true
                }
            }
        }
    }

    private fun internetConnectionChecker() = with(binding) {
        NetworkConnectionService(this@MainActivity)
            .observe(this@MainActivity) { isConnected ->
                UiHelper.changeVisibility(!isConnected, ivNoInternet, btTryAgain)
                UiHelper.changeVisibility(isConnected, navHostFragment)
            }
    }
}