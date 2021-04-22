package com.daya.moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.daya.moviecatalogue.data.movie.Movie
import com.daya.moviecatalogue.data.tvshow.TvShow
import com.daya.moviecatalogue.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding 
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieIntent = intent.getParcelableExtra<Movie>(DETAIL_EXTRA_MOVIE)
        val tvShowIntent = intent.getParcelableExtra<TvShow>(DETAIL_EXTRA_TV_SHOW)
        when {
            movieIntent != null -> {
                renderWithMovie(movieIntent)
            }
            tvShowIntent != null -> {
                renderWithTvShow(tvShowIntent)
            }
            else -> {
                Toast.makeText(this@DetailActivity, "cannot load detail", Toast.LENGTH_SHORT).show()
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

            //TODO load image
        }
    }

    private fun renderWithTvShow(tvShow: TvShow) {
        binding.apply {
            root.isVisible = true
            detailTvTitle.text = "${tvShow.title}(${tvShow.year})"
            detailTvGenre.text = "${tvShow.rate} | ${tvShow.genre}"
            detailTvDesc.text = tvShow.description
            detailTvScore.text = tvShow.user_score.toString()
            //TODO load image
        }
    }

    private fun renderWithoutData() {
        binding.root.isVisible = false
            //TODO load image
    }

    companion object {
        const val DETAIL_EXTRA_MOVIE = "detail_extra_movie"
        const val DETAIL_EXTRA_TV_SHOW = "detail_extra_tv_show"

    }
}