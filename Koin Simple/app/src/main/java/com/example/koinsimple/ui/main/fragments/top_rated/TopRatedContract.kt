package com.example.koinsimple.ui.main.fragments.top_rated

import com.example.koinsimple.domain.modelo.Movie

interface TopRatedContract {

    sealed class Event {

        object GetTopRated : Event()
        object MensajeMostrado: Event()

    }

    data class State(
        val movies: List<Movie> = emptyList(),
        val isLoading : Boolean = false,
        val error: String? = null,

    )
}