package com.vovan.pokeapp.presentation.pokelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vovan.pokeapp.R
import com.vovan.pokeapp.databinding.FragmentPokeListBinding
import com.vovan.pokeapp.presentation.adapter.PokeAdapter
import timber.log.Timber

class PokemonListFragment : Fragment() {

    lateinit var viewModel: PokemonListViewModel
    private lateinit var adapter: PokeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Binding
        val binding: FragmentPokeListBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_poke_list,
                container,
                false
            )


        adapter = PokeAdapter()
        viewModel = ViewModelProvider(this).get(PokemonListViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter


        viewModel.pokemonListData.observe(viewLifecycleOwner, { pokemonList ->
            Timber.d("Adapter setList")
            adapter.setPokemonList(pokemonList)
        })

        viewModel.loadData()

        return binding.root
    }


}