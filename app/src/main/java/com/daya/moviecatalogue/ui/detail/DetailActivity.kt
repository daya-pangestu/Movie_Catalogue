package com.daya.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.databinding.ActivityDetailBinding
import com.daya.moviecatalogue.loadImage

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
        val movie = intent.getParcelableExtra<Movie>(DETAIL_EXTRA_MOVIE)
        val tvShow  =  intent.getParcelableExtra<TvShow>(DETAIL_EXTRA_TV_SHOW)

        when {
            movie != null -> {
                viewModel.submitMovie(movie)
            }
            tvShow != null -> {
                viewModel.submitTvShow(tvShow)
            }
        }

        viewModel.observeMovie().observe(this,Observer(::renderWithMovie))
        viewModel.observeTvShow().observe(this,Observer(::renderWithTvShow))
    }

    private fun renderWithMovie(movie: Movie) {
        binding.apply {
            root.isVisible = true
            detailTvTitle.text = "${movie.title}(${movie.year})"
            detailTvGenre.text = "${movie.rate} | ${movie.genre}"
            detailTvDesc.text = movie.description
            detailTvReleaseDate.text = movie.release_date
            detailTvScore.text = movie.user_score.toString()
            detailIvPoster.loadImage(movie.image_url)

        }
    }

    private fun renderWithTvShow(tvShow: TvShow) {
        binding.apply {
            root.isVisible = true
            detailTvTitle.text = "${tvShow.title}(${tvShow.year})"
            detailTvGenre.text = "${tvShow.rate} | ${tvShow.genre}"
            detailTvDesc.text = tvShow.description
            detailTvScore.text = tvShow.user_score.toString()
            detailIvPoster.loadImage(tvShow.image_url)

        }
    }

    companion object {
        const val DETAIL_EXTRA_MOVIE = "detail_extra_movie"
        const val DETAIL_EXTRA_TV_SHOW = "detail_extra_tv_show"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else ->super.onOptionsItemSelected(item)
        }
    }
}

