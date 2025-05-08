package com.example.lbwatch.main_activity

import com.example.lbwatch.model.Movie

interface MainView {
    fun showMovies(movies: List<Movie>)
    fun showEmptyState()
    fun showToast(message: String)
}
