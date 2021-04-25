package com.vovan.pokeapp.presentation.pokedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentPokemonDetailsBinding
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.presentation.adapter.setPokemonImageFromUrl
import timber.log.Timber
import java.lang.IllegalArgumentException


class PokemonDetailsFragment : Fragment() {

    private lateinit var viewModel: PokemonDetailsViewModel
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

        val factory = PokemonDetailsModelFactory(getBundlePokemonId())
        viewModel = ViewModelProvider(this, factory)
            .get(PokemonDetailsViewModel::class.java)


        viewModel.pokemon.observe(viewLifecycleOwner, { state ->
            displayData(state)
        })


        return binding.root
    }

    fun displayData(state: PokemonDetailsViewState){
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




    private fun getBundlePokemonId(): Int{
        val id = arguments?.getInt(PARAM_POKEMON_ID)

        if(id != null) return id

        throw IllegalArgumentException("Null argument Pokemon id")
    }

    companion object{
        private const val PARAM_POKEMON_ID = "Pokemon_id"

        fun newInstance(id: Int): Fragment = PokemonDetailsFragment().apply {
            arguments = bundleOf(
                PARAM_POKEMON_ID to id
            )
        }
    }

}