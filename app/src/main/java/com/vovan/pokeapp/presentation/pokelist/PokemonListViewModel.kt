package com.vovan.pokeapp.presentation.pokelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.toPokemonItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class PokemonListViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _state = MutableLiveData<PokemonListViewState>()
    val state: LiveData<PokemonListViewState>
        get() = _state

    init {
        _state.value = PokemonListViewState.Loading
        loadData()
    }

    private fun loadData() {
        repository.allPokemons.map { entityList ->
            PokemonListViewState.Data(entityList.map { it.toPokemonItem() })
        }.onEach { _state.postValue(it) }.launchIn(viewModelScope)
    }

    fun storeItem(item: PokemonItem) {
       viewModelScope.launch {
           repository.savePokemon(item.id, item.imageUrl)
       }
    }

    fun deleteStoredItem(item: PokemonItem) {
        viewModelScope.launch {
            repository.deletePokemon(item.id)
        }
    }
}