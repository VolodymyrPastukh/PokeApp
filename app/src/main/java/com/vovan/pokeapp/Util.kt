package com.vovan.pokeapp

import com.vovan.pokeapp.data.network.PokemonDetailedDTO
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.presentation.adapter.PokemonItem
import kotlin.random.Random
import kotlin.random.nextInt

fun generatePokemonUrlFromId(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

fun PokemonEntity.toPokemonItem(): PokemonItem {
    return PokemonItem(
        id,
        name,
        imageUrl,
        attribute,
    )
}

fun PokemonDetailedDTO.toPokemonEntity(): PokemonEntity{
    return PokemonEntity(
        id,
        name,
        generatePokemonUrlFromId(id),
        Random.nextInt(0..3),
        abilities.map { it.ability.name }
    )
}