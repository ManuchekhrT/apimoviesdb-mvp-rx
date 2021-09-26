package com.example.moviesapi.ui.movies

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapi.R
import com.example.moviesapi.base.BaseFragment
import com.example.moviesapi.extensions.TAG_MOVIE_DETAIL_FRAG
import com.example.moviesapi.model.Movies
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : BaseFragment<MoviesPresenter>(), MoviesView, OnMoviesListClickListener {

    private var isLoading = false
    lateinit var layoutManager: LinearLayoutManager
    lateinit var movieAdapter: MovieAdapter
    private var pastVisibleItems = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var currentPage = 1
    private var fetchedMoviesList = mutableListOf<Movies.Movie>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        presenter.fetchPopularMovies(currentPage)

        rv_movies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isLoading = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                if (isLoading && (visibleItemCount + pastVisibleItems == totalItemCount)) {
                    isLoading = false
                    currentPage++;
                    presenter.fetchPopularMovies(currentPage)
                }
            }
        })
    }

    private fun prepareRecyclerView() {
        movieAdapter = MovieAdapter(this)
        layoutManager = LinearLayoutManager(getBaseContext())
        rv_movies.layoutManager = layoutManager
        rv_movies.setHasFixedSize(true)
        rv_movies.adapter = movieAdapter
    }

    override fun instantiatePresenter(): MoviesPresenter = MoviesPresenter(this)

    override fun getLayoutId(): Int = R.layout.fragment_movies

    override fun showLoading() {
        pb_loading.isVisible = true
    }

    override fun hideLoading() {
        pb_loading.isVisible = false
    }

    override fun displayMovies(movies: List<Movies.Movie>) {
        fetchedMoviesList.addAll(movies)
        movieAdapter.submitList(fetchedMoviesList)
    }

    override fun showError(error: String) {
        Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MoviesFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onMovieClick(item: Movies.Movie?) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragments_container,
                MovieDetailFragment.newInstance(item),
                TAG_MOVIE_DETAIL_FRAG
            )
            .addToBackStack(null)
            .commit()
    }

}