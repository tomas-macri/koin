package com.example.koinannotations.ui.main.fragments.detalle_movie

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koinannotations.R
import com.example.koinannotations.domain.usecases.actors.GetActorsByMovie
import com.example.koinannotations.domain.usecases.movies.GetMovieDetails
import com.example.koinannotations.utils.NetworkResult
import com.example.koinannotations.utils.StringProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DetalleMovieViewModel(
    private val getMovieDetailsUC: GetMovieDetails,
    private val getCastUC: GetActorsByMovie,
    private val stringProvider: StringProvider
) : ViewModel() {


    private val _uiState: MutableStateFlow<DetalleMovieContract.State> by lazy {
        MutableStateFlow(DetalleMovieContract.State())
    }

    val uiState: StateFlow<DetalleMovieContract.State> = _uiState

    fun handleEvent(event: DetalleMovieContract.Event) {
        when (event) {
            is DetalleMovieContract.Event.GetDetails -> {
                getMovie(event.id)
            }

            is DetalleMovieContract.Event.GetActors -> {
                getActors(event.id)
            }

            DetalleMovieContract.Event.MensajeMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    private fun getActors(id: Int) {
        viewModelScope.launch {
            getCastUC.invoke(id)
                .catch { e ->
                    _uiState.update { it.copy(error = e.message, isLoading = false) }
                }
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            _uiState.update { it.copy(actors = result.data) }
                        }

                        is NetworkResult.Error -> {
                            _uiState.update { it.copy(error = result.message) }
                        }

                        is NetworkResult.Loading -> {
                            _uiState.update { it.copy(isLoading = true) }
                        }
                    }

                }
        }
    }


    private fun getMovie(id: Int) {
        viewModelScope.launch {
            getMovieDetailsUC.invoke(id)
                .catch(action = { cause ->
                    _uiState.update {
                        Log.e(this.javaClass.name, cause.message?:"")
                        it.copy(
                            error = stringProvider.getString(R.string.error_cargar_movie),
                            isLoading = false
                        )
                    }
                })
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                Log.e(this.javaClass.name, result.message?:"")
                                it.copy(error = result.message, isLoading = false)
                            }
                        }

                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(movie = result.data, isLoading = false)
                        }
                    }
                }
        }
    }
}