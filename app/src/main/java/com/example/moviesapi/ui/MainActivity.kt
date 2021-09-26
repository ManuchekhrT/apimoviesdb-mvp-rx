package com.example.moviesapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapi.R
import com.example.moviesapi.extensions.TAG_MOVIES_FRAG
import com.example.moviesapi.ui.movies.MoviesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragments_container, MoviesFragment.newInstance(), TAG_MOVIES_FRAG)
                .addToBackStack(null)
                .commit()
        }
    }
}