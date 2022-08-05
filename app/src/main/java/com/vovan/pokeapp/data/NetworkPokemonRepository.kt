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

typealias FlowPokemons = Flow<Result<List<PokemonEntity>>>

class NetworkPokemonRepository(
    private val api: PokemonApiService,
    private val pokemonsDao: PokemonsDao,
    private val externalScope: CoroutineScope
) : PokemonRepository {

    private val itemsMap = mutableMapOf<Int, PokemonEntity>()

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
        try {
            val count = api.fetchPokemonList().count
            (0..count).forEach { c ->
                val storedPokemons = pokemonsDao.getAllPokemons().map { it.id }.toSet()
                itemsMap.putAll(api.fetchPokemonList(offset = c * 2)
                    .results
                    .map { getIdFromUrl(it.url) }
                    .map { name ->
                        api.fetchPokemonInfo(name).toPokemonEntity()
                            .also { if (storedPokemons.contains(it.id)) it.isLiked = true }
                    }.sortedBy { it.order }.map { it.id to it })

                emit(Result.Success(itemsMap.values.toList()))
            }
        } catch (e: Exception) {
            itemsMap.putAll(pokemonsDao.getAllPokemons().map { it.toPokemonEntity() }
                .sortedBy { it.order }.map { it.id to it })
            if (itemsMap.isEmpty()) emit(Result.Error(IllegalStateException("No items in api and db")))
            emit(Result.Success(itemsMap.values.toList()))
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

            emit(Result.Success(respond))
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