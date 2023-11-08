package com.example.koinsimple.di

import com.example.koinsimple.domain.usecases.actors.GetActorsByMovie
import com.example.koinsimple.domain.usecases.movies.*

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCasesModule = module {
    factoryOf(::GetActorsByMovie)
    factoryOf(::GetMovieDetails)
    factoryOf(::GetPopularMovies)
    factoryOf(::GetTopRatedMovies)
}