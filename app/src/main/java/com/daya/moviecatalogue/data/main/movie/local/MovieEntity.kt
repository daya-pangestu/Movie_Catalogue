package com.daya.moviecatalogue.data.main.movie.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entity")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Int = 0,
    val title: String,
    val description: String,
    val release_date: String,
    val user_score: Int,
    val year: Int,
    val image_url: String = "",
)