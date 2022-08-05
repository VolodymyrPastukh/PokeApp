package com.vovan.pokeapp.domain

import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonById(id: Int): Result<PokemonEntity>
    val allPokemons: Flow<Result<List<PokemonEntity>>>
    val allPokemonsGeneration: Flow<Result<List<PokemonEntity>>>

    suspend fun savePokemon(id: Int, url: String)
    suspend fun deletePokemon(id: Int): Boolean
}














