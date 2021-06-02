package com.vovan.pokeapp.presentation.pokedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentPokemonDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PokemonDetailsFragment : Fragment() {

    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by viewModel { parametersOf(navArgs.pokemonId)}
    private lateinit var binding: FragmentPokemonDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_pokemon_details,
            container,
            false
        )

        viewModel.state.observe(viewLifecycleOwner, { state ->
            displayData(state)
        })


        return binding.root
    }

    private fun displayData(state: PokemonDetailsViewState){
        when(state){
            is PokemonDetailsViewState.Loading -> {
                binding.pokemonImage.isVisible = false
                binding.pokemonName.isVisible = false
            }

            is PokemonDetailsViewState.Data -> {
                binding.progressBar.hide()
                binding.pokemonImage.isVisible = true
                binding.pokemonName.isVisible = true
                binding.pokemon = state.pokemonItem
                binding.executePendingBindings()
            }

            is PokemonDetailsViewState.Error -> {
                binding.progressBar.hide()
                binding.pokemonName.isVisible = true
                binding.pokemonName.text = state.message
            }
        }
    }

}