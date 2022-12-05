package com.vovan.pokeapp.data

import com.vovan.pokeapp.data.network.PokemonApiService
import com.vovan.pokeapp.data.room.PokemonsDao
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.Result
import com.vovan.pokeapp.getIdFromUrl
import com.vovan.pokeapp.toPokemonDB
import com.vovan.pokeapp.toPokemonEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.asCompletable
import kotlinx.coroutines.rx2.rxObservable
import kotlinx.coroutines.rx2.rxSingle
import timber.log.Timber

class RxPokemonRepository(
    private val api: PokemonApiService,
    private val pokemonsDao: PokemonsDao,
    private val externalScope: CoroutineScope,
) {

    private val itemsMap = mutableMapOf<Int, PokemonEntity>()

    fun getPokemonById(id: Int): Single<Result<PokemonEntity>> {
        return rxSingle {
            try {
                Result.Success(api.fetchPokemonInfo(id).toPokemonEntity())
            } catch (e: Exception) {
                val pokemon = pokemonsDao.getPokemonById(id)?.toPokemonEntity()
                    ?: return@rxSingle Result.Error(e)
                Result.Success(pokemon)
            }
        }
    }

    val allPokemons: Observable<Result<List<PokemonEntity>>> = rxObservable {
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

                trySend(Result.Success(itemsMap.values.toList()))
            }
        } catch (e: Exception) {
            itemsMap.putAll(pokemonsDao.getAllPokemons().map { it.toPokemonEntity() }
                .sortedBy { it.order }.map { it.id to it })
            if (itemsMap.isEmpty()) trySend(Result.Error(IllegalStateException("No items in api and db")))
            trySend(Result.Success(itemsMap.values.toList()))
        }
    }


    val allPokemonsGeneration: Observable<Result<List<PokemonEntity>>> = rxObservable {
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

            trySend(Result.Success(respond))
        }
    }

    fun savePokemon(id: Int, url: String): Completable = externalScope.launch {
        val pokemon = api.fetchPokemonInfo(id)
        pokemonsDao.insertAll(pokemon.toPokemonDB(url))
    }.asCompletable(externalScope.coroutineContext)

    fun deletePokemon(id: Int): Completable = externalScope.launch {
        val pokemon = pokemonsDao.getPokemonById(id) ?: return@launch
        pokemonsDao.delete(pokemon)
    }.asCompletable(externalScope.coroutineContext)
}