package com.daya.moviecatalogue.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.daya.moviecatalogue.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = MainPagerStateAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab , position ->
            tab.text = when (position) {
                0 -> "Movie"
                else -> "TvShow"
            }
        }.attach()
    }
}