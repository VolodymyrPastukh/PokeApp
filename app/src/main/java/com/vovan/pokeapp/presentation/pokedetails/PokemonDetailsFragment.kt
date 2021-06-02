package com.vovan.pokeapp.presentation.pokedetails

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
                pokemonImage.isVisible = false
                pokemonName.isVisible = false
            }

            is PokemonDetailsViewState.Data -> {
                progressBar.hide()
                pokemonImage.isVisible = true
                pokemonName.isVisible = true
                pokemon = state.pokemonItem
                executePendingBindings()
            }

            is PokemonDetailsViewState.Error -> {
                progressBar.hide()
                pokemonName.isVisible = true
                pokemonName.text = state.message
            }
        }
    }

}