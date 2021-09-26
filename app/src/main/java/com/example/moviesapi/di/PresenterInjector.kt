package com.example.moviesapi.di

import com.example.moviesapi.base.BaseView
import com.example.moviesapi.ui.MainPresenter
import com.example.moviesapi.ui.movies.MovieDetailPresenter
import com.example.moviesapi.ui.movies.MoviesPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {
    /**
     * Injects required dependencies into the specified MainPresenter.
     * @param mainPresenter MainPresenter in which to inject the dependencies
     */
    fun inject(movieDetailPresenter: MovieDetailPresenter)
    fun inject(moviesPresenter: MoviesPresenter)
    fun inject(mainPresenter: MainPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}