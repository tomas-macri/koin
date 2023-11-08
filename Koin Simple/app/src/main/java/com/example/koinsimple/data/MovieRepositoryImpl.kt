package com.example.koinsimple.data


import com.example.koinsimple.data.modelo.MovieEntity
import com.example.koinsimple.data.network.services.MovieApi
import com.example.koinsimple.data.remote.BaseApiResponse
import com.example.koinsimple.data.utils.toActor
import com.example.koinsimple.data.utils.toMovie
import com.example.koinsimple.domain.modelo.Actor
import com.example.koinsimple.domain.modelo.Movie
import com.example.koinsimple.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl constructor(
    private val movieApi: MovieApi,
) : BaseApiResponse(), MovieRepository {

    override fun getPopularMovies(): Flow<NetworkResult<List<Movie>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = validateApiCall(apiCall = { movieApi.getPopular() },
                transform = { listaMoviesResponse -> transformMovies(listaMoviesResponse.results) })
            emit(result)

        }.flowOn(Dispatchers.IO)
    }

    override fun getMovie(id: Int): Flow<NetworkResult<Movie>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = validateApiCall(apiCall = { movieApi.getMovie(id) },
                transform = { movie -> movie.toMovie() })
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


    override fun getTopRatedMovies(): Flow<NetworkResult<List<Movie>>> {
        return flow {
            emit(NetworkResult.Loading())
            val result = validateApiCall(apiCall = { movieApi.getTopRated() },
                transform = { listaMoviesResponse -> transformMovies(listaMoviesResponse.results) })
            emit(result)
        }.flowOn(Dispatchers.IO)
    }


    override fun getCast(id: Int): Flow<NetworkResult<List<Actor>>>{
        return flow {
            emit(NetworkResult.Loading())
            val result = validateApiCall(apiCall = { movieApi.getCast(id) },
                transform = { listaActorsResponse -> listaActorsResponse.cast?.map { it.toActor() }?: emptyList() })
            emit(result)
        }.flowOn(Dispatchers.IO)

    }

    private fun transformMovies(movies: List<MovieEntity>?): List<Movie> =
        movies?.map { it.toMovie() } ?: emptyList()

}