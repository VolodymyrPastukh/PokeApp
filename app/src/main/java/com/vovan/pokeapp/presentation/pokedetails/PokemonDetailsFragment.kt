package com.vovan.pokeapp.presentation.pokedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vovan.pokeapp.createRandGradientBackground
import com.vovan.pokeapp.databinding.FragmentPokemonDetailsBinding
import com.vovan.pokeapp.databinding.FragmentPokemonListBinding
import com.vovan.pokeapp.presentation.adapter.StatAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PokemonDetailsFragment : Fragment() {

    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by viewModel { parametersOf(navArgs.pokemonId) }
    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding: FragmentPokemonDetailsBinding
        get() = checkNotNull(_binding)
    private var adapter: StatAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPokemonDetailsBinding.inflate(inflater).apply { _binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        adapter = StatAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner, ::displayData)
    }

    private fun displayData(state: PokemonDetailsViewState) = with(binding) {
        when (state) {
            is PokemonDetailsViewState.Loading -> {
                progressBar.isVisible = true
            }

            is PokemonDetailsViewState.Data -> {
                progressBar.hide()
                layout.background = createRandGradientBackground()
                art.background = createRandGradientBackground()

                pokemon = state.pokemonItem
                executePendingBindings()

                if(state.pokemonItem.stats == null) {
                    recyclerView.visibility = View.GONE
                    return@with
                }
                adapter?.setData(state.pokemonItem.stats)

            }

            is PokemonDetailsViewState.Error -> {
                progressBar.hide()
            }
        }
    }

}