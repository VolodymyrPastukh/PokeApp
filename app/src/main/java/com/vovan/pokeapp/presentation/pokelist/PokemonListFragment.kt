package com.vovan.pokeapp.presentation.pokelist

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentPokemonListBinding
import com.vovan.pokeapp.presentation.adapter.PokemonAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PokemonListFragment : Fragment(R.layout.fragment_pokemon_list) {

    private val viewModel: PokemonListViewModel by viewModel()
    private val binding: FragmentPokemonListBinding by viewBinding()
    private var adapter: PokemonAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PokemonAdapter(PokemonAdapter.PokemonClickListener {
            findNavController().navigate(
                PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailsFragment(it)
            )
        })
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }

        viewModel.state.observe(viewLifecycleOwner, ::displayData)
    }


    private fun displayData(state: PokemonListViewState) = binding.apply{
        when(state){
            is PokemonListViewState.Loading -> {}

            is PokemonListViewState.Data -> {
                progressBar.hide()
                adapter?.submitList(state.pokemons)
            }

            is PokemonListViewState.Error -> {
                Timber.e(state.message)
            }
        }
    }

}