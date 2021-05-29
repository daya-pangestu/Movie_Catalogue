package com.daya.moviecatalogue.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daya.moviecatalogue.databinding.LoadItemBinding

class LoadingStateAdapter(private val retry: () -> Unit = {}) :
    LoadStateAdapter<LoadingStateAdapter.LoadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val binding = LoadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadViewHolder(binding,retry)
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadViewHolder(private val binding: LoadItemBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.progressCircular.isVisible = loadState is LoadState.Loading
        }
    }

}