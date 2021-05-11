package com.daya.moviecatalogue.ui.main.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.databinding.FragmentItemListBinding
import com.daya.moviecatalogue.di.idlingresource.DebugIdlingResource
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TvShowFragment : Fragment(R.layout.fragment_item_list) {

    private val mainViewModel by activityViewModels<MainViewModel>()

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

    fun tvShowObserver() {
        DebugIdlingResource.increment()
        mainViewModel.discoverTvShow.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Timber.i(" observerMovie loading")
                    binding.progressCircular.isVisible = true
                }
                is Resource.Success -> {
                    DebugIdlingResource.decrement()
                    binding.progressCircular.isVisible = false
                    val listTvShow = it.data
                    Timber.i(" observerMovie succes : $listTvShow")

                    binding.rvList.adapter = TvShowRecyclerViewAdapter(listTvShow) {
                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.DETAIL_EXTRA_TV_SHOW, it)
                        }
                        startActivity(intent)
                    }
                }
                is Resource.Error -> {
                    DebugIdlingResource.decrement()
                    Timber.i(" observerMovie error : ${it.exceptionMessage}")
                    binding.progressCircular.isVisible = false
                }
            }
        }
    }

}