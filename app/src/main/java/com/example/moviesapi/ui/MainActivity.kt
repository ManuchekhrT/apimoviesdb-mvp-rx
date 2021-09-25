package com.example.moviesapi.ui

import android.os.Bundle
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapi.R
import com.example.moviesapi.base.BaseActivity
import com.example.moviesapi.model.Movies
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    private val movieAdapter = MovieAdapter()
    private var isLoading = false
    lateinit var layoutManager: LinearLayoutManager
    private var pastVisibleItems = 0
    private var visibleItemCount:Int = 0
    private var totalItemCount:Int = 0
    private var currentPage = 1
    private var fetchedMoviesList = mutableListOf<Movies.Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        rv_movies.layoutManager = layoutManager
        rv_movies.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
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

    override fun instantiatePresenter(): MainPresenter = MainPresenter(this)

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
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}