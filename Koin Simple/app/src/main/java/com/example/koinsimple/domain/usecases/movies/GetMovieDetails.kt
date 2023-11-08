package com.example.koinsimple.domain.usecases.movies

import com.example.koinsimple.data.MovieRepository


class GetMovieDetails constructor(private val movieRepository: MovieRepository) {

    fun invoke(id: Int) = movieRepository.getMovie(id)
}