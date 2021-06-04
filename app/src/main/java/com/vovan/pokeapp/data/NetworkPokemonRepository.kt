package com.vovan.pokeapp.data

import com.vovan.pokeapp.data.network.PokemonApiService
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.getIdFromUrl
import com.vovan.pokeapp.toPokemonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import com.vovan.pokeapp.domain.Result

typealias FlowPokemons = Flow<List<PokemonEntity>>

class NetworkPokemonRepository(private val api: PokemonApiService) : PokemonRepository {

    override suspend fun getPokemonById(id: Int): Result<PokemonEntity> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemon = api.fetchPokemonInfo(id).toPokemonEntity()
                Result.Success(pokemon)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override val allPokemons: FlowPokemons = flow {
        val respond = mutableListOf<PokemonEntity>()
        val count = api.fetchPokemonList().count
        for (i in 0..count) {
            val ids = api.fetchPokemonList(offset = i * 2)
                .results.map { getIdFromUrl(it.url) }

            val pokemons = ids.map { name ->
                api.fetchPokemonInfo(name).toPokemonEntity()
            }

            respond.addAll(pokemons)
            respond.sortBy { it.order }

            emit(respond)
        }
    }.flowOn(Dispatchers.IO)
        .catch { e -> Timber.e(e) }


    override val allPokemonsGeneration: FlowPokemons = flow {
        val respond = mutableListOf<PokemonEntity>()

        for (i in 1..8) {
            val ids = api.fetchPokemonGeneration(i).pokemon_species
                .map { getIdFromUrl(it.url) }
            Timber.d("Generation $i")

            val pokemons = ids.map { name ->
                api.fetchPokemonInfo(name).toPokemonEntity(i)
            }
            respond.addAll(pokemons)
            respond.sortBy { it.generation }

            emit(respond)
        }
    }.flowOn(Dispatchers.IO)
        .catch { e -> Timber.e(e) }


}