package com.vovan.pokeapp

import com.vovan.pokeapp.data.dto.PokemonDetailsDTO
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonStatEntity
import com.vovan.pokeapp.presentation.adapter.PokemonItem

fun generatePokemonUrlFromId(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

fun generatePokemonArtUrlFromId(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

fun getIdFromUrl(url: String): Int {
    val regex = "\\b[0-9]+".toRegex()
    return regex.find(url)?.value?.toInt() ?: 1
}

fun PokemonEntity.toPokemonItem(): PokemonItem {
    return PokemonItem(
        id = id,
        name = name,
        height = height,
        weight = weight,
        order = order,
        types = types,
        stats = stats,
        abilities = abilities
    )
}


fun PokemonDetailsDTO.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        order = order,
        types = types.map { it.type.name },
        stats = stats.map { PokemonStatEntity(it.stat.name, it.base_stat) },
        abilities = abilities.map { it.ability.name }
    )
}

fun PokemonDetailsDTO.toPokemonEntity(generation: Int): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        height = height,
        weight = weight,
        order = order,
        generation = generation,
        types = types.map { it.type.name },
        stats = stats.map { PokemonStatEntity(it.stat.name, it.base_stat) },
        abilities = abilities.map { it.ability.name }
    )
}