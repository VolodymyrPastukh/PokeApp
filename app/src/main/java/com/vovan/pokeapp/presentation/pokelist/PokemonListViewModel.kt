package com.vovan.pokeapp.presentation.pokelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vovan.pokeapp.data.NetworkPokemonRepository
import com.vovan.pokeapp.data.network.createPokemonApiService
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.presentation.adapter.DisplayableItem
import com.vovan.pokeapp.presentation.adapter.HeaderItem
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.toPokemonItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PokemonListViewModel: ViewModel() {
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokemonApiService()
    )

    private val _pokemonListData = MutableLiveData<PokemonListViewState>()
    val pokemonListData: LiveData<PokemonListViewState>
        get() = _pokemonListData

    fun loadData() {
        _pokemonListData.value = PokemonListViewState.Loading
        repository.getPokemonList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showData(it.map { it.toPokemonItem() }) },
                { _pokemonListData.value = PokemonListViewState.Error("Error - $it") }
            )
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

        _pokemonListData.value = PokemonListViewState.Data(resultList)

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