package com.example.koinsimple.ui.main.fragments.top_rated

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
import com.example.koinsimple.databinding.FragmentTopRatedMoviesBinding
import com.example.koinsimple.ui.common.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class TopRatedMoviesFragment : Fragment() {

    private var _binding: FragmentTopRatedMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieAdapter: MovieAdapter

    private val viewModel: TopRatedMoviesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopRatedMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        stateListener()
        viewModel.handleEvent(TopRatedContract.Event.GetTopRated)
    }


    private fun stateListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->

                    binding.loading.visibility =
                        if (value.isLoading) View.VISIBLE else View.GONE
                    movieAdapter.submitList(value.movies)
                    value.error?.let {
                        Snackbar.make(binding.rvMovies, it, Snackbar.LENGTH_SHORT).show()
                        viewModel.handleEvent(TopRatedContract.Event.MensajeMostrado)
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
        val action = TopRatedMoviesFragmentDirections.actionTopRatedMoviesToDetalleMovieFragment(id)
        findNavController().navigate(action)
    }

}