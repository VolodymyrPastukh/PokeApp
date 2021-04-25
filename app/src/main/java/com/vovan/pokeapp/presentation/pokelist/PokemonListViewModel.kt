package com.vovan.pokeapp.presentation.pokelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vovan.pokeapp.data.MockPokemonRepository
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.presentation.adapter.DisplayableItem
import com.vovan.pokeapp.presentation.adapter.HeaderItem
import com.vovan.pokeapp.presentation.adapter.PokemonItem

class PokemonListViewModel: ViewModel() {
    private val repository: PokemonRepository = MockPokemonRepository()

    private val _pokemonListData = MutableLiveData<List<DisplayableItem>>()
    val pokemonListData: LiveData<List<DisplayableItem>>
        get() = _pokemonListData

    fun loadData() {
        val pokemons = repository.getPokemonList()
            .map { it.toPokeItem() }

        showData(pokemons)
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

        _pokemonListData.value = resultList

    }

    private fun getAttributeBanner(attribute: Int): HeaderItem {
        return when (attribute) {
            0 -> HeaderItem(HeaderItem.Attribute.STRENGTH)
            1 -> HeaderItem(HeaderItem.Attribute.AGILITY)
            2 -> HeaderItem(HeaderItem.Attribute.INTELLIGENCE)
            else -> HeaderItem()
        }
    }

    private fun PokemonEntity.toPokeItem(): PokemonItem {
        return PokemonItem(
            this.id,
            this.name,
            this.imageUrl,
            this.attribute,
        )
    }


}