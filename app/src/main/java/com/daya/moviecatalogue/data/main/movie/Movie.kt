package com.daya.moviecatalogue.data.main.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int = 0,
    val title: String,
    val description: String,
    val release_date: String,
    val user_score: Int,
    val year: Int,
    val image_url: String = "",
) : Parcelable