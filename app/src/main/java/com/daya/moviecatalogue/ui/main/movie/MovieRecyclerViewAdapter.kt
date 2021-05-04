package com.daya.moviecatalogue.ui.main.movie

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.databinding.ItemMovieBinding

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
            binding.tvGenre.text = movie.genre
            binding.tvReleaseDate.text = movie.release_date
            binding.tvScore.text = movie.user_score.toString()
            Glide.with(itemView.context)
                .load(movie.image_url)
                .into(binding.ivPoster)
        }
    }
}