package com.example.koinannotations.ui.main.fragments.top_rated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koinannotations.domain.usecases.movies.GetTopRatedMovies
import com.example.koinannotations.ui.main.fragments.top_rated.TopRatedContract.State
import com.example.koinannotations.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class TopRatedMoviesViewModel(
    private val getTopRatedMoviesUC: GetTopRatedMovies,
) : ViewModel() {


    private val _uiState: MutableStateFlow<State> by lazy {
        MutableStateFlow(State())
    }

    val uiState: StateFlow<State> = _uiState

    fun handleEvent(event: TopRatedContract.Event) {
        when (event) {
            is TopRatedContract.Event.GetTopRated -> {
                getTopRated()
            }

            TopRatedContract.Event.MensajeMostrado -> {
                _uiState.update { it.copy(error = null) }
            }
        }


    }

    private fun getTopRated() {
        viewModelScope.launch {
            getTopRatedMoviesUC.invoke()
                .catch(action = { cause ->
                    _uiState.update {
                        it.copy(
                            error = cause.message ?: "",
                            isLoading = false
                        )
                    }
                })
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(error = result.message, isLoading = false)
                            }
                        }

                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(movies = result.data ?: emptyList(), isLoading = false)
                        }
                    }
                }
        }
    }
}
