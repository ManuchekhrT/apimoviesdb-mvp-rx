package com.example.moviesapi.ui.movies

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.moviesapi.R
import com.example.moviesapi.extensions.BASE_MOVIES_IMAGE_API
import com.example.moviesapi.model.Movies
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(val clickListener: OnMoviesListClickListener) :
    ListAdapter<Movies.Movie, RecyclerView.ViewHolder>(TaskDiffCallBack()) {

    //This check runs on background thread
    class TaskDiffCallBack : DiffUtil.ItemCallback<Movies.Movie>() {
        override fun areItemsTheSame(oldItem: Movies.Movie, newItem: Movies.Movie): Boolean {
            return oldItem.id == newItem.id;
        }

        override fun areContentsTheSame(oldItem: Movies.Movie, newItem: Movies.Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MvViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        (holder as MvViewHolder).apply {
            bind(movie)
        }
    }

    inner class MvViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(movieItem: Movies.Movie?) {
            itemView.tv_title.text = movieItem?.title
            itemView.tv_vote_average.text =
                context.getString(R.string.rating, movieItem?.voteAverage.toString())
            itemView.tv_vote_count.text =
                context.getString(R.string.vote_count, movieItem?.voteCount.toString())
            itemView.tv_overview.text = movieItem?.overview

            itemView.pb_poster_loading.isVisible = true
            Glide.with(context)
                .load(BASE_MOVIES_IMAGE_API + movieItem?.posterPath)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.pb_poster_loading.isVisible = false
                        return false;
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.pb_poster_loading.isVisible = false
                        return false;
                    }

                })
                .into(itemView.iv_poster)

            itemView.setOnClickListener {
                clickListener.onMovieClick(movieItem)
            }
        }

    }

    override fun submitList(list: MutableList<Movies.Movie>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}

