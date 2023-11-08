package com.example.koinannotations.domain.usecases.movies

import com.example.koinannotations.data.MovieRepository
import org.koin.core.annotation.Factory

@Factory
class GetMovieDetails(private val movieRepository: MovieRepository) {

    fun invoke(id: Int) = movieRepository.getMovie(id)
}