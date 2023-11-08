package com.example.koinannotations.ui.main.fragments.popular

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koinannotations.R
import com.example.koinannotations.domain.usecases.movies.GetPopularMovies
import com.example.koinannotations.ui.main.fragments.popular.PopularContract.State
import com.example.koinannotations.utils.NetworkResult
import com.example.koinannotations.utils.StringProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class PopularMoviesViewModel (
    private val getPopularMoviesUC: GetPopularMovies,
    private val stringProvider: StringProvider
) : ViewModel() {


    private val _uiState: MutableStateFlow<State> by lazy {
        MutableStateFlow(State())
    }

    val uiState: StateFlow<State> = _uiState

    fun handleEvent(event: PopularContract.Event) {
        when (event) {
            PopularContract.Event.GetPopular -> {
                getPopular()
            }
            PopularContract.Event.MensajeMostrado -> {
                _uiState.update { it.copy(mensaje = null) }
            }
        }


    }

    private fun getPopular() {
        viewModelScope.launch {
                getPopularMoviesUC.invoke()
                    .catch(action = { cause ->
                        Log.e(this.javaClass.name, cause.message?:"")
                        _uiState.update { it.copy(mensaje = stringProvider.getString(
                                                                    R.string.error_cargar_populares) , isLoading = false)}
                    })
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(mensaje = result.message, isLoading = false)
                                }
                            }
                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(movies = result.data ?: emptyList(), isLoading = false, mensaje = result.message)
                            }
                        }
                    }
        }
    }


}