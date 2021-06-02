package com.vovan.pokeapp.presentation.pokelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.domain.Result
import com.vovan.pokeapp.presentation.adapter.DisplayableItem
import com.vovan.pokeapp.presentation.adapter.HeaderItem
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.toPokemonItem
import kotlinx.coroutines.delay
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
        _state.value = PokemonListViewState.Loading
        viewModelScope.launch {
            val result = repository.getPokemonList()
            processResult(result)
        }
    }

    private fun processResult(result: Result<List<PokemonEntity>>){
        when(result){
            is Result.Success ->
                showData(result.data.map { it.toPokemonItem() })

            is Result.Error ->
                _state.value = PokemonListViewState.Error(result.error.message ?: "Unknown 404)")
        }
    }

    private fun showData(pokemonList: List<PokemonItem>) {
        val listByAttributes = listOf(
            pokemonList.filter { it.attribute == 0 },
            pokemonList.filter { it.attribute == 1 },
            pokemonList.filter { it.attribute == 2 },
            pokemonList.filter { it.attribute == -1 }
        )
        val resultList = mutableListOf<DisplayableItem>()


        for (index in listByAttributes.indices) {
            resultList.add(getAttributeBanner(index))
            resultList.addAll(listByAttributes[index] as Collection<DisplayableItem>)
        }

        _state.value = PokemonListViewState.Data(resultList)

    }

    private fun getAttributeBanner(attribute: Int): HeaderItem {
        return when (attribute) {
            0 -> HeaderItem(HeaderItem.Attribute.STRENGTH)
            1 -> HeaderItem(HeaderItem.Attribute.AGILITY)
            2 -> HeaderItem(HeaderItem.Attribute.INTELLIGENCE)
            else -> HeaderItem()
        }
    }




}