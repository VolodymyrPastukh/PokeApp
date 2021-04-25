package com.vovan.pokeapp.data

import com.vovan.pokeapp.domain.PokemonEntity
import com.vovan.pokeapp.domain.PokemonRepository
import com.vovan.pokeapp.generatePokemonUrlFromId

class MockPokemonRepository: PokemonRepository {

    private val pokemons = mutableListOf(
        PokemonEntity(
            1,
            "bulbasaur",
            generatePokemonUrlFromId(1),
            1
        ),
        PokemonEntity(
            2,
            "ivysaur",
            generatePokemonUrlFromId(2),
            2
        ),
        PokemonEntity(
            3,
            "venusaur",
            generatePokemonUrlFromId(3),
            0
        ),
        PokemonEntity(
            4,
            "charmander",
            generatePokemonUrlFromId(4),
            2
        ),
        PokemonEntity(
            5,
            "charmeleon",
            generatePokemonUrlFromId(5)
        )
    )

    override fun getPokemonById(id: Int): PokemonEntity {
        return pokemons[id]
    }

    override fun getPokemonList(): List<PokemonEntity> = pokemons
}