package com.vovan.pokeapp.presentation.pokedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.domain.Result
import com.vovan.pokeapp.toPokemonItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _state = MutableLiveData<PokemonDetailsViewState>()
    val state: LiveData<PokemonDetailsViewState>
        get() = _state

    init {
        _state.value = PokemonDetailsViewState.Loading
    }

     fun loadData(pokemonId: Int) {
        viewModelScope.launch {
            val result = repository.getPokemonById(pokemonId.toString())
            processResult(result)
        }
    }

    private fun processResult(result: Result<PokemonEntity>) {
        when (result) {
            is Result.Success ->
                _state.value = PokemonDetailsViewState.Data(result.data.toPokemonItem())

            is Result.Error ->
                _state.value = PokemonDetailsViewState.Error(result.error.message ?: "Unknown 404)")
        }
    }

}