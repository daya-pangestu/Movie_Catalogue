package com.daya.moviecatalogue.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    val forYouFragment = ContainerForYouFragment()
    val favoriteFragment = ContainerFavFragment()
    var activeFragment : Fragment = forYouFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.main_frame_layout, favoriteFragment, "favorite")
            .hide(favoriteFragment)
            .commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.main_frame_layout,forYouFragment,"foryou")
            .commit()

        binding.bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.main_action_for_you -> {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(activeFragment)
                        .show(forYouFragment)
                    activeFragment = forYouFragment
                }
                R.id.main_action_favorite -> {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(activeFragment)
                        .show(favoriteFragment)
                    activeFragment = favoriteFragment
                }
            }
        }
    }

}