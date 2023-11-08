package com.example.koinannotations.data


import com.example.koinannotations.data.modelo.MovieEntity
import com.example.koinannotations.data.network.services.MovieApi
import com.example.koinannotations.data.remote.BaseApiResponse
import com.example.koinannotations.data.utils.toActor
import com.example.koinannotations.data.utils.toMovie
import com.example.koinannotations.domain.modelo.Actor
import com.example.koinannotations.domain.modelo.Movie
import com.example.koinannotations.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Single

@Single(binds = [MovieRepository::class])
class MovieRepositoryImpl(
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