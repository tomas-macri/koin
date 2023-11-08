package com.example.koinsimple.domain.usecases.actors

import com.example.koinsimple.data.MovieRepository

class GetActorsByMovie constructor(private val repository: MovieRepository) {
    fun invoke(id: Int) = repository.getCast(id)
}