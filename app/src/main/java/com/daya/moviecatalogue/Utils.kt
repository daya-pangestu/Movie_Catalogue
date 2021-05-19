package com.daya.moviecatalogue

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.bumptech.glide.Glide
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.movie.local.MovieEntity
import com.daya.moviecatalogue.data.main.movie.response.DetailMovieResponse
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.data.main.tvshow.local.TvShowEntity
import com.daya.moviecatalogue.data.main.tvshow.response.DetailTvShowResponse

private const val URI_IMAGE = "https://image.tmdb.org/t/p/w500"
fun ImageView.loadImage(url: String) {
    if (url.isDigitsOnly()) {
        //it come from resource
        Glide.with(this.context)
            .load(url.toInt())
            .into(this)

    } else {
        //it from API
        val fullImageUrl = "$URI_IMAGE$url"
        Glide.with(this.context)
            .load(fullImageUrl)
            .into(this)

    }
}

//response to general
fun DetailMovieResponse.mapToMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title ?: "",
        description = this.overview ?: "",
        image_url = this.poster_path,
        release_date = this.release_date,
        user_score = (this.vote_average * 10).toInt(),
        year = this.release_date.take(4).toInt()
    )
}

fun DetailTvShowResponse.maptoTvShow(): TvShow {
    return TvShow(
        id = this.id,
        title = this.name,
        description = this.overview,
        image_url = this.poster_path,
        user_score = (this.vote_average * 10).toInt(),
        year = this.first_air_date.take(4).toInt()
    )
}

//general to entity
fun Movie.mapToMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        image_url = this.image_url,
        release_date = this.release_date,
        user_score = this.user_score,
        year = this.year
    )
}

fun TvShow.mapToTvShowEntity() : TvShowEntity {
    return TvShowEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        image_url = this.image_url,
        user_score = this.user_score,
        year = this.year
    )
}

//entity to general
fun MovieEntity.mapToMovie(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        description = this.description,
        image_url = this.image_url,
        release_date = this.release_date,
        user_score = this.user_score,
        year = this.year
    )
}

fun TvShowEntity.mapToTvShow() : TvShow {
    return TvShow(
        id = this.id,
        title = this.title,
        description = this.description,
        image_url = this.image_url,
        user_score = this.user_score,
        year = this.year
    )
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this,text,duration).show()
}
