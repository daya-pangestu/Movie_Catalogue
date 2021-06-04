package com.daya.moviecatalogue.ui.main.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.databinding.FragmentItemListBinding
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.main.TvShowRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TvShowFavFragment : Fragment(R.layout.fragment_item_list) {
    private val binding by viewBinding<FragmentItemListBinding>()

    private val viewModel by viewModels<TvShowFavViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerFavoriteTvShow()
    }

    private fun observerFavoriteTvShow() {
        val adapter = TvShowRecyclerViewAdapter {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.DETAIL_EXTRA_TV_SHOW, it.id)
            }
            startActivity(intent)
        }
        binding.rvList.adapter = adapter
        lifecycleScope.launch {
            viewModel.favoriteTvShows.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}