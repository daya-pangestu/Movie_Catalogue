package com.daya.moviecatalogue.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    val forYouFragment = ContainerForYouFragment()
    val favoriteFragment = ContainerFavFragment()
    var activeFragment : Fragment = forYouFragment
    val fm = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbarTitle("For You")

        fm.beginTransaction()
            .add(R.id.main_frame_layout, favoriteFragment, "favorite")
            .hide(favoriteFragment)
            .commit()

        fm.beginTransaction()
            .add(R.id.main_frame_layout,forYouFragment,"foryou")
            .commit()

        binding.bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    val onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener{
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.main_action_for_you -> {
                    fm.beginTransaction().hide(activeFragment).show(forYouFragment).commit();
                    activeFragment = forYouFragment
                    setToolbarTitle("For You")
                    return true
                }
                R.id.main_action_favorite -> {
                    fm.beginTransaction().hide(activeFragment).show(favoriteFragment).commit();
                    activeFragment = favoriteFragment
                    setToolbarTitle("Favorite")
                    return true
                }
            }
            return false
        }
    }

    private fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

}