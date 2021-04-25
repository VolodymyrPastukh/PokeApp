package com.vovan.pokeapp.presentation.pokedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPokemonDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_pokemon_details,
            container,
            false
        )

        val factory = PokemonDetailsModelFactory(getBundlePokemonId())
        viewModel = ViewModelProvider(this, factory)
            .get(PokemonDetailsViewModel::class.java)


        viewModel.pokemon.observe(viewLifecycleOwner, {pokemonItem ->
            binding.pokemonName.text = pokemonItem.name

            Picasso.get().load(pokemonItem.imageUrl).into(binding.pokemonImage, object : Callback {
                override fun onSuccess() {
                    Timber.d("Loaded image")
                }

                override fun onError(e: Exception?) {
                    Timber.d("Loaded image exception $e")
                }
            })

        })


        return binding.root
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