package com.vovan.pokeapp.presentation.pokedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.domain.Result
import com.vovan.pokeapp.toPokemonItem
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val pokemonId: Int,
    private val repository: PokemonRepository
) : ViewModel() {

    private val _state = MutableLiveData<PokemonDetailsViewState>()
    val state: LiveData<PokemonDetailsViewState>
        get() = _state

    init {
        _state.value = PokemonDetailsViewState.Loading
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val result = repository.getPokemonById(pokemonId)
            processResult(result)
        }
    }

    private fun processResult(result: Result<PokemonEntity>) {
        _state.postValue(
            when (result) {
                is Result.Success -> PokemonDetailsViewState.Data(result.data.toPokemonItem())
                is Result.Error -> PokemonDetailsViewState.Error(result.error.message ?: "404")
            }
        )
    }

}