package com.vovan.pokeapp.presentation.pokelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vovan.pokeapp.data.RxPokemonRepository
import com.vovan.pokeapp.domain.Result
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.toPokemonItem
import com.vovan.pokeapp.utils.rxSubscribe
import io.reactivex.disposables.CompositeDisposable

class RxPokemonListViewModel(private val repository: RxPokemonRepository) : ViewModel() {

    private val _state = MutableLiveData<PokemonListViewState>()
    val state: LiveData<PokemonListViewState>
        get() = _state

    private val disposableBug = CompositeDisposable()

    init {
        _state.value = PokemonListViewState.Loading
        loadData()
    }

    private fun loadData() {
        repository.allPokemons.rxSubscribe(disposableBug, map = { response ->
            when (response) {
                is Result.Success -> PokemonListViewState.Data(response.data.map { it.toPokemonItem() })
                is Result.Error -> PokemonListViewState.Error(
                    response.error.message ?: "unknown error"
                )
            }
        }) {
            _state.postValue(it)
        }
    }

    fun storeItem(item: PokemonItem) {
        repository.savePokemon(item.id, item.imageUrl).rxSubscribe(disposableBug)

    }

    fun deleteStoredItem(item: PokemonItem) {
        repository.deletePokemon(item.id).rxSubscribe(disposableBug)
    }

    override fun onCleared() {
        disposableBug.clear()
        super.onCleared()
    }
}