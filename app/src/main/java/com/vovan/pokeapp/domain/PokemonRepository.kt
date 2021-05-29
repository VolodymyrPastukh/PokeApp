package com.vovan.pokeapp.domain

interface PokemonRepository {
    suspend fun getPokemonById(id: String): Result<PokemonEntity>
    suspend fun getPokemonList(): Result<List<PokemonEntity>>
}














