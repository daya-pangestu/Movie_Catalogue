package com.daya.moviecatalogue.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.databinding.FragmentContainerBinding
import com.daya.moviecatalogue.ui.main.MainPagerStateAdapter
import com.daya.moviecatalogue.ui.main.foryou.movie.MovieFragment
import com.daya.moviecatalogue.ui.main.foryou.tvshow.TvShowFragment
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber

class ContainerForYouFragment : Fragment(R.layout.fragment_container) {

    private val binding by viewBinding<FragmentContainerBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("container for you ")

        val pagerAdapter = MainPagerStateAdapter(childFragmentManager, lifecycle)
        pagerAdapter.addFragments(MovieFragment(),TvShowFragment())
        binding.viewPager.adapter = pagerAdapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager){ tab , position ->
            tab.text = when (position) {
                0 -> "Movie"
                else -> "TvShow"
            }
        }.attach()
    }
}