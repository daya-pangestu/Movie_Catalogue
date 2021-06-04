package com.daya.moviecatalogue.ui.main.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.databinding.FragmentItemListBinding
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.main.MovieRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MovieFavFragment : Fragment(R.layout.fragment_item_list) {
    private val binding by viewBinding<FragmentItemListBinding>()

    private val viewModel by viewModels<MovieFavViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerFavoriteMovies()
    }

    private fun observerFavoriteMovies() {
        val adapter = MovieRecyclerViewAdapter() {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.DETAIL_EXTRA_MOVIE, it.id)
            }
            startActivity(intent)
        }
        binding.rvList.adapter = adapter
        lifecycleScope.launch {
            viewModel.favoriteMovies.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}