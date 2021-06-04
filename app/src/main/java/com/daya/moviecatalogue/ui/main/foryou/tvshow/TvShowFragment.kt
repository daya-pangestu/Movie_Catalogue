package com.daya.moviecatalogue.ui.main.foryou.tvshow

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
import com.daya.moviecatalogue.ui.main.TvShowRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TvShowFragment : Fragment(R.layout.fragment_item_list) {

    private val mainViewModel by viewModels<TvShowViewModel>()

    val binding: FragmentItemListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            with(rvList) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }
        tvShowObserver()
    }

    private fun tvShowObserver() {
        val adapter = TvShowRecyclerViewAdapter {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.DETAIL_EXTRA_TV_SHOW, it.id)
            }
            startActivity(intent)
        }
        binding.rvList.adapter = adapter
        lifecycleScope.launch {
            mainViewModel.discoverTvShow.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}