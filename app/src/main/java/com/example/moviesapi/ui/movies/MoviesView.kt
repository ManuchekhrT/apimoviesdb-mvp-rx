package com.example.moviesapi.ui.movies

import com.example.moviesapi.base.BaseView
import com.example.moviesapi.model.Movies

interface MoviesView : BaseView {
    fun showLoading()
    fun hideLoading()
    fun showError(error: String)
    fun displayMovies(movies: List<Movies.Movie>)
}