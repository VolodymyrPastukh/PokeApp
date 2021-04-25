package com.vovan.pokeapp.presentation.pokedetails

import com.vovan.pokeapp.presentation.adapter.PokemonItem

sealed class PokemonDetailsViewState {
    object Loading: PokemonDetailsViewState()

    data class Data(val pokemonItem: PokemonItem): PokemonDetailsViewState()

    data class Error(val message: String): PokemonDetailsViewState()
}