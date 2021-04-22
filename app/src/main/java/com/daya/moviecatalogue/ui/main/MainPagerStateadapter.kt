package com.daya.moviecatalogue.ui.main

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.daya.moviecatalogue.ui.main.movie.MovieFragment
import com.daya.moviecatalogue.ui.main.tvshow.TvShowFragment

class MainPagerStateadapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int) =
        when (position) {
            0 -> MovieFragment()
            else -> TvShowFragment()
        }
}