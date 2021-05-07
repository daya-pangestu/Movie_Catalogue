package com.daya.moviecatalogue.ui.main.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.di.idlingresource.IdlingResources
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.detail.DetailActivity.Companion.DETAIL_EXTRA_MOVIE
import com.daya.moviecatalogue.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment() {

    val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var idlingResources: IdlingResources

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
            mainViewModel.discoverMovie.observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {
                        idlingResources.increment()
                    }
                    is Resource.Success -> {
                        idlingResources.decrement()
                        val listMovie = it.data

                        view.adapter = MovieRecyclerViewAdapter(listMovie) {
                            val intent = Intent(context, DetailActivity::class.java).apply {
                                putExtra(DETAIL_EXTRA_MOVIE, it.title)
                            }
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        idlingResources.decrement()
                    }
                }
            }
        }
        return view
    }
}