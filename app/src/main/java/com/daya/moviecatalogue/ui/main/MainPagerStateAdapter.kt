package com.daya.moviecatalogue.ui.main

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.daya.moviecatalogue.ui.main.foryou.movie.MovieFragment
import com.daya.moviecatalogue.ui.main.foryou.tvshow.TvShowFragment

class MainPagerStateAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int) =
        when (position) {
            0 -> MovieFragment()
            else -> TvShowFragment()
        }
}