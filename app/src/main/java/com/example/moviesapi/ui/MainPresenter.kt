package com.example.moviesapi.ui

import com.example.moviesapi.base.BasePresenter
import com.example.moviesapi.network.MovieApi
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter(mainView: MainView) : BasePresenter<MainView>(mainView) {

    @Inject
    lateinit var movieApi: MovieApi

    private var subscription: Disposable? = null

    fun fetchPopularMovies(page: Int) {
        view.showLoading()
        subscription = ReactiveNetwork
            .observeInternetConnectivity()
            .flatMapSingle { connectivity -> movieApi.getPopularMovies(page = page) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.hideLoading()
                    view.displayMovies(it.result)
                },
                {
                    view.hideLoading()
                    view.showError("Internet connection falied")
                })
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}