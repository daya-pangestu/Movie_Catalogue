package com.daya.moviecatalogue.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daya.moviecatalogue.data.main.tvshow.TvShow
import com.daya.moviecatalogue.databinding.ItemTvshowBinding
import com.daya.moviecatalogue.loadImage
import com.daya.moviecatalogue.tvShowDiffCallback

class TvShowRecyclerViewAdapter(
    private val onItemClick: (TvShow) -> Unit
) : PagingDataAdapter<TvShow,TvShowRecyclerViewAdapter.TvViewHolder>(tvShowDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val binding = ItemTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  TvViewHolder(binding){
            val item = getItem(it)
            onItemClick(item!!)
        }
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
    
    inner class TvViewHolder(private val binding: ItemTvshowBinding, onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { onItemClick(absoluteAdapterPosition) }
        }

        fun bind(tvShow: TvShow) {
            binding.tvTitle.text = tvShow.title
            binding.tvDesc.text = tvShow.description
            binding.tvScore.text = tvShow.user_score.toString()
            binding.ivPoster.loadImage(tvShow.image_url)
        }
    }
}