package com.vovan.pokeapp.presentation.adapter

import com.vovan.pokeapp.domain.PokemonStatEntity
import com.vovan.pokeapp.generatePokemonArtUrlFromId
import com.vovan.pokeapp.generatePokemonUrlFromId

interface DisplayableItem

data class PokemonItem(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String = generatePokemonUrlFromId(id),
    val artUrl: String = generatePokemonArtUrlFromId(id),
    val order: Int,
    val generation: Int = 0,
    val types: List<String>,
    val stats: List<PokemonStatItem>,
    val abilities: List<String>
): DisplayableItem

data class PokemonStatItem(
    val name: String,
    val base_stat: Int
): DisplayableItem
