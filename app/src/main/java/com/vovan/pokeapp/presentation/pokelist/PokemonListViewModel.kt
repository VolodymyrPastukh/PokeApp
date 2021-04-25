package com.vovan.pokeapp.presentation.pokelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vovan.pokeapp.data.NetworkPokemonRepository
import com.vovan.pokeapp.data.network.createPokemonApiService
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.presentation.adapter.DisplayableItem
import com.vovan.pokeapp.presentation.adapter.HeaderItem
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PokemonListViewModel: ViewModel() {
    private val repository: PokemonRepository = NetworkPokemonRepository(
        api = createPokemonApiService()
    )

    private val _pokemonListData = MutableLiveData<List<DisplayableItem>>()
    val pokemonListData: LiveData<List<DisplayableItem>>
        get() = _pokemonListData

    fun loadData() {
        val disposable =repository.getPokemonList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { showData(it.map { it.toPokemonItem() }) },
                { Timber.d("ViewModel error $it") }
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

    private fun PokemonEntity.toPokemonItem(): PokemonItem {
        return PokemonItem(
            this.id,
            this.name,
            this.imageUrl,
            this.attribute,
        )
    }


}