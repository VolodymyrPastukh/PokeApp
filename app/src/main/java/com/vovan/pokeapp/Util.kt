package com.vovan.pokeapp

import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.presentation.adapter.PokemonItem

fun generatePokemonUrlFromId(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

fun PokemonEntity.toPokemonItem(): PokemonItem {
    return PokemonItem(
        this.id,
        this.name,
        this.imageUrl,
        this.attribute,
    )
}