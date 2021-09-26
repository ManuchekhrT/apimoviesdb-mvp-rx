package com.example.moviesapi.base

import com.example.moviesapi.di.ContextModule
import com.example.moviesapi.di.DaggerPresenterInjector
import com.example.moviesapi.di.NetworkModule
import com.example.moviesapi.di.PresenterInjector
import com.example.moviesapi.ui.movies.MovieDetailPresenter
import com.example.moviesapi.ui.movies.MoviesPresenter

/**
 * Base presenter any presenter of the application must extend.
 * It provides initial injections and required methods.
 * @param V the type of the View the presenter is based on
 * @property view the view the presenter is based on
 * @constructor Injects the required dependencies
 */
abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    /**
     * The inhector used to inject required dependencies
     */
    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .baseView(view)
        .contextModule(ContextModule)
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * This method may be called when the presenter view is created
     */
    open fun onViewCreated(){}

    /**
     * This method may be called when the presenter view is destroyed
     */
    open fun onViewDestroyed(){}

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MovieDetailPresenter -> injector.inject(this)
            is MoviesPresenter -> injector.inject(this)
        }
    }
}