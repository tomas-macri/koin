package com.example.koinsimple.ui.main.fragments.popular

import com.example.koinsimple.domain.modelo.Movie

interface PopularContract {

    sealed class Event {

        object GetPopular : Event()
        object MensajeMostrado: Event()

    }

    data class State(
        val movies: List<Movie> = emptyList(),
        val isLoading : Boolean = false,
        val mensaje: String? = null,

        )
}