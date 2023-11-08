package com.example.koinsimple.domain.usecases.movies

import com.example.koinsimple.data.MovieRepository

class GetPopularMovies constructor(private val repository: MovieRepository) {

    fun invoke() = repository.getPopularMovies()
}