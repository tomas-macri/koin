package com.example.koinsimple.ui.main.fragments.detalle_movie

import com.example.koinsimple.domain.modelo.Actor
import com.example.koinsimple.domain.modelo.Movie

interface DetalleMovieContract {

    sealed class Event {

        class GetDetails(val id: Int ) : Event()
        class GetActors(val id: Int) : Event()
        object MensajeMostrado: Event()

    }

    data class State(
        val movie: Movie? = null,
        val actors: List<Actor>? = null,
        val isLoading : Boolean = false,
        val error: String? = null,
        )
}