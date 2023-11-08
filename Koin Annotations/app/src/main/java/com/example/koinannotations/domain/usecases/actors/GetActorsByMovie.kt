package com.example.koinannotations.domain.usecases.actors

import com.example.koinannotations.data.MovieRepository
import org.koin.core.annotation.Factory

@Factory
class GetActorsByMovie(private val repository: MovieRepository) {
    fun invoke(id: Int) = repository.getCast(id)
}