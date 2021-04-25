package com.vovan.pokeapp.domain

import io.reactivex.Single

interface PokemonRepository {

    fun getPokemonById(id: String): Single<PokemonEntity>
    fun getPokemonList(): Single<List<PokemonEntity>>

}

