package com.daya.moviecatalogue.data.tvshow

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
        val title: String,
        val year: Int,
        val description: String,
        val genre: String,
        val rate: String,
        val user_score: Int,
        val image_url: Int = 0
) : Parcelable