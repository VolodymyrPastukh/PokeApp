package com.vovan.pokeapp

fun generatePokemonUrlFromId(id: Int): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"