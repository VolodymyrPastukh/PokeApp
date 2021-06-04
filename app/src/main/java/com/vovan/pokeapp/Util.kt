package com.vovan.pokeapp

import com.vovan.pokeapp.data.dto.PokemonDetailedDTO
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.presentation.adapter.PokemonItem

fun generatePokemonUrlFromId(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

fun generatePokemonArtUrlFromId(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"


fun PokemonEntity.toPokemonItem(): PokemonItem {
    return PokemonItem(
        id,
        name,
        imageUrl,
        order,
    )
}

fun getIdFromUrl(url: String): Int {
    val regex = "\\b[0-9]+".toRegex()
    return regex.find(url)?.value?.toInt() ?: 1
}

fun PokemonDetailedDTO.toPokemonEntity(): PokemonEntity{
    return PokemonEntity(
        id,
        name,
        generatePokemonArtUrlFromId(id),
        order,
        abilities = abilities.map { it.ability.name }
    )
}

fun PokemonDetailedDTO.toPokemonEntity(generation: Int): PokemonEntity{
    return PokemonEntity(
        id,
        name,
        generatePokemonUrlFromId(id),
        order,
        generation,
        abilities = abilities.map { it.ability.name }
    )
}