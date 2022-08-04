package com.vovan.pokeapp.data

import com.vovan.pokeapp.data.network.PokemonApiService
import com.vovan.pokeapp.data.room.PokemonsDao
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.domain.Result
import com.vovan.pokeapp.getIdFromUrl
import com.vovan.pokeapp.toPokemonDB
import com.vovan.pokeapp.toPokemonEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import timber.log.Timber

typealias FlowPokemons = Flow<List<PokemonEntity>>

class NetworkPokemonRepository(
    private val api: PokemonApiService,
    private val pokemonsDao: PokemonsDao,
    private val externalScope: CoroutineScope
) : PokemonRepository {

    override suspend fun getPokemonById(id: Int): Result<PokemonEntity> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(api.fetchPokemonInfo(id).toPokemonEntity())
            } catch (e: Exception) {
                val pokemon = pokemonsDao.getPokemonById(id)?.toPokemonEntity()
                    ?: return@withContext Result.Error(e)
                Result.Success(pokemon)
            }
        }
    }

    override val allPokemons: FlowPokemons = flow {
        val respond = mutableListOf<PokemonEntity>()
        try {
            val count = api.fetchPokemonList().count
            (0..count).forEach { c ->
                val storedPokemons = pokemonsDao.getAll().map { it.id }.toSet()
                val newPokemons = api.fetchPokemonList(offset = c * 2)
                    .results
                    .map { getIdFromUrl(it.url) }
                    .map { name ->
                        api.fetchPokemonInfo(name).toPokemonEntity()
                            .also { if (storedPokemons.contains(it.id)) it.isLiked = true }
                    }.sortedBy { it.order }

                respond.addAll(newPokemons)
                emit(respond)
            }
        } catch (e: Exception) {
            respond.addAll(pokemonsDao.getAll().map { it.toPokemonEntity() })
            emit(respond)
        }
    }.shareIn(externalScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 0))


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


    override suspend fun savePokemon(id: Int, url: String) {
        withContext(externalScope.coroutineContext) {
            val pokemon = api.fetchPokemonInfo(id)
            pokemonsDao.insertAll(pokemon.toPokemonDB(url))
        }
    }

    override suspend fun deletePokemon(id: Int): Boolean {
        return withContext(externalScope.coroutineContext) {
            val pokemon = pokemonsDao.getPokemonById(id) ?: return@withContext false
            pokemonsDao.delete(pokemon)
            true
        }
    }
}