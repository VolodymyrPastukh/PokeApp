package com.vovan.pokeapp.presentation.pokelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vovan.pokeapp.data.RxPokemonRepository
import com.vovan.pokeapp.domain.Result
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.toPokemonItem
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class RxPokemonListViewModel(private val repository: RxPokemonRepository) : ViewModel() {

    private val pokemonsData = mutableListOf<PokemonItem>()
    private val _state = MutableLiveData<PokemonListViewState>()
    val state: LiveData<PokemonListViewState>
        get() = _state


    init {
        _state.value = PokemonListViewState.Loading
        loadData()
    }

    private fun loadData() {
        val disposableData = repository.allPokemons.rxSubscribe(map = { response ->
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
        repository.savePokemon(item.id, item.imageUrl).rxSubscribe()
    }

    fun deleteStoredItem(item: PokemonItem) {
        repository.deletePokemon(item.id).rxSubscribe()
    }

    override fun onCleared() {
        super.onCleared()
    }
}

private fun Completable.rxSubscribe() =
    this.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribe()

private fun <T> Observable<T>.rxSubscribe(subscribe: (T) -> Unit) =
    this.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .subscribe(subscribe)

private fun <T, O> Observable<T>.rxSubscribe(map: (T) -> O, subscribe: (O) -> Unit) =
    this.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation())
        .map(map)
        .subscribe(subscribe)