package com.example.moviesapi.ui.movies

import com.example.moviesapi.model.Movies

interface OnMoviesListClickListener {
    fun onMovieClick(item: Movies.Movie?)
}