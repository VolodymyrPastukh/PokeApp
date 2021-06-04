package com.vovan.pokeapp.domain

import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonById(id: Int): Result<PokemonEntity>
    val allPokemons: Flow<List<PokemonEntity>>
    val allPokemonsGeneration: Flow<List<PokemonEntity>>
}














