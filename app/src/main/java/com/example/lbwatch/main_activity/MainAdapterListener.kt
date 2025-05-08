package com.example.lbwatch.main_activity

import com.example.lbwatch.model.Movie

interface MainAdapterListener {
    fun onMovieSelected(movie: Movie, isSelected: Boolean)
}