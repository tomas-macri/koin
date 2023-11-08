package com.example.koinannotations.di

import com.example.koinannotations.BuildConfig
import com.example.koinannotations.data.network.AuthInterceptor
import com.example.koinannotations.data.network.services.MovieApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Single
fun provideAuthInterceptor(@Named("api_key") apiKey: String): AuthInterceptor {
    return AuthInterceptor(apiKey)
}

@Single
fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    return interceptor
}

@Single
fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    authInterceptor: AuthInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()
}

@Single
@Named("base_url")
fun provideBaseUrl(): String {
    return BuildConfig.BASE_URL
}

@Single
@Named("api_key")
fun provideApiKey(): String {
    return BuildConfig.API_KEY
}

@Single
fun provideRetrofit(
    okHttpClient: OkHttpClient,
    @Named("base_url") baseUrl: String,
    moshiConverterFactory: MoshiConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(moshiConverterFactory)
        .client(okHttpClient)
        .build()
}

@Single
fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()


@Single
fun provideMovieService(retrofit: Retrofit): MovieApi {
    return retrofit.create(MovieApi::class.java)
}
