package com.daya.moviecatalogue.data.movie

data class Movie(
    val description: String,
    val genre: String,
    val rate: String,
    val release_date: String,
    val title: String,
    val user_score: Int,
    val year: Int
)