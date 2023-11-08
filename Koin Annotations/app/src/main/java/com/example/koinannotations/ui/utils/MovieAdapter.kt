package com.example.koinannotations.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.koinannotations.BuildConfig
import com.example.koinannotations.R
import com.example.koinannotations.databinding.MovieViewBinding
import com.example.koinannotations.domain.modelo.Movie


class MovieAdapter(private val actions: MovieActions) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(
    DiffCallback()
) {

    interface MovieActions {
        fun onMovieClick(id: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position).let {
            holder.render(it, actions)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.titulo == newItem.titulo
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = MovieViewBinding.bind(view)

        fun render(
            movie: Movie,
            actions: MovieActions
        ) {
            with(binding) {
                txtName.text = movie.titulo
                idIVCourseImage.load(BuildConfig.IMAGE_URL + movie.posterPath) {
                    placeholder(R.drawable.ic_baseline_downloading_24)
                    error(R.drawable.ic_baseline_error_24)
                }
                txtRating.text = movie.rating.toString()

                txtPopularity.text = movie.popularity.toString()
                txtResumen.text = if (movie.resumen.length > 100) {"${movie.resumen.substring(0, 100)}..."
                } else{movie.resumen}

                itemView.setOnClickListener { actions.onMovieClick(movie.id) }
            }
        }
    }
}