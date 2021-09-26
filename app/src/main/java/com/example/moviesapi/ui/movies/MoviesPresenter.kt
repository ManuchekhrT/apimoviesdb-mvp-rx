package com.example.moviesapi.ui.movies

import com.example.moviesapi.base.BasePresenter
import com.example.moviesapi.network.MovieApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesPresenter(val mainView: MoviesView) : BasePresenter<MoviesView>(mainView) {

    @Inject
    lateinit var movieApi: MovieApi

    private var subscription: Disposable? = null

    fun fetchPopularMovies(page: Int) {
        view.showLoading()
        subscription = movieApi.getPopularMovies(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.hideLoading()
                    view.displayMovies(it.result)
                },
                {
                    view.hideLoading()
                    view.showError("" + it.message)
                })
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}