package com.daya.moviecatalogue.ui.main.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.ui.detail.DetailActivity
import com.daya.moviecatalogue.ui.main.MainViewModel
import com.daya.moviecatalogue.ui.main.movie.MovieRecyclerViewAdapter

class TvShowFragment : Fragment() {
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = MovieRecyclerViewAdapter(mainViewModel.getMovie){
                    val intent = Intent(context, DetailActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return view
    }
}