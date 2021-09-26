package com.example.moviesapi.ui

import com.example.moviesapi.base.BaseView

interface MainView : BaseView {
    fun showError(error: String)
    fun callFragment()
}