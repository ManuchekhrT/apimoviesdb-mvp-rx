package com.example.moviesapi.ui

import android.os.Bundle
import com.example.moviesapi.R
import com.example.moviesapi.base.BaseActivity
import com.example.moviesapi.extensions.TAG_MOVIES_FRAG
import com.example.moviesapi.extensions.showToast
import com.example.moviesapi.ui.movies.MoviesFragment

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.checkConnection()
    }

    override fun instantiatePresenter(): MainPresenter = MainPresenter(this)

    override fun showError(error: String) {
        this.showToast(error)
    }

    override fun callFragment() {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_MOVIES_FRAG)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragments_container, MoviesFragment.newInstance(), TAG_MOVIES_FRAG)
                .addToBackStack(null)
                .commit()
        }
    }
}