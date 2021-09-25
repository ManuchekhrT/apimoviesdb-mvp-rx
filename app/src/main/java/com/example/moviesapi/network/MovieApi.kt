package com.example.moviesapi.network

import com.example.moviesapi.extensions.*
import com.example.moviesapi.model.Movies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET(POPULAR_MOVIES_PATH)
    fun getPopularMovies(
        @Query(API_KEY) apiKey: String = MOVIESDB_GENERATED_API_KEY,
        @Query(LANGUAGE) language: String = LANGUAGE_EN_US,
        @Query(PAGE) page: Int,
        ): Single<Movies>
}