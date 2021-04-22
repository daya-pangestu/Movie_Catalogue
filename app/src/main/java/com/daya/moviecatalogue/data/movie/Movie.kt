package com.daya.moviecatalogue.data.movie

data class Movie(
    val title: String,
    val description: String,
    val genre: String,
    val rate: String,
    val release_date: String,
    val user_score: Int,
    val year: Int
)