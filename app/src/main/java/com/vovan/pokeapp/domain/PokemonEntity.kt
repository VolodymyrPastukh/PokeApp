package com.vovan.pokeapp.domain

data class PokemonEntity(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val artUrl: String,
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


