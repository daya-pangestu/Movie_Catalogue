package com.daya.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.Resource
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.databinding.ActivityDetailBinding
import com.daya.moviecatalogue.di.idlingresource.TestIdlingResource
import com.daya.moviecatalogue.loadImage
import com.daya.moviecatalogue.toast
import dagger.hilt.android.AndroidEntryPoint

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
        var movie = intent.getIntExtra(DETAIL_EXTRA_MOVIE,0)
        var tvShow  =  intent.getIntExtra(DETAIL_EXTRA_TV_SHOW,0)

        when {
            movie != 0 -> {
                TestIdlingResource.increment()
                viewModel.submitMovie(movie)
            }
            tvShow != 0 -> {
                TestIdlingResource.increment()
                viewModel.submitTvShow(tvShow)
            }
        }

        viewModel.observeMovie().observe(this){
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    TestIdlingResource.decrement()
                    renderWithMovie(it.data)
                }
                is Resource.Error -> {
                    TestIdlingResource.decrement()
                }
            }
        }
        viewModel.observeTvShow().observe(this){
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    TestIdlingResource.decrement()
                    renderWithTvShow(it.data)
                }
                is Resource.Error -> {
                    TestIdlingResource.decrement()
                    toast(getString(R.string.fail_to_load))
                }
            }
        }

        viewModel.observeIsFavorite.observe(this){
            invalidateOptionsMenu()
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
    }

    private fun renderWithTvShow(tvShow: TvShow) {
        binding.apply {
            detailPlaceholderDesc.text = getString(R.string.description)
            detailTvTitle.text = "${tvShow.title}(${tvShow.year})"
            detailTvDesc.text = tvShow.description
            detailTvScore.text = tvShow.user_score.toString()
            detailIvPoster.loadImage(tvShow.image_url)
        }
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
        if (viewModel.observeIsFavorite.value == true) {
            menu?.findItem(R.id.action_favorite)?.icon =
                ContextCompat.getDrawable(this@DetailActivity,R.drawable.ic_baseline_favorite_24)
        } else{
            menu?.findItem(R.id.action_favorite)?.icon =
                ContextCompat.getDrawable(this@DetailActivity,R.drawable.ic_baseline_favorite_border_24)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
               onBackPressed()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}

