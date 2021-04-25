package com.vovan.pokeapp.data

import com.vovan.pokeapp.data.network.PokemonApiService
import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.generatePokemonUrlFromId
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber
import kotlin.random.Random
import kotlin.random.nextInt

class NetworkPokemonRepository(val api: PokemonApiService): PokemonRepository {
    override fun getPokemonById(id: String): Single<PokemonEntity> {
        return api.fetchPokemonInfo(id)
            .map { pokemonDTO ->
                PokemonEntity(
                    pokemonDTO.id,
                    pokemonDTO.name,
                    generatePokemonUrlFromId(pokemonDTO.id),
                    Random.nextInt(0..3),
                    pokemonDTO.abilities.map { it.ability.name }
                )
            }
    }

    override fun getPokemonList(): Single<List<PokemonEntity>> {
        return api.fetchPokemonList().flatMap { pokemonList ->

            Observable
                .fromIterable(pokemonList.results)
                .flatMapSingle {
                    Timber.d("${pokemonList.results.indexOf(it)}")
                    getPokemonById(it.name)
                }.toList()

        }
    }
}