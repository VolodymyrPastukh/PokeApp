package com.vovan.pokeapp.domain

data class PokemonEntity(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val attribute: Int = -1,
    val abilities: List<String> = emptyList()
)