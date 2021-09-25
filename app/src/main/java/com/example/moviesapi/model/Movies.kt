package com.example.moviesapi.model

import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val result: List<Movie>
) {
    data class Movie(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("popularity")
        val popularity: Number,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        @SerializedName("vote_average")
        val voteAverage: Number,
        @SerializedName("vote_count")
        val voteCount: Int,
    )
}
