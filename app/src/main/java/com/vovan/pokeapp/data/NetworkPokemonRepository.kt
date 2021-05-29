package com.vovan.pokeapp.data

import com.vovan.pokeapp.data.network.PokemonApiService
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.domain.Result
import com.vovan.pokeapp.toPokemonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkPokemonRepository(val api: PokemonApiService): PokemonRepository {
    override suspend fun getPokemonById(id: String): Result<PokemonEntity> {
        return withContext(Dispatchers.IO){
            try {
                val pokemon = api.fetchPokemonInfo(id).toPokemonEntity()
                Result.Success(pokemon)
            }catch (e: Exception){
                Result.Error(e)
            }
        }
    }

    override suspend fun getPokemonList(): Result<List<PokemonEntity>> {
        return withContext(Dispatchers.IO){
            try {
                val names = api.fetchPokemonList().results.map { it.name }
                val pokemonList = names.map { name ->
                    api.fetchPokemonInfo(name).toPokemonEntity()
                }

                Result.Success(pokemonList)
            }catch (e: java.lang.Exception){
                Result.Error(e)
            }
        }
    }
}