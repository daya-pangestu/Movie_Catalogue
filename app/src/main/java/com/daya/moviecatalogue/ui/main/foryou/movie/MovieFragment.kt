package com.daya.moviecatalogue.ui.main.foryou.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.databinding.FragmentItemListBinding
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_MOVIE
import com.daya.moviecatalogue.ui.main.LoadingStateAdapter
import com.daya.moviecatalogue.ui.main.MovieRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_item_list) {

    val mainViewModel by viewModels<MovieViewModel>()

    private val binding by viewBinding<FragmentItemListBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            with(rvList) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
        observerMovies()
    }

    private fun observerMovies() {
        val adapter = MovieRecyclerViewAdapter {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(DETAIL_EXTRA_MOVIE, it.id)
            }
            startActivity(intent)
        }
        binding.rvList.adapter =adapter
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.discoverMovie.collectLatest {
                adapter.submitData(it)
            }
            adapter.withLoadStateFooter(LoadingStateAdapter())
        }
    }
}