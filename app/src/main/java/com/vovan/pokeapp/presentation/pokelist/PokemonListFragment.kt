package com.vovan.pokeapp.presentation.pokelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentPokemonListBinding
import com.vovan.pokeapp.presentation.Navigation
import com.vovan.pokeapp.presentation.adapter.PokemonAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PokemonListFragment : Fragment() {

    private val viewModel: PokemonListViewModel by viewModel()
    private lateinit var adapter: PokemonAdapter
    private lateinit var binding: FragmentPokemonListBinding
    private val navigator: Navigation? by lazy { (activity as? Navigation) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Binding
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_pokemon_list,
                container,
                false
            )


        adapter = PokemonAdapter(PokemonAdapter.PokemonClickListener {
            navigator?.openPokemonDetails(it)
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter


        viewModel.state.observe(viewLifecycleOwner, { state ->
            displayData(state)
        })

        return binding.root
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