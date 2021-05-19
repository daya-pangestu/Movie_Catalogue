package com.daya.moviecatalogue.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.databinding.FragmentContainerBinding
import com.daya.moviecatalogue.ui.main.favorite.movie.MovieFavFragment
import com.daya.moviecatalogue.ui.main.favorite.tvshow.TvShowFavFragment
import com.google.android.material.tabs.TabLayoutMediator

class ContainerFavFragment : Fragment(R.layout.fragment_item_list) {

    private val binding by viewBinding<FragmentContainerBinding>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = MainPagerStateAdapter(childFragmentManager, lifecycle)
        pagerAdapter.addFragments(MovieFavFragment(),TvShowFavFragment())
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab , position ->
            tab.text = when (position) {
                0 -> "Movie Fav"
                else -> "TvShow Fav"
            }
        }.attach()
    }
}