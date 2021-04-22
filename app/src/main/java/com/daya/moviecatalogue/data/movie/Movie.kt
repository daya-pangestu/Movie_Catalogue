package com.daya.moviecatalogue.data.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val description: String,
    val genre: String,
    val rate: String,
    val release_date: String,
    val user_score: Int,
    val year: Int,
    val image_url: Int = 0
) : Parcelable