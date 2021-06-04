package com.vovan.pokeapp.data.dto

data class PokemonListDTO(
    val count: Int,
    val next: String?,
    val results: List<PokemonPartialDTO>,
)

data class PokemonPartialDTO(
    val name: String,
    val url: String
)

data class PokemonDetailedDTO(
    val id: Int,
    val name: String,
    val order: Int,
    val abilities: List<PokemonAbilityData>
)

data class PokemonAbilityDetailsData(
    val name: String,
    val url: String
)

data class PokemonAbilityData(
    val ability: PokemonAbilityDetailsData,
    val is_hidden: Boolean,
    val slot: Int,
)


data class GenerationDetailedDTO(
    val name: String,
    val pokemon_species: List<GenerationPokemon>
)

data class GenerationPokemon(
    val url: String
)