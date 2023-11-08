package com.example.koinannotations.data.network.services

import com.example.koinannotations.data.modelo.ListActorsResponse
import com.example.koinannotations.data.modelo.ListMoviesResponse
import com.example.koinannotations.data.modelo.MovieEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET(ApiConstants.TOP_RATED_URL)
    suspend fun getTopRated() : Response<ListMoviesResponse>

    @GET(ApiConstants.MOVIE_DETAIL_URL)
    suspend fun getMovie(@Path(ApiConstants.MOVIE_ID) id: Int) : Response<MovieEntity>

    @GET(ApiConstants.POPULAR_URL)
    suspend fun getPopular() : Response<ListMoviesResponse>

    @GET(ApiConstants.MOVIE_CAST_URL)
    suspend fun getCast(@Path(ApiConstants.MOVIE_ID) id: Int) : Response<ListActorsResponse>
}