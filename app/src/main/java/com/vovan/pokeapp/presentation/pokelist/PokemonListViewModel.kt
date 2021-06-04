package com.vovan.pokeapp.presentation.pokelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.toPokemonItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PokemonListViewModel(private val repository: PokemonRepository): ViewModel() {

    private val _state = MutableLiveData<PokemonListViewState>()
    val state: LiveData<PokemonListViewState>
        get() = _state

    init {
        _state.value = PokemonListViewState.Loading
        loadData()
    }


    private fun loadData() {
        viewModelScope.launch {
            repository.allPokemons.collect{ pokemons ->
                showData(pokemons.map { it.toPokemonItem() })
            }
        }
    }


    private fun showData(pokemonList: List<PokemonItem>) {
        _state.value = PokemonListViewState.Data(pokemonList)
    }

}