package com.vovan.pokeapp.presentation.pokedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vovan.pokeapp.data.NetworkPokemonRepository
import com.vovan.pokeapp.data.network.createPokemonApiService
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import com.vovan.pokeapp.toPokemonItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PokemonDetailsViewModel(private val pokemonId: Int) : ViewModel() {

    private val repository = NetworkPokemonRepository(
        api = createPokemonApiService()
    )

    private val _pokemon = MutableLiveData<PokemonItem>()
    val pokemon: LiveData<PokemonItem>
        get() = _pokemon

    init {
        loadData()
    }

    private fun loadData(){
        repository.getPokemonById(pokemonId.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _pokemon.value = it.toPokemonItem()},
                { Timber.d("ViewModel error $it")}
            )
    }


}