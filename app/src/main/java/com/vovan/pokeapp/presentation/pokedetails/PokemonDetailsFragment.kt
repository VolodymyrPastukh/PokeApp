package com.vovan.pokeapp.presentation.pokedetails

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentPokemonDetailsBinding
import com.vovan.pokeapp.presentation.adapter.PokemonAdapter
import com.vovan.pokeapp.presentation.adapter.PokemonStatItem
import com.vovan.pokeapp.presentation.adapter.StatAdapter
import com.vovan.pokeapp.presentation.pokelist.PokemonListFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PokemonDetailsFragment : Fragment(R.layout.fragment_pokemon_details) {

    private val navArgs by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel: PokemonDetailsViewModel by viewModel { parametersOf(navArgs.pokemonId)}
    private val binding: FragmentPokemonDetailsBinding by viewBinding()
    private var adapter: StatAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = StatAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

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
                adapter?.setData(state.pokemonItem.stats)

            }

            is PokemonDetailsViewState.Error -> {
                progressBar.hide()
            }
        }
    }

}