package com.vovan.pokeapp.presentation.pokelist

import com.vovan.pokeapp.presentation.adapter.DisplayableItem
import com.vovan.pokeapp.presentation.adapter.PokemonItem

sealed class PokemonListViewState {
    object Loading: PokemonListViewState()

    data class Data(val pokemons: List<DisplayableItem>): PokemonListViewState()

    data class Error(val message: String): PokemonListViewState()
}