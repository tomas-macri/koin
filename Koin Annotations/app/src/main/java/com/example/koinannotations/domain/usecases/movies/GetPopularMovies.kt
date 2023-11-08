package com.example.koinannotations.domain.usecases.movies

import com.example.koinannotations.data.MovieRepository
import org.koin.core.annotation.Factory

@Factory
class GetPopularMovies (private val repository: MovieRepository) {

    fun invoke() = repository.getPopularMovies()
}