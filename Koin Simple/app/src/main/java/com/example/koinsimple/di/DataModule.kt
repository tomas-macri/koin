package com.example.koinsimple.di

import com.example.koinsimple.BuildConfig
import com.example.koinsimple.data.MovieRepository
import com.example.koinsimple.data.MovieRepositoryImpl
import com.example.koinsimple.data.network.AuthInterceptor
import com.example.koinsimple.data.network.services.MovieApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {

    single {
        AuthInterceptor(get(named("api_key")))
    }

    single { HttpLoggingInterceptor.Logger.DEFAULT }

    single {
        HttpLoggingInterceptor(get()).apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    singleOf(::MovieRepositoryImpl) bind MovieRepository::class

    single(named("api_key")) { BuildConfig.API_KEY }

    single(named("base_url")) { BuildConfig.BASE_URL }

    single { MoshiConverterFactory.create() }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("base_url")))
            .addConverterFactory(get<MoshiConverterFactory>())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(MovieApi::class.java) }

}