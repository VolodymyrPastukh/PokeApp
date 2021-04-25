package com.vovan.pokeapp.domain

interface PokemonRepository {

    fun getPokemonById(id: Int): PokemonEntity
    fun getPokemonList(): List<PokemonEntity>

}

