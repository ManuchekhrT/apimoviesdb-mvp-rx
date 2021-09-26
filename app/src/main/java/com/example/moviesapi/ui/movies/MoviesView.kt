package com.example.moviesapi.ui.movies

import com.example.moviesapi.base.BaseView
import com.example.moviesapi.model.Movies

interface MoviesView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun displayMovies(movies: List<Movies.Movie>)
    fun showError(error: String)
}