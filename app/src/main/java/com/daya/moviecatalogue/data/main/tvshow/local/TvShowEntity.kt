package com.daya.moviecatalogue.data.main.tvshow.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvShow_entity")
data class TvShowEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "tvShowId")
    val id: Int,
    val title: String,
    val year: Int,
    val description: String,
    val user_score: Int,
    val image_url: String = "",
)