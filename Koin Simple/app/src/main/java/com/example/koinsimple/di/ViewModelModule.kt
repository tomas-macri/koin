package com.example.koinsimple.di

import com.example.koinsimple.ui.main.fragments.detalle_movie.DetalleMovieViewModel
import com.example.koinsimple.ui.main.fragments.popular.PopularMoviesViewModel
import com.example.koinsimple.ui.main.fragments.top_rated.TopRatedMoviesViewModel
import com.example.koinsimple.utils.StringProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
//  viewModel { DetalleMovieViewModel(get(), get(), get()) }
    viewModelOf(::DetalleMovieViewModel)
    viewModelOf(::TopRatedMoviesViewModel)
    viewModelOf(::PopularMoviesViewModel)
    single { StringProvider(androidContext()) }
}