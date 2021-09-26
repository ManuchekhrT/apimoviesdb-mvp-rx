package com.example.moviesapi.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.moviesapi.R
import com.example.moviesapi.base.BaseFragment
import com.example.moviesapi.extensions.BASE_MOVIES_IMAGE_API
import com.example.moviesapi.model.Movies
import kotlinx.android.synthetic.main.fragment_movie_detail.*

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailFragment : BaseFragment<MovieDetailPresenter>(), MovieDetailView {

    private var movie: Movies.Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getSerializable(ARG_MOVIE_ITEM) as Movies.Movie
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie?.let {
            Glide.with(getBaseContext())
                .load(BASE_MOVIES_IMAGE_API + it.backdropPath)
                .into(iv_backdrop_path)

            tv_detail_title.text = it.title
            tv_detail_vote_average.text = getString(R.string.rating, it.voteAverage.toString())
            tv_detail_vote_count.text = getString(R.string.vote_count, it.voteCount.toString())
            tv_detail_overview.text = it.overview
        }
    }

    companion object {
        private const val ARG_MOVIE_ITEM = "movie_item"

        @JvmStatic
        fun newInstance(item: Movies.Movie?) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_MOVIE_ITEM, item)
                }
            }
    }

    override fun instantiatePresenter(): MovieDetailPresenter = MovieDetailPresenter(this)

    override fun getLayoutId(): Int = R.layout.fragment_movie_detail
}