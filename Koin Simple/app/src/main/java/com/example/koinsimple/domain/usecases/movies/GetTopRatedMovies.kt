package com.example.koinsimple.domain.usecases.movies

import com.example.koinsimple.data.MovieRepository

class GetTopRatedMovies constructor(private val repository: MovieRepository) {
    fun invoke() = repository.getTopRatedMovies()
}