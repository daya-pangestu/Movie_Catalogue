package com.daya.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.databinding.ActivityDetailBinding
import com.daya.moviecatalogue.loadImage
import com.daya.moviecatalogue.toast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.detail_toolbar_title)
            setDisplayHomeAsUpEnabled(true)
        }
        val movie = intent.getIntExtra(DETAIL_EXTRA_MOVIE,0)
        val tvShow  =  intent.getIntExtra(DETAIL_EXTRA_TV_SHOW,0)

        when {
            movie != 0 -> {
                viewModel.submitMovie(movie)
            }
            tvShow != 0 -> {
                viewModel.submitTvShow(tvShow)
            }
        }

        viewModel.observeMovie.observe(this){
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    renderWithMovie(it.data)
                }
                is Resource.Error -> {
                    toast(getString(R.string.fail_to_load))
                }
            }
        }
        viewModel.observeTvShow.observe(this){
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    renderWithTvShow(it.data)
                }
                is Resource.Error -> {
                    toast(getString(R.string.fail_to_load))
                }
            }
        }

        //oneshoot operation, called only when activity opened,
        viewModel.checkIsFavorite.observe(this){
            when (it) {
                is Resource.Success -> {
                    viewModel.isFavorite = it.data
                    invalidateOptionsMenu()
                }
                else -> {
                    Timber.i("isFavorite status $it")
                }
            }
        }
        
        viewModel.observeSaving.observe(this){
            when (it) {
                is Resource.Loading -> toast("adding to favorite")
                is Resource.Success -> {
                    toast("added to favorite")
                    viewModel.isFavorite = it.data
                    invalidateOptionsMenu()
                }
                is Resource.Error -> toast("error ${it.exceptionMessage}")
            }
        }
        
        viewModel.observeDeleting.observe(this){
            when (it) {
                is Resource.Loading -> toast("removing from favorite")
                is Resource.Success -> {
                    toast("removed from favorite")
                    viewModel.isFavorite = !it.data
                    invalidateOptionsMenu()
                }
                is Resource.Error -> toast("error ${it.exceptionMessage}")
            }
        }
    }

    private fun renderWithMovie(movie: Movie) {
        binding.apply {
            detailPlaceholderDesc.text = getString(R.string.description)
            detailTvTitle.text = "${movie.title}(${movie.year})"
            detailTvDesc.text = movie.description
            detailTvReleaseDate.text = movie.release_date
            detailTvScore.text = movie.user_score.toString()
            detailIvPoster.loadImage(movie.image_url)
        }
        viewModel.startObserveIsFavorite(movie.id)
    }

    private fun renderWithTvShow(tvShow: TvShow) {
        binding.apply {
            detailPlaceholderDesc.text = getString(R.string.description)
            detailTvTitle.text = "${tvShow.title}(${tvShow.year})"
            detailTvDesc.text = tvShow.description
            detailTvScore.text = tvShow.user_score.toString()
            detailIvPoster.loadImage(tvShow.image_url)
        }
        viewModel.startObserveIsFavorite(tvShow.id)

    }

    companion object {
        const val DETAIL_EXTRA_MOVIE = "detail_extra_movie"
        const val DETAIL_EXTRA_TV_SHOW = "detail_extra_tv_show"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val currentlySaved =
            (viewModel.observeSaving.value is Resource.Success)
                    && (viewModel.observeDeleting.value !is Resource.Success)

        val hasSavedBefore = when (val res = viewModel.checkIsFavorite.value) {
            is Resource.Success -> res.data
            else -> false
        }

        val isFavorite = viewModel.isFavorite
        menu?.findItem(R.id.action_favorite)?.isVisible = !isFavorite
        menu?.findItem(R.id.action_unfavorite)?.isVisible = isFavorite
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_favorite -> {
                viewModel.addToFavorite()
                true
            }
            R.id.action_unfavorite -> {
                viewModel.deleteFromFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}

