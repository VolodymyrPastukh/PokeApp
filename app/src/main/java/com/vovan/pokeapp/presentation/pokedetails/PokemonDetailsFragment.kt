package com.vovan.pokeapp.presentation.pokedetails

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentPokemonDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {

    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by viewModel { parametersOf(navArgs.pokemonId)}
    private val binding: FragmentPokemonDetailsBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::displayData)
    }

    private fun displayData(state: PokemonDetailsViewState) = binding.apply{
        when(state){
            is PokemonDetailsViewState.Loading -> {
                constrain.isVisible = false
            }

            is PokemonDetailsViewState.Data -> {
                progressBar.hide()
                constrain.isVisible = true
                pokemon = state.pokemonItem
                executePendingBindings()

                stat1t.text = state.pokemonItem.stats[0].name
                stat1p.progress = state.pokemonItem.stats[0].base_stat

                stat2t.text = state.pokemonItem.stats[1].name
                stat2p.progress = state.pokemonItem.stats[1].base_stat

                stat3t.text = state.pokemonItem.stats[2].name
                stat3p.progress = state.pokemonItem.stats[2].base_stat

                stat4t.text = state.pokemonItem.stats[3].name
                stat4p.progress = state.pokemonItem.stats[3].base_stat

                stat5t.text = state.pokemonItem.stats[4].name
                stat5p.progress = state.pokemonItem.stats[4].base_stat

                stat6t.text = state.pokemonItem.stats[5].name
                stat6p.progress = state.pokemonItem.stats[5].base_stat
            }

            is PokemonDetailsViewState.Error -> {
                progressBar.hide()
            }
        }
    }

}