package com.vovan.pokeapp.domain

import com.vovan.pokeapp.generatePokemonArtUrlFromId
import com.vovan.pokeapp.generatePokemonUrlFromId

data class PokemonEntity(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String = generatePokemonUrlFromId(id),
    val artUrl: String = generatePokemonArtUrlFromId(id),
    val order: Int,
    val generation: Int = 0,
    val types: List<String>,
    val stats: List<PokemonStatEntity>,
    val abilities: List<String>
)

data class PokemonStatEntity(
    val name: String,
    val base_stat: Int
)


