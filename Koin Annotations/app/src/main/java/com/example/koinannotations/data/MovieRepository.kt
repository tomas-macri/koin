package com.example.koinannotations.data

import com.example.koinannotations.domain.modelo.Actor
import com.example.koinannotations.domain.modelo.Movie
import com.example.koinannotations.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<NetworkResult<List<Movie>>>

    fun getMovie(id: Int): Flow<NetworkResult<Movie>>

    fun getTopRatedMovies(): Flow<NetworkResult<List<Movie>>>

    fun getCast(id: Int): Flow<NetworkResult<List<Actor>>>
}