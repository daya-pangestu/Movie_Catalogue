package com.daya.moviecatalogue.ui.main.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.databinding.FragmentItemListBinding
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.main.MovieRecyclerViewAdapter
import timber.log.Timber

class MovieFavFragment : Fragment(R.layout.fragment_item_list) {
    private val binding by viewBinding<FragmentItemListBinding>()

    private val viewModel by viewModels<MovieFavViewModel>()

    private val idlingResources = TestIdlingResource

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerFavoriteMovies()
    }

    fun observerFavoriteMovies() {
        idlingResources.increment()
        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            idlingResources.decrement()
            binding.progressCircular.isVisible = false
            val listMovie = it
            Timber.i(" observerMovie succes : $listMovie")
            binding.rvList.adapter = MovieRecyclerViewAdapter(listMovie) {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.DETAIL_EXTRA_MOVIE, it.id)
                }
                startActivity(intent)
            }
        }
    }
}