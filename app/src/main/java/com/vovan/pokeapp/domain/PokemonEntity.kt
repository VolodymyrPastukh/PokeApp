package com.vovan.pokeapp.domain

data class PokemonEntity(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val order: Int,
    val generation: Int = 0,
    val abilities: List<String> = emptyList()
)
