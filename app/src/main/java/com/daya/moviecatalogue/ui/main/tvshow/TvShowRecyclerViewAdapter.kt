package com.daya.moviecatalogue.ui.main.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daya.moviecatalogue.data.tvshow.TvShow
import com.daya.moviecatalogue.databinding.ItemTvshowBinding

class TvShowRecyclerViewAdapter(
    private val values: List<TvShow>,
    private val onItemClick: (TvShow) -> Unit
) : RecyclerView.Adapter<TvShowRecyclerViewAdapter.TvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val binding = ItemTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  TvViewHolder(binding){
            val item = values[it]
            onItemClick(item)
        }
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class TvViewHolder(private val binding: ItemTvshowBinding, onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { onItemClick(absoluteAdapterPosition) }
        }

        fun bind(tvshow: TvShow) {
            //Load image with glide
            binding.tvTitle.text = tvshow.title
            binding.tvDesc.text = tvshow.description
            binding.tvGenre.text = tvshow.genre
            binding.tvScore.text = tvshow.user_score.toString()

        }
    }


}