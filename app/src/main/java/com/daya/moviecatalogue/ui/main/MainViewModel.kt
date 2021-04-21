package com.daya.moviecatalogue.ui.main

import androidx.lifecycle.ViewModel
import com.daya.moviecatalogue.data.DataDummy

class MainViewModel : ViewModel() {

    val getMovie = DataDummy.getListMovie()

    val getTvShow = DataDummy.getListTvShow()
}