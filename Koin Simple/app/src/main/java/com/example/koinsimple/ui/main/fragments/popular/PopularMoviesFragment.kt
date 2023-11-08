package com.example.koinsimple.ui.main.fragments.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.koinsimple.databinding.FragmentPopularMoviesBinding
import com.example.koinsimple.ui.common.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularMoviesFragment : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    private val viewModel: PopularMoviesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        stateListener()
        viewModel.handleEvent(PopularContract.Event.GetPopular)
    }


    private fun stateListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) { //con esta linea se asegura que el listener del flow se ejecute solo cuando la activity esté iniciada
                viewModel.uiState.collect { value ->
                    binding.loading.visibility =
                        if (value.isLoading) View.VISIBLE else View.GONE
                    movieAdapter.submitList(value.movies)
                    value.mensaje?.let {
                        Snackbar.make(binding.rvMovies, it, Snackbar.LENGTH_SHORT).show()
                        viewModel.handleEvent(PopularContract.Event.MensajeMostrado)
                    }
                }
            }
        }
    }


    private fun init() {
        movieAdapter = MovieAdapter(
            object : MovieAdapter.MovieActions{
                override fun onMovieClick(id: Int) {
                    movieSelected(id)
                }

            }

        )
        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovies.adapter = movieAdapter
    }

    private fun movieSelected(id: Int) {
        val action = PopularMoviesFragmentDirections.actionPopularMoviesToDetalleMovieFragment(id)
        findNavController().navigate(action)
    }
}