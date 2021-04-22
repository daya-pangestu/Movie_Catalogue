package com.daya.moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.daya.moviecatalogue.data.movie.Movie
import com.daya.moviecatalogue.data.tvshow.TvShow
import com.daya.moviecatalogue.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Detail"
            setDisplayHomeAsUpEnabled(true)
        }
        viewModel.movie = intent.getParcelableExtra(DETAIL_EXTRA_MOVIE)
        viewModel.tvShow =  intent.getParcelableExtra(DETAIL_EXTRA_TV_SHOW)
        when {
            viewModel.movie != null -> renderWithMovie(viewModel.movie!!)
            viewModel.tvShow != null -> renderWithTvShow(viewModel.tvShow!!)
            else -> {
                Toast.makeText(this@DetailActivity, "cannot load detail", Toast.LENGTH_SHORT).show()
                renderWithoutData()
            }
        }
    }

    private fun renderWithMovie(movie: Movie) {
        binding.apply {
            root.isVisible = true
            detailTvTitle.text = "${movie.title}(${movie.year})"
            detailTvGenre.text = "${movie.rate} | ${movie.genre}"
            detailTvDesc.text = movie.description
            detailTvReleaseDate.text = movie.release_date
            detailTvScore.text = movie.user_score.toString()

            Glide.with(this@DetailActivity)
                .load(movie.image_url)
                .into(binding.detailIvPoster)
        }
    }

    private fun renderWithTvShow(tvShow: TvShow) {
        binding.apply {
            root.isVisible = true
            detailTvTitle.text = "${tvShow.title}(${tvShow.year})"
            detailTvGenre.text = "${tvShow.rate} | ${tvShow.genre}"
            detailTvDesc.text = tvShow.description
            detailTvScore.text = tvShow.user_score.toString()
            Glide.with(this@DetailActivity)
                .load(tvShow.image_url)
                .into(binding.detailIvPoster)        }
    }

    private fun renderWithoutData() {
        binding.root.isVisible = false
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