package com.vovan.pokeapp.presentation.pokelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
    private lateinit var adapter: PokemonAdapter
    private val binding: FragmentPokemonListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PokemonAdapter(PokemonAdapter.PokemonClickListener {
            findNavController().navigate(
                PokemonListFragmentDirections.actionPokemonListFragmentToPokemonDetailsFragment(it)
            )
        })
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner, ::displayData)
    }


    private fun displayData(state: PokemonListViewState){
        when(state){
            is PokemonListViewState.Loading -> {}

            is PokemonListViewState.Data -> {
                binding.progressBar.hide()
                adapter.setPokemonList(state.pokemons)
            }

            is PokemonListViewState.Error -> {
                Timber.e(state.message)
            }
        }
    }

}