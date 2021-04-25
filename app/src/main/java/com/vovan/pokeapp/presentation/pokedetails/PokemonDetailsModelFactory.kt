package com.vovan.pokeapp.presentation.pokedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class PokemonDetailsModelFactory(private val pokemonId: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)){
            return PokemonDetailsViewModel(pokemonId) as T
        }
        throw IllegalArgumentException("Unknown class ViewModel")
    }
}