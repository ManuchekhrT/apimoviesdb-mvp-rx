package com.example.moviesapi.ui

import com.example.moviesapi.R
import com.example.moviesapi.base.BasePresenter
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val mainView: MainView) : BasePresenter<MainView>(mainView) {

    private var subscription: Disposable? = null

    fun checkConnection() {
        subscription = ReactiveNetwork
            .observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it) {
                        view.callFragment()
                    } else {
                        view.showError(
                            mainView.getBaseContext().getString(R.string.internet_conn_error)
                        )
                    }
                },
                {
                    view.showError("" + it.message)
                })
    }

    override fun onViewDestroyed() {
        super.onViewDestroyed()
        subscription?.dispose()
    }

}
