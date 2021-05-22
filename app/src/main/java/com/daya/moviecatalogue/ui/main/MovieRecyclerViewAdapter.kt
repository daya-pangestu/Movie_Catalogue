package com.daya.moviecatalogue.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.databinding.ItemMovieBinding
import com.daya.moviecatalogue.loadImage

class MovieRecyclerViewAdapter(
        private val values: List<Movie>,
        private val onItemClick : (Movie) -> Unit
) : RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding){
            val item = values[it]
            onItemClick(item)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: ItemMovieBinding, onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { onItemClick(absoluteAdapterPosition) }
        }

        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            binding.tvDesc.text = movie.description
            binding.tvReleaseDate.text = movie.release_date
            binding.tvScore.text = movie.user_score.toString()
            binding.ivPoster.loadImage(movie.image_url)
        }
    }
}