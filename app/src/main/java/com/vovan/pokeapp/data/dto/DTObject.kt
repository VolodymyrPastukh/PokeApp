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

data class PokemonDetailsDTO(
    val id: Int,
    val name: String,
    val order: Int,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeDTO>,
    val stats: List<PokemonStatsDTO>,
    val abilities: List<PokemonAbilityData>
)

data class PokemonTypeDTO(val slot: Int, val type: PokemonTypeDetailsDTO)
data class PokemonTypeDetailsDTO(val name: String)

data class PokemonAbilityData(val ability: PokemonAbilityDetailsData )
data class PokemonAbilityDetailsData(val name: String, val url: String)

data class PokemonStatsDTO(val base_stat: Int, val stat: PokemonStatDetailsDTO)
data class PokemonStatDetailsDTO(val name: String)


data class GenerationDetailedDTO(val name: String, val pokemon_species: List<GenerationPokemon>)
data class GenerationPokemon(val url: String)