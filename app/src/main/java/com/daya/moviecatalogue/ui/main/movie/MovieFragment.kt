package com.daya.moviecatalogue.ui.main.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.di.idlingresource.DebugIdlingResource
import com.daya.moviecatalogue.di.idlingresource.IdlingResources
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_MOVIE
import com.daya.moviecatalogue.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_item_list) {

    val mainViewModel by viewModels<MainViewModel>()

    val idlingResources = DebugIdlingResource


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (view is RecyclerView) {
//            rvMovie = view
//            with(view) {
//                layoutManager = LinearLayoutManager(context)
//                setHasFixedSize(true)
//            }
//        }
       // observerMovies()
    }

    fun observerMovies() {
        idlingResources.increment()
        mainViewModel.discoverMovie.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Timber.i(" observerMovie loading")

                }
                is Resource.Success -> {
                    idlingResources.decrement()
                    val listMovie = it.data
                    Timber.i(" observerMovie succes : $listMovie")


//                    GHb\,TY\rvMovie.adapter = MovieRecyclerViewAdapter(listMovie) {
//                        val intent = Intent(context, DetailActivity::class.java).apply {
//                            putExtra(DETAIL_EXTRA_MOVIE, it)
//                        }
//                        startActivity(intent)
//                    }

                }
                is Resource.Error -> {
                    idlingResources.decrement()
                    Timber.i(" observerMovie error : ${it.exceptionMessage}")
                }
            }
        }
    }
}