package com.example.koinannotations.ui.main.fragments.detalle_movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.koinannotations.BuildConfig
import com.example.koinannotations.R
import com.example.koinannotations.databinding.FragmentDetalleMovieBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetalleMovieFragment : Fragment() {

    private var _binding: FragmentDetalleMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetalleMovieViewModel by viewModel()

    private lateinit var actorAdapter: ActorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetalleMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetalleMovieFragmentArgs by navArgs()
        var idMovie: Int
        args.let {
            idMovie = it.id
        }
        initRecycler()
        stateListener()
        viewModel.handleEvent(DetalleMovieContract.Event.GetDetails(idMovie))
        viewModel.handleEvent(DetalleMovieContract.Event.GetActors(idMovie))
    }

    private fun initRecycler() {
        actorAdapter = ActorAdapter()
        binding.recyclerActores.adapter = actorAdapter
        binding.recyclerActores.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun stateListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) { //con esta linea se asegura que el listener del flow se ejecute solo cuando la activity esté iniciada
                viewModel.uiState.collect { value ->
                    value.movie?.let {
                        with(binding) {
                            txtTitle.text = it.titulo
                            imgMovie.load(BuildConfig.IMAGE_URL + it.posterPath) {
                                placeholder(R.drawable.ic_baseline_downloading_24)
                                error(R.drawable.ic_baseline_error_24)
                            }
                            txtRating.text = it.rating.toString()
                            txtRating.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_baseline_star_24,
                                0,
                                0,
                                0
                            )
                            val resumen = "${getString(R.string.overview)} ${it.resumen}"
                            txtResumen.text = resumen
                            val budget = "${getString(R.string.budget)} ${it.budget ?: getString(R.string.unknown)}"
                            txtBudget.text = budget
                        }
                    }
                    value.actors?.let {
                        actorAdapter.submitList(it)
                    }

                    value.error?.let {
                        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                        viewModel.handleEvent(DetalleMovieContract.Event.MensajeMostrado)
                    }
                    binding.loading.visibility = if (value.isLoading) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
    }

}