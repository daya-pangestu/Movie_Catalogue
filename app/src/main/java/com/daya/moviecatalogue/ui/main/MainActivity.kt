package com.daya.moviecatalogue.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager.adapter = MainPagerStateadapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewpager){ tab , position ->
            tab.text = when (position) {
                0 -> "Movie"
                else -> "TvShow"
            }
        }.attach()
    }
}